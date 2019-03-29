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
import javafx.geometry.Point3D;
import eu.jangos.extractor.file.common.CAaBspNode;
import eu.jangos.extractor.file.common.CImVector;
import eu.jangos.extractor.file.common.MapUnit;
import eu.jangos.extractor.file.exception.MPQException;
import eu.jangos.extractor.file.exception.WMOException;
import eu.jangos.extractor.file.mpq.MPQManager;
import eu.jangos.extractor.file.wmo.group.MLIQ;
import eu.jangos.extractor.file.wmo.group.MOBA;
import eu.jangos.extractor.file.wmo.group.MOGP;
import eu.jangos.extractor.file.wmo.group.MOPY;
import eu.jangos.utils.FlagUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import systems.crigges.jmpq3.JMpqException;

/**
 *
 * @author Warkdev
 */
public class WMOGroup {

    // Section for WMO Group File.
    private static final int SUPPORTED_GROUP_VERSION = 17;

    private static final String HEADER_MVER = "MVER";
    private static final String HEADER_MOGP = "MOGP";
    private static final String HEADER_MOPY = "MOPY";
    private static final String HEADER_MOVI = "MOVI";
    private static final String HEADER_MOVT = "MOVT";
    private static final String HEADER_MONR = "MONR";
    private static final String HEADER_MOTV = "MOTV";
    private static final String HEADER_MOBA = "MOBA";
    private static final String HEADER_MOLR = "MOLR";
    private static final String HEADER_MODR = "MODR";
    private static final String HEADER_MOBN = "MOBN";
    private static final String HEADER_MOBR = "MOBR";
    private static final String HEADER_MOCV = "MOCV";
    private static final String HEADER_MLIQ = "MLIQ";
    private static final String HEADER_MORI = "MORI";

    // Size of one record for the given chunk.
    private static final int SIZE_MOPY = 2;
    private static final int SIZE_MOVI = 2;
    private static final int SIZE_MOVT = 12;
    private static final int SIZE_MONR = 12;
    private static final int SIZE_MOTV = 8;
    private static final int SIZE_MOBA = 24;
    private static final int SIZE_MOLR = 2;
    private static final int SIZE_MODR = 2;
    private static final int SIZE_MOBN = 16;
    private static final int SIZE_MOBR = 2;
    private static final int SIZE_MOCV = 4;
    private static final int SIZE_MORI = 2;

    // Flag values
    public static final long FLAG_HAS_BSP_TREE = 0x1;
    public static final long FLAG_HAS_LIGHT_MAP = 0x2;
    public static final long FLAG_HAS_VERTEX_COLORS = 0x4;
    public static final long FLAG_IS_EXTERIOR = 0x8;
    public static final long FLAG_UNUSED_1 = 0x10;
    public static final long FLAG_UNUSED_2 = 0x20;
    public static final long FLAG_EXTERIOR_LIT = 0x40;
    public static final long FLAG_UNREACHABLE = 0x80;
    public static final long FLAG_UNUSED_3 = 0x100;
    public static final long FLAG_HAS_LIGHT = 0x200;
    public static final long FLAG_UNUSED_4 = 0x400;
    public static final long FLAG_HAS_DOODADS = 0x800;
    public static final long FLAG_HAS_LIQUID = 0x1000;
    public static final long FLAG_IS_INTERIOR = 0x2000;
    public static final long FLAG_UNUSED_5 = 0x4000;
    public static final long FLAG_UNUSED_6 = 0x8000;
    public static final long FLAG_ALWAYS_DRAW = 0x10000;
    public static final long FLAG_HAS_TRIANGLESTRIP = 0x20000;
    public static final long FLAG_SHOW_SKYBOX = 0x40000;
    public static final long FLAG_IS_OCEAN = 0x80000;
    public static final long FLAG_UNUSED_8 = 0x100000;
    public static final long FLAG_IS_MOUNT_ALLOWED = 0x200000;
    public static final long FLAG_UNUSED_9 = 0x400000;
    public static final long FLAG_UNUSED_10 = 0x800000;
    public static final long FLAG_HAS_2_MOCV = 0x1000000;
    public static final long FLAG_HAS_2_MOTV = 0x2000000;
    public static final long FLAG_ANTIPORTAL = 0x4000000;
    public static final long FLAG_UNUSED_11 = 0x8000000;
    public static final long FLAG_UNUSED_12 = 0x10000000;
    public static final long FLAG_EXTERIOR_CULL = 0x20000000;
    public static final long FLAG_HAS_3_MOTV = 0x40000000;
    public static final long FLAG_UNUSED_13 = 0x80000000;

