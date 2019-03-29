/* 
 * Copyright (C) 2019 Warkdev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.jangos.extractor.file.impl;

import javafx.geometry.Point2D;
import eu.jangos.extractor.file.ChunkLiquidRenderer;
import eu.jangos.extractor.file.FileReader;
import eu.jangos.extractor.file.adt.chunk.MCLQ;
import eu.jangos.extractor.file.adt.chunk.MCNK;
import eu.jangos.extractor.file.adt.chunk.MODF;
import eu.jangos.extractor.file.exception.FileReaderException;
import eu.jangos.extractor.file.exception.MPQException;
import eu.jangos.extractor.file.exception.ModelRendererException;
import eu.jangos.extractor.file.exception.WDTException;
import eu.jangos.extractor.file.mpq.MPQManager;
import eu.jangos.extractor.file.wdt.AreaInfo;
import eu.jangos.extractor.rendering.PolygonMesh;
import eu.jangos.extractor.rendering.Render2DType;
import eu.jangos.extractor.rendering.Render3DType;
import eu.jangos.utils.FlagUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import systems.crigges.jmpq3.JMpqException;

/**
 * WDT represents a WDT file from WoW package. This class allows to read its
 * definition.
 *
 * @author Warkdev
 */
public class WDT extends FileReader {

    private static final Logger logger = LoggerFactory.getLogger(WDT.class);

    // Headers expected in the WDT.
    private static final String HEADER_MVER = "MVER";
    private static final String HEADER_MPHD = "MPHD";
    private static final String HEADER_MAIN = "MAIN";
    private static final String HEADER_MWMO = "MWMO";
    private static final String HEADER_MODF = "MODF";

    // Supported version of the WDT
    private static final int SUPPORTED_VERSION = 18;
    private static final int SIZE_AREA_INFO = 8;

    // Flag value to indicate whether it uses global map object or not. In case not, there's ADT (terrain information).
    public static final int FLAG_USE_GLOBAL_MAP_OBJ = 0x0001;

    // Map tile size for terrain information. Only one value as the tile is a square.
    public static final int MAP_TILE_SIZE = 64;

    private int version;

    private int flags;

    private List<AreaInfo> listAreas = new ArrayList<>();
    private String wmo;
    private MODF wmoPlacement = new MODF();
    private ADT[][] adtArray;
    private float liquidMinHeight;
    private float liquidMaxHeight;

