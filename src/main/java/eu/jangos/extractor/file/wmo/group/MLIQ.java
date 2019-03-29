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
package eu.jangos.extractor.file.wmo.group;

import javafx.geometry.Point3D;
import eu.jangos.utils.FlagUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Warkdev
 */
public class MLIQ {
    
    // Used to decode flag value. It's guessed that the flag (one byte), is ordered as made as several meaning:
    // AA BB CC DD. Where DD is the liquid type except if CC = 11.    
    private static final int MASK_LIQUID = 0x03;
    private static final int FLAG_IS_WATER = 0x00;    
    private static final int FLAG_IS_OCEAN = 0x01;
    private static final int FLAG_IS_MAGMA = 0x02;
    private static final int FLAG_IS_SLIME = 0x03;
    private static final int FLAG_IS_ANIMATED = 0x04;
    private static final int MASK_NO_LIQUID = 0x0F;
    private static final int FLAG_E = 0x10;
    private static final int FLAG_F = 0x20;
    private static final int FLAG_FISHABLE = 0x40;
    private static final int FLAG_OVERLAP = 0x80;
    
    // Other flag values are unknown.

    private int xVerts;
    private int yVerts;
    private int xTiles;
    private int yTiles;
    private Point3D baseCoordinates;
    private short materialId;

    private List<WaterVert> liquidVertexList = new ArrayList<>();
    
    // Flag is only one byte but as java use signed numbers and wow unsigned one, it's stored as short.
    private List<Short> flags = new ArrayList<>();

    public void read(ByteBuffer data) {
        this.liquidVertexList.clear();
        this.flags.clear();

        this.xVerts = data.getInt();
        this.yVerts = data.getInt();
        this.xTiles = data.getInt();
        this.yTiles = data.getInt();
        this.baseCoordinates = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        this.materialId = data.getShort();

        WaterVert liquidVertex;
        for (int i = 0; i < this.xVerts * this.yVerts; i++) {
            liquidVertex = new WaterVert();
            liquidVertex.read(data);
            liquidVertexList.add(liquidVertex);
        }

        for (int i = 0; i < this.xTiles * this.yTiles; i++) {
            flags.add((short) Byte.toUnsignedInt(data.get()));
        }
    }

    public int getxVerts() {
        return xVerts;
    }

    public void setxVerts(int xVerts) {
        this.xVerts = xVerts;
    }

    public int getyVerts() {
        return yVerts;
    }

    public void setyVerts(int yVerts) {
        this.yVerts = yVerts;
    }

    public int getxTiles() {
        return xTiles;
    }

    public void setxTiles(int xTiles) {
        this.xTiles = xTiles;
    }

    public int getyTiles() {
        return yTiles;
    }

    public void setyTiles(int yTiles) {
        this.yTiles = yTiles;
    }

    public Point3D getBaseCoordinates() {
        return baseCoordinates;
    }

    public void setBaseCoordinates(Point3D baseCoordinates) {
        this.baseCoordinates = baseCoordinates;
    }

    public short getMaterialId() {
        return materialId;
    }

    public void setMaterialId(short materialId) {
        this.materialId = materialId;
    }

    public List<WaterVert> getLiquidVertexList() {
        return liquidVertexList;
    }

    public void setLiquidVertexList(List<WaterVert> liquidVertexList) {
        this.liquidVertexList = liquidVertexList;
    }

    public List<Short> getFlags() {
        return flags;
    }

    public void setFlags(List<Short> flags) {
        this.flags = flags;
    }

    public boolean hasNoLiquid(int row, int col) {
        return hasFlag(row, col, MASK_NO_LIQUID);
    }    
    
    public boolean isWater(int row, int col) {
        return !hasNoLiquid(row, col) && (this.flags.get(getFlagPosition(row, col)) & MASK_LIQUID) == FLAG_IS_WATER;
    }
    
    public boolean isOcean(int row, int col) {
        return !hasNoLiquid(row, col) && (this.flags.get(getFlagPosition(row, col)) & MASK_LIQUID) == FLAG_IS_OCEAN;
    }    
    
    public boolean isMagma(int row, int col) {
        return !hasNoLiquid(row, col) && (this.flags.get(getFlagPosition(row, col)) & MASK_LIQUID) == FLAG_IS_MAGMA;
    }
    
    public boolean isSlime(int row, int col) {
        return !hasNoLiquid(row, col) && (this.flags.get(getFlagPosition(row, col)) & MASK_LIQUID) == FLAG_IS_SLIME;
    }
    
    public boolean isAnimated(int row, int col) {
        return !hasNoLiquid(row, col) && hasFlag(row, col, FLAG_IS_ANIMATED);
    }
    
    public boolean isFlagESet(int row, int col) {
        return hasFlag(row, col, FLAG_E);
    }
    
    public boolean isFlagFSet(int row, int col) {
        return hasFlag(row, col, FLAG_F);
    }
    
    public boolean isFishable(int row, int col) {
        return !hasNoLiquid(row, col) && hasFlag(row, col, FLAG_FISHABLE);
    }
    
    public boolean isOverlap(int row, int col) {
        return hasFlag(row, col, FLAG_OVERLAP);
    }   
    
    private boolean hasFlag(int row, int col, int flag) {
        return FlagUtils.hasFlag(this.flags.get(getFlagPosition(row, col)), flag);
    }

    public WaterVert getVertextAt(int row, int col) {
        return this.liquidVertexList.get(getDataPosition(row, col));
    }
    
    private int getDataPosition(int row, int col) {
        return col * xVerts + row;
    }
    
    private int getFlagPosition(int row, int col) {
        return col * xTiles + row;
    }
}
