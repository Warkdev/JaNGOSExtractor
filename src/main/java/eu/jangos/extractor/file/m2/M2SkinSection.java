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

import javafx.geometry.Point3D;
import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class M2SkinSection {
    private short id;
    private short level;
    private short vertexStart;
    private short vertexCount;
    private int indexStart;
    private short indexCount;
    private short boneCount;
    private short boneComboIndex;
    private short boneInfluences;
    private short centerBoneIndex;
    private Point3D centerPosition;

    public M2SkinSection() {        
    }

    public void read(ByteBuffer data) {
        this.id = data.getShort();
        this.level = data.getShort();
        this.vertexStart = data.getShort();
        this.vertexCount = data.getShort();
        this.indexStart = Short.toUnsignedInt(data.getShort());
        this.indexCount = data.getShort();
        this.boneCount = data.getShort();
        this.boneComboIndex = data.getShort();
        this.boneInfluences = data.getShort();
        this.centerBoneIndex = data.getShort();
        this.centerPosition = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
    }
    
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short Level) {
        this.level = Level;
    }

    public short getVertexStart() {
        return vertexStart;
    }

    public void setVertexStart(short vertexStart) {
        this.vertexStart = vertexStart;
    }

    public short getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(short vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int getIndexStart() {
        return indexStart;
    }

    public void setIndexStart(int indexStart) {
        this.indexStart = indexStart;
    }

    public short getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(short indexCount) {
        this.indexCount = indexCount;
    }

    public short getBoneCount() {
        return boneCount;
    }

    public void setBoneCount(short boneCount) {
        this.boneCount = boneCount;
    }

    public short getBoneComboIndex() {
        return boneComboIndex;
    }

    public void setBoneComboIndex(short boneComboIndex) {
        this.boneComboIndex = boneComboIndex;
    }

    public short getBoneInfluences() {
        return boneInfluences;
    }

    public void setBoneInfluences(short boneInfluences) {
        this.boneInfluences = boneInfluences;
    }

    public short getCenterBoneIndex() {
        return centerBoneIndex;
    }

    public void setCenterBoneIndex(short centerBoneIndex) {
        this.centerBoneIndex = centerBoneIndex;
    }

    public Point3D getCenterPosition() {
        return centerPosition;
    }

    public void setCenterPosition(Point3D centerPosition) {
        this.centerPosition = centerPosition;
    }
    
    
}