    private ByteBuffer data;
    private String filename;

    private MOGP group = new MOGP();
    private List<MOPY> materialsList = new ArrayList<>();
    private List<Short> indexList = new ArrayList<>();
    private List<Point3D> vertexList = new ArrayList<>();
    private List<Point3D> normalList = new ArrayList<>();
    private List<Point2D> textureVertexList = new ArrayList<>();
    private List<MOBA> batchList = new ArrayList<>();
    private List<Short> lightRefList = new ArrayList<>();
    private List<Short> doodadRefList = new ArrayList<>();
    private List<CAaBspNode> bspTreeList = new ArrayList<>();
    private List<Short> nodeFaceIndices = new ArrayList<>();
    private List<CImVector> colorVertexList = new ArrayList<>();
    private MLIQ liquid = new MLIQ();
    private List<Short> triangleStripIndices = new ArrayList<>();

    // Liquid render data.
    private List<Short> liquidIndicesList = new ArrayList<>();
    private List<Point3D> liquidVerticesList = new ArrayList<>();
    private List<Point2D> liquidTexCoordList = new ArrayList<>();

    public void init(MPQManager manager, String filename) throws WMOException, MPQException, JMpqException {
        this.data = ByteBuffer.wrap(manager.getMPQForFile(filename).extractFileAsBytes(filename));
        this.data.order(ByteOrder.LITTLE_ENDIAN);
        this.filename = filename;

        checkHeader(HEADER_MVER);
        // Let's skip size.
        this.data.getInt();

        int version = this.data.getInt();
        if (version != SUPPORTED_GROUP_VERSION) {
            throw new WMOException("Group file version is not supported, supported version: " + SUPPORTED_GROUP_VERSION + ", actual version: " + version);
        }

        int chunkSize = 0;
        clear();

        checkHeader(HEADER_MOGP);
        // Skip size.
        chunkSize = this.data.getInt();
        this.group.read(data);

        checkHeader(HEADER_MOPY);
        chunkSize = data.getInt() / SIZE_MOPY;
        MOPY material;
        for (int i = 0; i < chunkSize; i++) {
            material = new MOPY();
            material.read(data);
            this.materialsList.add(material);
        }

        checkHeader(HEADER_MOVI);
        chunkSize = data.getInt() / SIZE_MOVI;
        for (int i = 0; i < chunkSize; i++) {
            indexList.add(data.getShort());
        }

        checkHeader(HEADER_MOVT);
        chunkSize = data.getInt() / SIZE_MOVT;
        for (int i = 0; i < chunkSize; i++) {
            vertexList.add(new Point3D(data.getFloat(), data.getFloat(), data.getFloat()));
        }

        checkHeader(HEADER_MONR);
        chunkSize = data.getInt() / SIZE_MONR;
        for (int i = 0; i < chunkSize; i++) {
            normalList.add(new Point3D(data.getFloat(), data.getFloat(), data.getFloat()));
        }

        checkHeader(HEADER_MOTV);
        chunkSize = data.getInt() / SIZE_MOTV;
        for (int i = 0; i < chunkSize; i++) {
            textureVertexList.add(new Point2D(data.getFloat(), data.getFloat()));
        }

        checkHeader(HEADER_MOBA);
        chunkSize = data.getInt() / SIZE_MOBA;
        MOBA batch;
        for (int i = 0; i < chunkSize; i++) {
            batch = new MOBA();
            batch.read(data);
            batchList.add(batch);
        }

        if (hasLight()) {
            checkHeader(HEADER_MOLR);
            chunkSize = data.getInt() / SIZE_MOLR;
            for (int i = 0; i < chunkSize; i++) {
                lightRefList.add(data.getShort());
            }
        }

        if (hasDoodad()) {
            checkHeader(HEADER_MODR);
            chunkSize = data.getInt() / SIZE_MODR;
            for (int i = 0; i < chunkSize; i++) {
                doodadRefList.add(data.getShort());
            }
        }

        if (hasBSPTree()) {
            checkHeader(HEADER_MOBN);
            chunkSize = data.getInt() / SIZE_MOBN;
            CAaBspNode node;
            for (int i = 0; i < chunkSize; i++) {
                node = new CAaBspNode();
                node.read(data);
                bspTreeList.add(node);
            }

            checkHeader(HEADER_MOBR);
            chunkSize = data.getInt() / SIZE_MOBR;
            for (int i = 0; i < chunkSize; i++) {
                nodeFaceIndices.add(data.getShort());
            }
        }

        if (hasVertexColors()) {
            checkHeader(HEADER_MOCV);
            chunkSize = data.getInt();
            if (data.remaining() < chunkSize) {
                // Avoid bufferoverflow while reading some files. Undercity_144.wmo seems to be the only one.
                chunkSize /= SIZE_MOCV;
                chunkSize--;
            } else {
                chunkSize /= SIZE_MOCV;
            }
            CImVector vector;
            for (int i = 0; i < chunkSize; i++) {
                vector = new CImVector();
                vector.read(data);
                colorVertexList.add(vector);
            }
        }

        if (hasLiquid()) {
            checkHeader(HEADER_MLIQ);
            chunkSize = data.getInt();
            liquid.read(data);

            // Generating Liquid 3D. 
            // Starting with vertices.   
            List<Point3D> tempVertices = new ArrayList<>();
            for (int x = 0; x < liquid.getxVerts(); x++) {
                for (int y = 0; y < liquid.getyVerts(); y++) {                    
                    tempVertices.add(new Point3D(liquid.getBaseCoordinates().getX() + MapUnit.UNIT_SIZE * x, 
                            liquid.getBaseCoordinates().getY() + MapUnit.UNIT_SIZE * y, 
                            liquid.getBaseCoordinates().getZ() + liquid.getVertextAt(x, y).getHeight()));
                }
            }

            // Then with indices & texture coord.
            short index = 0;
            int pos;            
            for (int x = 0; x < liquid.getxTiles(); x++) {
                for (int y = 0; y < liquid.getyTiles(); y++) {
                    if (!liquid.hasNoLiquid(x, y)) {
                        pos = x * (liquid.getyVerts()) + y;
                        
                        this.liquidTexCoordList.add(new Point2D(x, y + 1));                                                      
                        this.liquidVerticesList.add(tempVertices.get(pos + 1));
                        this.liquidIndicesList.add(index++);                                                
                        
                        this.liquidTexCoordList.add(new Point2D(x, y));
                        this.liquidVerticesList.add(tempVertices.get(pos));
                        this.liquidIndicesList.add(index++);                                                                                                                                                                                                
                        
                        this.liquidTexCoordList.add(new Point2D(x + 1, y));                              
                        this.liquidVerticesList.add(tempVertices.get(pos + liquid.getyVerts() + 1));
                        this.liquidIndicesList.add(index++);
                        
                        this.liquidTexCoordList.add(new Point2D(x + 1, y + 1));                        
                        this.liquidVerticesList.add(tempVertices.get(pos + liquid.getyVerts()));                        
                        this.liquidIndicesList.add(index++);
                    }
                }
            }
        }

        if (hasTriangleStrip()) {
            checkHeader(HEADER_MORI);
            chunkSize = data.getInt() / SIZE_MORI;
            for (int i = 0; i < chunkSize; i++) {
                triangleStripIndices.add(data.getShort());
            }
        }
    }