    @Override
    public void init(MPQManager manager, String filename, boolean loadChildren) throws FileReaderException, JMpqException, MPQException, IOException {
        super.init = false;
        listAreas.clear();
        this.adtArray = new ADT[MAP_TILE_SIZE][MAP_TILE_SIZE];

        for (int i = 0; i < MAP_TILE_SIZE; i++) {
            for (int j = 0; j < MAP_TILE_SIZE; j++) {
                adtArray[i][j] = null;
            }
        }

        liquidMinHeight = Float.MAX_VALUE;
        liquidMaxHeight = -Float.MAX_VALUE;

        super.filename = filename;
        super.data = ByteBuffer.wrap(manager.getMPQForFile(filename).extractFileAsBytes(filename));
        super.data.order(ByteOrder.LITTLE_ENDIAN);

        int size;

        readVersion(super.data);

        if (version != SUPPORTED_VERSION) {
            throw new WDTException("The WDT file version is not supported (" + this.version + "), supported version: " + SUPPORTED_VERSION);
        }

        checkHeader(HEADER_MPHD);
        size = super.data.getInt();
        this.flags = super.data.getInt();

        // We just skip unused data.
        super.data.position(super.data.position() + (size - 4));

        checkHeader(HEADER_MAIN);
        size = super.data.getInt();

        if (size != MAP_TILE_SIZE * MAP_TILE_SIZE * SIZE_AREA_INFO) {
            throw new WDTException("The size for the ADT Map Tile is not the expected one. This file looks corrupted.");
        }

        AreaInfo info;
        for (int i = 0; i < MAP_TILE_SIZE * MAP_TILE_SIZE; i++) {
            info = new AreaInfo();
            info.read(super.data);
            listAreas.add(info);
        }

        checkHeader(HEADER_MWMO);
        size = super.data.getInt();

        if (useGlobalMapObj()) {
            // There should be one WMO definition with its placement information.
            wmo = readString(super.data);

            checkHeader(HEADER_MODF);
            size = super.data.getInt();
            this.wmoPlacement = new MODF();
            this.wmoPlacement.read(super.data);
        }

        if (loadChildren) {
            ADT adt;
            Point2D liquidMapBounds;
            String base = FilenameUtils.getPath(this.filename) + FilenameUtils.getBaseName(this.filename);
            int minX = MAP_TILE_SIZE;
            int minY = MAP_TILE_SIZE;
            int maxX = 0;
            int maxY = 0;
            for (int x = 0; x < MAP_TILE_SIZE; x++) {
                for (int y = 0; y < MAP_TILE_SIZE; y++) {
                    if (hasTerrain(x, y)) {
                        adt = new ADT();
                        adt.init(manager, base + "_" + y + "_" + x + ".adt", false);
                        adtArray[x][y] = adt;
                        liquidMapBounds = adt.getLiquidMapBounds();
                        if (liquidMapBounds.getX() > this.liquidMaxHeight) {
                            this.liquidMaxHeight = (float) liquidMapBounds.getX();
                        }
                        if (liquidMapBounds.getY() < this.liquidMinHeight) {
                            this.liquidMinHeight = (float) liquidMapBounds.getY();
                        }
                        if (x < minX) {
                            minX = x;
                        } else if (x > maxX) {
                            maxX = x;
                        }
                        if (y < minY) {
                            minY = y;
                        } else if (y > maxY) {
                            maxY = y;
                        }
                    }
                }
            }
            logger.debug("Boundaries: Min[x: " + minX + ", y: " + minY + "], Max[x: " + maxX + ", y: " + maxY + "]");
            // Now can trim the adtArray.            
            this.adtArray = ArrayUtils.subarray(this.adtArray, minX, maxX + 1);
            for (int x = 0; x < this.adtArray.length; x++) {
                this.adtArray[x] = ArrayUtils.subarray(this.adtArray[x], minY, maxY + 1);
            }
        }

        super.init = true;
    }

    /**
     * Indicates whether this WDT has only a WMO in it (as a dungeon).
     *
     * @return True if this WDT is only storing a WMO information or false if it
     * also has terrain information.
     */
    public boolean useGlobalMapObj() {
        return hasFlag(FLAG_USE_GLOBAL_MAP_OBJ);
    }

    private Pane renderTerrainTileMap() {
        Pane pane = new Pane();

        // ADT array has been trimmed and is empty.
        if (this.adtArray.length == 0 || this.adtArray[0].length == 0) {
            return pane;
        }

        Canvas canvas = new Canvas(this.adtArray.length, this.adtArray[0].length);
        PixelWriter pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();

        pane.setPrefHeight(this.adtArray[0].length);
        pane.setPrefWidth(this.adtArray.length);

        Color color;
        for (int x = 0; x < this.adtArray.length; x++) {
            for (int y = 0; y < this.adtArray[x].length; y++) {
                if (adtArray[x][y] != null) {
                    color = Color.DARKGOLDENROD;
                } else {
                    color = Color.BLACK;
                }
                pixelWriter.setColor(x, y, color);
            }
        }

        pane.getChildren().add(canvas);
        return pane;
    }

