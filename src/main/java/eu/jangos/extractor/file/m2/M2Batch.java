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