    private boolean hasFlag(long flag) {
        return FlagUtils.hasFlag(this.group.getFlags(), flag);
    }

    public boolean hasLight() {
        return hasFlag(FLAG_HAS_LIGHT);
    }

    public boolean hasLiquid() {
        return hasFlag(FLAG_HAS_LIQUID);
    }

    public boolean hasDoodad() {
        return hasFlag(FLAG_HAS_DOODADS);
    }

    public boolean hasBSPTree() {
        return hasFlag(FLAG_HAS_BSP_TREE);
    }

    public boolean hasVertexColors() {
        return hasFlag(FLAG_HAS_VERTEX_COLORS);
    }

    public boolean hasTriangleStrip() {
        return hasFlag(FLAG_HAS_TRIANGLESTRIP);
    }

    public boolean isOcean() {
        return hasFlag(FLAG_IS_OCEAN);
    }

    public ByteBuffer getData() {
        return data;
    }

    public void setData(ByteBuffer data) {
        this.data = data;
    }

    public MOGP getGroup() {
        return group;
    }

    public void setGroup(MOGP group) {
        this.group = group;
    }

    public List<MOPY> getMaterialsList() {
        return materialsList;
    }

    public void setMaterialsList(List<MOPY> materialsList) {
        this.materialsList = materialsList;
    }