    private Pane renderMergedHoleMap() throws ModelRendererException, FileReaderException {
        Pane pane = new Pane();

        if(adtArray.length == 0) {
            return pane;
        }
        
        int adtSize = ADT.SIZE_TILE_MAP * ADT.CHUNK_TILE_MAP_LENGTH;
        Canvas canvas = new Canvas(adtSize * adtArray.length, adtSize * adtArray[0].length);
        pane.setPrefHeight(adtSize);
        pane.setPrefWidth(adtSize);
        pane.setMinHeight(pane.getPrefHeight());
        pane.setMinWidth(pane.getPrefWidth());
        pane.setMaxHeight(pane.getPrefHeight());
        pane.setMaxWidth(pane.getPrefWidth());
        PixelWriter pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();

        ADT adt;
        for (int x = 0; x < adtArray.length; x++) {
            for (int y = 0; y < adtArray[x].length; y++) {
                adt = adtArray[x][y];                
                if (adt != null) {
                    int offsetX = x * adtSize;
                    int offsetY = y * adtSize;
                    int idx = 0;
                    int idy = 0;
                    List<MCNK> mapChunks = adt.getMapChunks();
                    for (MCNK chunk : mapChunks) {
                        for (int i = 0; i < ADT.CHUNK_TILE_MAP_LENGTH; i++) {
                            for (int j = 0; j < ADT.CHUNK_TILE_MAP_LENGTH; j++) {
                                if (chunk.isHole(i, j)) {
                                    pixelWriter.setColor(offsetX + i + (idx * ADT.CHUNK_TILE_MAP_LENGTH), offsetY + j + (idy * ADT.CHUNK_TILE_MAP_LENGTH), Color.WHITE);
                                } else {
                                    pixelWriter.setColor(offsetX + i + (idx * ADT.CHUNK_TILE_MAP_LENGTH), offsetY + j + (idy * ADT.CHUNK_TILE_MAP_LENGTH), Color.BLACK);
                                }
                            }
                        }

                        idx++;
                        if (idx % 16 == 0) {
                            idx = 0;
                            idy++;
                        }
                    }                    
                }
            }
        }

        pane.getChildren().add(canvas);
        return pane;
    }

