/* 
 * Copyright 2018 Warkdev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.jangos.extractor.file.adt.chunk;

import javafx.geometry.Point2D;
import eu.jangos.extractor.file.FileReader;
import eu.jangos.extractor.file.exception.FileReaderException;
import eu.jangos.extractor.file.exception.MPQException;
import eu.jangos.extractor.file.exception.ModelRendererException;
import eu.jangos.extractor.file.impl.M2;
import eu.jangos.extractor.file.mpq.MPQManager;
import eu.jangos.extractor.rendering.PolygonMesh;
import eu.jangos.extractor.rendering.Render2DType;
import eu.jangos.extractor.rendering.Render3DType;
import eu.jangos.utils.FlagUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

/**
 *
 * @author Warkdev
 */
public class MCNK extends FileReader {

    // Headers in MCNK Sub-chunk.
    private static final String HEADER_MCVT = "MCVT";
    private static final String HEADER_MCNR = "MCNR";
    private static final String HEADER_MCLY = "MCLY";
    private static final String HEADER_MCRF = "MCRF";
    private static final String HEADER_MCSH = "MCSH";
    private static final String HEADER_MCAL = "MCAL";
    private static final String HEADER_MCLQ = "MCLQ";
    private static final String HEADER_MCSE = "MCSE";

    // Flags in MCNK Sub-Chunk.
    public static final int FLAG_HAS_MCSH = 0x01;
    public static final int FLAG_IMPASS = 0x02;
    public static final int FLAG_RIVER = 0x04;
    public static final int FLAG_OCEAN = 0x08;
    public static final int FLAG_MAGMA = 0x10;
    public static final int FLAG_SLIME = 0x20;
    public static final int FLAG_HAS_MCCV = 0x40;
    public static final int FLAG_UNK1 = 0x80;
    public static final int FLAG_UNK2 = 0x100;
    public static final int FLAG_UNK3 = 0x200;
    public static final int FLAG_UNK4 = 0x400;
    public static final int FLAG_UNK5 = 0x800;
    public static final int FLAG_UNK6 = 0x1000;
    public static final int FLAG_UNK7 = 0x2000;
    public static final int FLAG_UNK8 = 0x4000;
    public static final int FLAG_UNK9 = 0x8000;
    public static final int FLAG_IS_HIGH_RES_HOLE = 0x10000;
    public static final int FLAG_UNK10 = 0x20000;
    public static final int FLAG_UNK11 = 0x40000;
    public static final int FLAG_UNK12 = 0x80000;
    public static final int FLAG_UNK13 = 0x100000;
    public static final int FLAG_UNK14 = 0x200000;
    public static final int FLAG_UNK15 = 0x400000;
    public static final int FLAG_UNK16 = 0x800000;
    public static final int FLAG_UNK17 = 0x1000000;
    public static final int FLAG_UNK18 = 0x2000000;
    public static final int FLAG_UNK19 = 0x4000000;
    public static final int FLAG_UNK20 = 0x8000000;
    public static final int FLAG_UNK21 = 0x10000000;
    public static final int FLAG_UNK22 = 0x20000000;
    public static final int FLAG_UNK23 = 0x40000000;
    public static final int FLAG_UNK24 = 0x80000000;

    private int flags;
    private int indexX;
    private int indexY;
    private int nbLayers;
    private int nDoodadRefs;
    private int offsetMCVT;
    private int offsetMCNR;
    private int offsetMCLY;
    private int offsetMCRF;
    private int offsetMCAL;
    private int sizeAlpha;
    private int offsetMCSH;
    private int sizeShadow;
    private int areadId;
    private int nMapObjRefs;
    private int holes;
    private byte[][] lowQualityTextMap = new byte[8][8];
    private int predTex;
    private int noEffectDoodad;
    private int offsetMCSE;
    private int nSndEmitters;
    private int offsetMCLQ;
    private int sizeLiquid;
    private Point3D position;
    private int offsetMCCV;
    private int offsetMCLV;
    private MCVT vertices = new MCVT();
    private MCNR normals = new MCNR();
    private List<MCLQ> listLiquids = new ArrayList<>();
    private MCLY[] textureLayers = new MCLY[4];
    private List<Integer> mcrfList = new ArrayList<>();

    public MCNK() {
        for (int i = 0; i < this.textureLayers.length; i++) {
            textureLayers[i] = new MCLY();
        }
    }