    public List<Short> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<Short> indexList) {
        this.indexList = indexList;
    }

    public List<Point3D> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Point3D> vertexList) {
        this.vertexList = vertexList;
    }

    public List<Point3D> getNormalList() {
        return normalList;
    }

    public void setNormalList(List<Point3D> normalList) {
        this.normalList = normalList;
    }

    public List<Point2D> getTextureVertexList() {
        return textureVertexList;
    }

    public void setTextureVertexList(List<Point2D> textureVertexList) {
        this.textureVertexList = textureVertexList;
    }

    public List<MOBA> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<MOBA> batchList) {
        this.batchList = batchList;
    }

    public List<Short> getLightRefList() {
        return lightRefList;
    }

    public void setLightRefList(List<Short> lightRefList) {
        this.lightRefList = lightRefList;
    }

    public List<Short> getDoodadRefList() {
        return doodadRefList;
    }

    public void setDoodadRefList(List<Short> doodadRefList) {
        this.doodadRefList = doodadRefList;
    }

    public List<CAaBspNode> getBspTreeList() {
        return bspTreeList;
    }

    public void setBspTreeList(List<CAaBspNode> bspTreeList) {
        this.bspTreeList = bspTreeList;
    }

    public List<Short> getNodeFaceIndices() {
        return nodeFaceIndices;
    }

    public void setNodeFaceIndices(List<Short> nodeFaceIndices) {
        this.nodeFaceIndices = nodeFaceIndices;
    }

    public List<CImVector> getColorVertexList() {
        return colorVertexList;
    }

    public void setColorVertexList(List<CImVector> colorVertexList) {
        this.colorVertexList = colorVertexList;
    }

    public MLIQ getLiquid() {
        return liquid;
    }

    public void setLiquid(MLIQ liquid) {
        this.liquid = liquid;
    }

    public List<Short> getTriangleStripIndices() {
        return triangleStripIndices;
    }

    public void setTriangleStripIndices(List<Short> triangleStripIndices) {
        this.triangleStripIndices = triangleStripIndices;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Short> getLiquidIndicesList() {
        return liquidIndicesList;
    }

    public void setLiquidIndicesList(List<Short> liquidIndicesList) {
        this.liquidIndicesList = liquidIndicesList;
    }

    public List<Point3D> getLiquidVerticesList() {
        return liquidVerticesList;
    }

    public void setLiquidVerticesList(List<Point3D> liquidVerticesList) {
        this.liquidVerticesList = liquidVerticesList;
    }

    public List<Point2D> getLiquidTexCoordList() {
        return liquidTexCoordList;
    }

    public void setLiquidTexCoordList(List<Point2D> liquidTexCoordList) {
        this.liquidTexCoordList = liquidTexCoordList;
    }

    private void checkHeader(String expectedHeader) throws WMOException {
        StringBuilder sb = new StringBuilder();
        byte[] header = new byte[4];

        data.get(header);

        sb = sb.append(new String(header)).reverse();
        if (!sb.toString().equals(expectedHeader)) {
            throw new WMOException(this.filename + " - Expected header " + expectedHeader + ", received header: " + sb.toString());
        }
    }

    private void clear() {
        materialsList.clear();
        indexList.clear();
        vertexList.clear();
        normalList.clear();
        textureVertexList.clear();
        batchList.clear();
        lightRefList.clear();
        doodadRefList.clear();
        bspTreeList.clear();
        nodeFaceIndices.clear();
        colorVertexList.clear();
        triangleStripIndices.clear();
        liquidIndicesList.clear();
        liquidTexCoordList.clear();
        liquidVerticesList.clear();
    }

}