    private Pane renderMergedTileMap(Render2DType renderType) throws ModelRendererException, FileReaderException {
        Pane pane = new Pane();

        // ADT array has been trimmed and is empty.
        if (this.adtArray.length == 0 || this.adtArray[0].length == 0) {
            return pane;
        }

        int adtSize = ADT.SIZE_TILE_MAP * ChunkLiquidRenderer.LIQUID_FLAG_LENGTH;
        Canvas canvas = new Canvas(adtSize * this.adtArray.length, adtSize * this.adtArray[0].length);
        PixelWriter pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();

        pane.setPrefHeight(canvas.getHeight());
        pane.setPrefWidth(canvas.getWidth());
        pane.setMinHeight(pane.getPrefHeight());
        pane.setMaxHeight(pane.getPrefHeight());
        pane.setMinWidth(pane.getPrefWidth());
        pane.setMaxWidth(pane.getPrefWidth());

        ADT adt;
        List<MCNK> mapChunks;
        MCNK chunk;
        MCLQ liquid;

        for (int x = 0; x < adtArray.length; x++) {
            for (int y = 0; y < adtArray[x].length; y++) {
                adt = adtArray[x][y];
                //logger.debug("Rendering ADT x: " + x + ", y: " + y);
                if (adt != null) {
                    int offsetX = adtSize * x;
                    int offsetY = adtSize * y;
                    mapChunks = adt.getMapChunks();
                    for (int xx = 0; xx < ADT.SIZE_TILE_MAP; xx++) {
                        for (int yy = 0; yy < ADT.SIZE_TILE_MAP; yy++) {
                            chunk = mapChunks.get(xx * ADT.SIZE_TILE_MAP + yy);
                            if (chunk.hasNoLiquid()) {
                                canvas.getGraphicsContext2D().setFill(ChunkLiquidRenderer.COLOR_NONE);
                                canvas.getGraphicsContext2D().fillRect(offsetX + (xx * MCLQ.LIQUID_FLAG_LENGTH), offsetY + (yy * MCLQ.LIQUID_FLAG_LENGTH), MCLQ.LIQUID_FLAG_LENGTH, MCLQ.LIQUID_FLAG_LENGTH);
                            } else {
                                // Only the first layer (for now).
                                liquid = chunk.getListLiquids().get(0);
                                for (int i = 0; i < MCLQ.LIQUID_FLAG_LENGTH; i++) {
                                    for (int j = 0; j < MCLQ.LIQUID_FLAG_LENGTH; j++) {
                                        pixelWriter.setColor(offsetX + (xx * MCLQ.LIQUID_FLAG_LENGTH) + i, offsetY + (yy * MCLQ.LIQUID_FLAG_LENGTH) + j, liquid.getColorForLiquid(renderType, i, j));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        pane.getChildren().add(canvas);
        return pane;
    }

    private PolygonMesh renderTerrain(Map<String, M2> cache) {
        // todo.

        return shapeMesh;
    }

    private PolygonMesh renderLiquid() {
        // todo.

        return liquidMesh;
    }

    /**
     * Indicates whether the tile at the position row/col has a terrain
     * information (under the format of an ADT file) or not.
     *
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return True if the tile has a terrain information, false otherwise.
     */
    public boolean hasTerrain(int row, int col) {
        return this.listAreas.get(row * MAP_TILE_SIZE + col).hasADT();
    }

    // Getter & Setter.
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public List<AreaInfo> getListAreas() {
        return listAreas;
    }

    public void setListAreas(List<AreaInfo> listAreas) {
        this.listAreas = listAreas;
    }

    public String getWmo() {
        return wmo;
    }

    public void setWmo(String wmo) {
        this.wmo = wmo;
    }

    public MODF getWmoPlacement() {
        return wmoPlacement;
    }

    public void setWmoPlacement(MODF wmoPlacement) {
        this.wmoPlacement = wmoPlacement;
    }

    // Private methods.
    /**
     * Read the version of the file. If the file version doesn't match the
     * version header, it throws an exception.
     *
     * @param in The ByteBuffer from which the version needs to be read.
     * @throws WDTException If the expected header is not found.
     */
    private void readVersion(ByteBuffer in) throws WDTException {
        StringBuilder sb = new StringBuilder();
        byte[] header = new byte[4];

        in.get(header);

        sb = sb.append(new String(header)).reverse();
        if (!sb.toString().equals(HEADER_MVER)) {
            throw new WDTException("Expected header " + HEADER_MVER + ", received header: " + sb.toString());
        }

        // We skip the size as we know it's 4.
        in.getInt();
        this.version = in.getInt();
    }

    /**
     * Check whether the flag field has the bit at the position flag set or not.
     *
     * @param flag The position of the bit that must be checked.
     * @return True if the bit is set, false otherwise.
     */
    private boolean hasFlag(int flag) {
        return FlagUtils.hasFlag(this.flags, flag);
    }

    @Override
    public Pane render2D(Render2DType renderType) throws ModelRendererException, FileReaderException {
        if (super.init) {
            switch (renderType) {
                case RENDER_TILEMAP_TERRAIN:
                    return renderTerrainTileMap();
                case RENDER_TILEMAP_TERRAIN_HEIGHTMAP:
                case RENDER_TILEMAP_TERRAIN_HOLEMAP:
                    return renderMergedHoleMap();
                case RENDER_TILEMAP_LIQUID_TYPE:
                case RENDER_TILEMAP_LIQUID_HEIGHTMAP:
                case RENDER_TILEMAP_LIQUID_FISHABLE:
                case RENDER_TILEMAP_LIQUID_ANIMATED:
                    return renderMergedTileMap(renderType);
                default:
                    throw new UnsupportedOperationException("This render type is not supported.");
            }
        } else {
            throw new FileReaderException("This WDT has not been initialized yet.");
        }
    }

    @Override
    public PolygonMesh render3D(Render3DType renderType, Map<String, M2> cache) throws ModelRendererException, MPQException, FileReaderException {
        switch (renderType) {
            case LIQUID:
                return renderLiquid();
            case TERRAIN:
                return renderTerrain(cache);
            default:
                throw new UnsupportedOperationException("This render type is not supported.");
        }
    }
}
