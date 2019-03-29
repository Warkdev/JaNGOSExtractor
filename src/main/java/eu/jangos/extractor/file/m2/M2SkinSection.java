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