    @Override
    public void init(MPQManager manager, String filename, boolean loadChildren) throws IOException, FileReaderException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(ByteBuffer in) throws FileReaderException {
        super.data = in;

        int size;
        int start;

        this.flags = super.data.getInt();
        this.indexX = super.data.getInt();
        this.indexY = super.data.getInt();
        this.nbLayers = super.data.getInt();
        this.nDoodadRefs = super.data.getInt();
        this.offsetMCVT = super.data.getInt();
        this.offsetMCNR = super.data.getInt();
        this.offsetMCLY = super.data.getInt();
        this.offsetMCRF = super.data.getInt();
        this.offsetMCAL = super.data.getInt();
        this.sizeAlpha = super.data.getInt();
        this.offsetMCSH = super.data.getInt();
        this.sizeShadow = super.data.getInt();
        this.areadId = super.data.getInt();
        this.nMapObjRefs = super.data.getInt();
        this.holes = super.data.getInt();

        for (int j = 0; j < 16; j++) {
            // Skipping low quality text map for now. (64 bytes)
            super.data.get();
        }

        this.predTex = super.data.getInt();
        this.noEffectDoodad = super.data.getInt();
        this.offsetMCSE = super.data.getInt();
        this.nSndEmitters = super.data.getInt();
        this.offsetMCLQ = super.data.getInt();
        this.sizeLiquid = super.data.getInt();
        this.position = new Point3D(super.data.getFloat(), super.data.getFloat(), super.data.getFloat());        
        this.offsetMCCV = super.data.getInt();
        this.offsetMCLV = super.data.getInt();

        // Unused
        super.data.getInt();

        // Must now parse MCVT            
        checkHeader(HEADER_MCVT);
        // We ignore size.
        super.data.getInt();
        this.vertices.read(super.data);

        // Must now parse MCNR
        checkHeader(HEADER_MCNR);
        // We ignore size.
        super.data.getInt();
        this.normals.read(super.data);

        // 13 unknown bytes at the end of normals:
        for (int j = 0; j < 13; j++) {
            super.data.get();
        }

        // Must now parse MCLY.
        checkHeader(HEADER_MCLY);

        // We ignore size.
        super.data.getInt();
        for (int j = 0; j < this.nbLayers; j++) {
            this.textureLayers[j].read(super.data);
        }

        // Must now parse MCRF.
        checkHeader(HEADER_MCRF);

        size = super.data.getInt();
        start = super.data.position();
        while (super.data.position() - start < size) {
            this.mcrfList.add(super.data.getInt());
        }

        // Must now parse MCSH.
        checkHeader(HEADER_MCSH);

        size = super.data.getInt();
        for (int j = 0; j < size; j++) {
            super.data.get();
        }

        // Must now parse MCAL.
        checkHeader(HEADER_MCAL);

        size = super.data.getInt();
        for (int j = 0; j < size; j++) {
            super.data.get();
        }

        // Must now parse MCLQ.
        checkHeader(HEADER_MCLQ);

        size = super.data.getInt();
        // Then we skip the "size field" as it's always 0.

        // Documentation is spread over several codebase, none really figuring out what it is properly.
        // Thanks for Mangos/CMangos codebase on which this is based.            
        if (hasLiquid()) {
            MCLQ liquid;
            // MCLQ can be made of several layers. It's assumed (guessed) that MCLQ are ordered by liquid type in the ADT.
            if (isRiver()) {
                liquid = new MCLQ();
                liquid.read(super.data);
                this.listLiquids.add(liquid);
            }
            if (isOcean()) {
                liquid = new MCLQ();
                liquid.read(super.data);
                this.listLiquids.add(liquid);
            }
            if (isMagma()) {
                liquid = new MCLQ();
                liquid.read(super.data);
                this.listLiquids.add(liquid);
            }
            if (isSlime()) {
                liquid = new MCLQ();
                liquid.read(super.data);
                this.listLiquids.add(liquid);
            }
        }

        // Must now parse MCSE.
        checkHeader(HEADER_MCSE);

        // Flag value not well documented.
        super.data.getInt();
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getIndexX() {
        return indexX;
    }

    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }

    public int getNbLayers() {
        return nbLayers;
    }

    public void setNbLayers(int nbLayers) {
        this.nbLayers = nbLayers;
    }

    public int getnDoodadRefs() {
        return nDoodadRefs;
    }

    public void setnDoodadRefs(int nDoodadRefs) {
        this.nDoodadRefs = nDoodadRefs;
    }

    public int getOffsetMCVT() {
        return offsetMCVT;
    }

    public void setOffsetMCVT(int offsetMCVT) {
        this.offsetMCVT = offsetMCVT;
    }

    public int getOffsetMCNR() {
        return offsetMCNR;
    }

    public void setOffsetMCNR(int offsetMCNR) {
        this.offsetMCNR = offsetMCNR;
    }

    public int getOffsetMCLY() {
        return offsetMCLY;
    }

    public void setOffsetMCLY(int offsetMCLY) {
        this.offsetMCLY = offsetMCLY;
    }

    public int getOffsetMCRF() {
        return offsetMCRF;
    }

