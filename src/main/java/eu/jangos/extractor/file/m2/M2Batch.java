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
package eu.jangos.extractor.file.m2;

import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class M2Batch {
    private byte flags;
    private byte priorityPlane;
    private short shaderId;
    private short skinSectionIndex;
    private short geosetIndex;
    private short colorIndex;
    private short materialIndex;
    private short materialLayer;
    private short textureCount;
    private short textureComboIndex;
    private short textureCoordComboIndex;
    private short textureWeightComboIndex;
    private short textureTransformComboIndex;

    public M2Batch() {
    }

    public void read(ByteBuffer data) {
        this.flags = data.get();
        this.priorityPlane = data.get();
        this.shaderId = data.getShort();
        this.skinSectionIndex = data.getShort();
        this.geosetIndex = data.getShort();
        this.colorIndex = data.getShort();
        this.materialIndex = data.getShort();
        this.materialLayer = data.getShort();
        this.textureCount = data.getShort();
        this.textureComboIndex = data.getShort();
        this.textureCoordComboIndex = data.getShort();
        this.textureWeightComboIndex = data.getShort();
        this.textureTransformComboIndex = data.getShort();
    }
    
    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public byte getPriorityPlane() {
        return priorityPlane;
    }

    public void setPriorityPlane(byte priorityPlane) {
        this.priorityPlane = priorityPlane;
    }

    public short getShaderId() {
        return shaderId;
    }

    public void setShaderId(short shaderId) {
        this.shaderId = shaderId;
    }

    public short getSkinSectionIndex() {
        return skinSectionIndex;
    }

    public void setSkinSectionIndex(short skinSectionIndex) {
        this.skinSectionIndex = skinSectionIndex;
    }

    public short getGeosetIndex() {
        return geosetIndex;
    }

    public void setGeosetIndex(short geosetIndex) {
        this.geosetIndex = geosetIndex;
    }

    public short getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(short colorIndex) {
        this.colorIndex = colorIndex;
    }

    public short getMaterialIndex() {
        return materialIndex;
    }

    public void setMaterialIndex(short materialIndex) {
        this.materialIndex = materialIndex;
    }

    public short getMaterialLayer() {
        return materialLayer;
    }

    public void setMaterialLayer(short materialLayer) {
        this.materialLayer = materialLayer;
    }

    public short getTextureCount() {
        return textureCount;
    }

    public void setTextureCount(short textureCount) {
        this.textureCount = textureCount;
    }

    public short getTextureComboIndex() {
        return textureComboIndex;
    }

    public void setTextureComboIndex(short textureComboIndex) {
        this.textureComboIndex = textureComboIndex;
    }

    public short getTextureCoordComboIndex() {
        return textureCoordComboIndex;
    }

    public void setTextureCoordComboIndex(short textureCoordComboIndex) {
        this.textureCoordComboIndex = textureCoordComboIndex;
    }

    public short getTextureWeightComboIndex() {
        return textureWeightComboIndex;
    }

    public void setTextureWeightComboIndex(short textureWeightComboIndex) {
        this.textureWeightComboIndex = textureWeightComboIndex;
    }

    public short getTextureTransformComboIndex() {
        return textureTransformComboIndex;
    }

    public void setTextureTransformComboIndex(short textureTransformComboIndex) {
        this.textureTransformComboIndex = textureTransformComboIndex;
    }
    
    
}
