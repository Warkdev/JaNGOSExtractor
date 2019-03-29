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
import eu.jangos.extractor.file.common.Point4D;

/**
 *
 * @author Warkdev
 */
public class M2CompBone {
    private int keyBoneId;
    private int enumBones;
    private int flags;
    private short parentBone;
    private short submeshId;
    private M2Track<Point3D> translation;
    private M2Track<Point4D> rotation;
    private M2Track<Point3D> scale;
    private Point3D pivot;

    public M2CompBone() {
    }

    public int getKeyBoneId() {
        return keyBoneId;
    }

    public void setKeyBoneId(int keyBoneId) {
        this.keyBoneId = keyBoneId;
    }

    public int getEnumBones() {
        return enumBones;
    }

    public void setEnumBones(int enumBones) {
        this.enumBones = enumBones;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public short getParentBone() {
        return parentBone;
    }

    public void setParentBone(short parentBone) {
        this.parentBone = parentBone;
    }

    public short getSubmeshId() {
        return submeshId;
    }

    public void setSubmeshId(short submeshId) {
        this.submeshId = submeshId;
    }

    public M2Track<Point3D> getTranslation() {
        return translation;
    }

    public void setTranslation(M2Track<Point3D> translation) {
        this.translation = translation;
    }

    public M2Track<Point4D> getRotation() {
        return rotation;
    }

    public void setRotation(M2Track<Point4D> rotation) {
        this.rotation = rotation;
    }

    public M2Track<Point3D> getScale() {
        return scale;
    }

    public void setScale(M2Track<Point3D> scale) {
        this.scale = scale;
    }

    public Point3D getPivot() {
        return pivot;
    }

    public void setPivot(Point3D pivot) {
        this.pivot = pivot;
    }
    
    
}