    public void setOffsetMCRF(int offsetMCRF) {
        this.offsetMCRF = offsetMCRF;
    }

    public int getOffsetMCAL() {
        return offsetMCAL;
    }

    public void setOffsetMCAL(int offsetMCAL) {
        this.offsetMCAL = offsetMCAL;
    }

    public int getSizeAlpha() {
        return sizeAlpha;
    }

    public void setSizeAlpha(int sizeAlpha) {
        this.sizeAlpha = sizeAlpha;
    }

    public int getOffsetMCSH() {
        return offsetMCSH;
    }

    public void setOffsetMCSH(int offsetMCSH) {
        this.offsetMCSH = offsetMCSH;
    }

    public int getSizeShadow() {
        return sizeShadow;
    }

    public void setSizeShadow(int sizeShadow) {
        this.sizeShadow = sizeShadow;
    }

    public int getAreadId() {
        return areadId;
    }

    public void setAreadId(int areadId) {
        this.areadId = areadId;
    }

    public int getnMapObjRefs() {
        return nMapObjRefs;
    }

    public void setnMapObjRefs(int nMapObjRefs) {
        this.nMapObjRefs = nMapObjRefs;
    }

    public int getHoles() {
        return holes;
    }

    public void setHoles(int holes) {
        this.holes = holes;
    }

    public byte[][] getLowQualityTextMap() {
        return lowQualityTextMap;
    }

    public void setLowQualityTextMap(byte[][] lowQualityTextMap) {
        this.lowQualityTextMap = lowQualityTextMap;
    }

    public int getPredTex() {
        return predTex;
    }

    public void setPredTex(int predTex) {
        this.predTex = predTex;
    }

    public int getNoEffectDoodad() {
        return noEffectDoodad;
    }

    public void setNoEffectDoodad(int noEffectDoodad) {
        this.noEffectDoodad = noEffectDoodad;
    }

    public int getOffsetMCSE() {
        return offsetMCSE;
    }

    public void setOffsetMCSE(int offsetMCSE) {
        this.offsetMCSE = offsetMCSE;
    }

    public int getnSndEmitters() {
        return nSndEmitters;
    }

    public void setnSndEmitters(int nSndEmitters) {
        this.nSndEmitters = nSndEmitters;
    }

    public int getOffsetMCLQ() {
        return offsetMCLQ;
    }

    public void setOffsetMCLQ(int offsetMCLQ) {
        this.offsetMCLQ = offsetMCLQ;
    }

    public int getSizeLiquid() {
        return sizeLiquid;
    }

    public void setSizeLiquid(int sizeLiquid) {
        this.sizeLiquid = sizeLiquid;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public int getOffsetMCCV() {
        return offsetMCCV;
    }

    public void setOffsetMCCV(int offsetMCCV) {
        this.offsetMCCV = offsetMCCV;
    }

    public int getOffsetMCLV() {
        return offsetMCLV;
    }

    public void setOffsetMCLV(int offsetMCLV) {
        this.offsetMCLV = offsetMCLV;
    }

    public MCVT getVertices() {
        return vertices;
    }

    public void setVertices(MCVT vertices) {
        this.vertices = vertices;
    }

    public MCNR getNormals() {
        return normals;
    }

    public void setNormals(MCNR normals) {
        this.normals = normals;
    }

    public MCLY[] getTextureLayers() {
        return textureLayers;
    }

    public void setTextureLayers(MCLY[] textureLayers) {
        this.textureLayers = textureLayers;
    }

    public List<Integer> getMcrfList() {
        return mcrfList;
    }

    public void setMcrfList(List<Integer> mcrfList) {
        this.mcrfList = mcrfList;
    }

    public List<MCLQ> getListLiquids() {
        return listLiquids;
    }

    public void setListLiquids(List<MCLQ> listLiquids) {
        this.listLiquids = listLiquids;
    }

    public boolean hasMCSH() {
        return FlagUtils.hasFlag(this.flags, FLAG_HAS_MCSH);
    }

    public boolean isImpass() {
        return FlagUtils.hasFlag(this.flags, FLAG_IMPASS);
    }

    public boolean hasLiquid() {
        return isRiver() || isOcean() || isMagma() || isSlime();
    }

    public boolean hasNoLiquid() {
        return !hasLiquid();
    }

    public boolean isRiver() {
        return FlagUtils.hasFlag(flags, FLAG_RIVER);
    }

    public boolean isOcean() {
        return FlagUtils.hasFlag(flags, FLAG_OCEAN);
    }

    public boolean isMagma() {
        return FlagUtils.hasFlag(flags, FLAG_MAGMA);
    }

    public boolean isSlime() {
        return FlagUtils.hasFlag(flags, FLAG_SLIME);
    }

    public boolean hasMCCV() {
        return FlagUtils.hasFlag(this.flags, FLAG_HAS_MCCV);
    }

    public boolean isUnknown1() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK1);
    }

    public boolean isUnknown2() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK2);
    }

    public boolean isUnknown3() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK3);
    }

    public boolean isUnknown4() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK4);
    }

    public boolean isUnknown5() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK5);
    }

    public boolean isUnknown6() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK6);
    }

    public boolean isUnknown7() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK7);
    }

    public boolean isUnknown8() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK8);
    }

    public boolean isUnknown9() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK9);
    }

    public boolean isHighResHole() {
        return FlagUtils.hasFlag(this.flags, FLAG_IS_HIGH_RES_HOLE);
    }

    public boolean isLowResHole() {
        return !isHighResHole();
    }

    public boolean isUnknown10() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK10);
    }

    public boolean isUnknown11() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK11);
    }

    public boolean isUnknown12() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK12);
    }

    public boolean isUnknown13() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK13);
    }

    public boolean isUnknown14() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK14);
    }

    public boolean isUnknown15() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK15);
    }

    public boolean isUnknown16() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK16);
    }

    public boolean isUnknown17() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK17);
    }

    public boolean isUnknown18() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK18);
    }

    public boolean isUnknown19() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK19);
    }

    public boolean isUnknown20() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK20);
    }

    public boolean isUnknown21() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK21);
    }

    public boolean isUnknown22() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK22);
    }

    public boolean isUnknown23() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK23);
    }

    public boolean isUnknown24() {
        return FlagUtils.hasFlag(this.flags, FLAG_UNK24);
    }

    /**
     * Check in the bitmap hole if there's a hole at the position x, y.
     *
     * @param x The x position to be checked.
     * @param y The y position to be checked.
     * @return True if there's a hole.
     */
    public boolean isHole(int x, int y) {
        int flagHole = (int) Math.pow(2, Math.floor(x / 2f) * 1f + Math.floor(y / 2f) * 4f);
        return FlagUtils.hasFlag(this.holes, flagHole);
    }

    /**
     * Provide a 2-float Vector representing the maximum and the minimum liquid
     * height found in this chunk. Point2D.x contains the Maximum Height while
     * Point2D.y contains the Minimum Height.
     *
     * @return A Point2D object containing the maximum and the minimum liquid
     * height in this chunk. If there's no liquid in this chunk, the returned
     * values are -Float.MAX_VALUE as maximum and Float.MAX_VALUE as minimum.
     */
    public Point2D getLiquidHeightBounds() {
        Point2D heightBounds = new Point2D(-Float.MAX_VALUE, Float.MAX_VALUE);
        float height;
        if (hasLiquid()) {
            for (MCLQ liquid : this.listLiquids) {
                for (int x = 0; x < MCLQ.LIQUID_DATA_LENGTH; x++) {
                    for (int y = 0; y < MCLQ.LIQUID_DATA_LENGTH; y++) {
                        //height = liquid.getHeightAt(x, y);
                        height = 0;
                        if (height == Float.MAX_VALUE) {
                            continue;
                        }
                        if (heightBounds.getX() < height) {
                            
                            heightBounds = new Point2D(height, heightBounds.getY());
                        }
                        if (heightBounds.getY() > height) {
                            heightBounds = new Point2D(heightBounds.getX(), height);
                        }
                    }
                }
            }
        }
        return heightBounds;
    }

    private Pane renderLiquid(Render2DType renderType) throws ModelRendererException, FileReaderException {
        if (hasNoLiquid()) {
            // Full grid is empty.            
            Canvas canvas = new Canvas(MCLQ.LIQUID_FLAG_LENGTH, MCLQ.LIQUID_FLAG_LENGTH);

            canvas.getGraphicsContext2D().setFill(MCLQ.COLOR_NONE);
            canvas.getGraphicsContext2D().fillRect(0.0, 0.0, (double) MCLQ.LIQUID_FLAG_LENGTH, (double) MCLQ.LIQUID_FLAG_LENGTH);
            return new Pane(canvas);
        } else {
            return this.listLiquids.get(0).render2D(renderType);
        }
    }

    @Override
    public Pane render2D(Render2DType renderType) throws ModelRendererException, FileReaderException {
        switch (renderType) {
            case RENDER_TILEMAP_LIQUID_TYPE:
            case RENDER_TILEMAP_LIQUID_HEIGHTMAP:
            case RENDER_TILEMAP_LIQUID_FISHABLE:
            case RENDER_TILEMAP_LIQUID_ANIMATED:
                return renderLiquid(renderType);
            default:
                throw new UnsupportedOperationException("These render types are not yet supported");
        }
    }

    @Override
    public PolygonMesh render3D(Render3DType renderType, Map<String, M2> cache) throws ModelRendererException, MPQException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
