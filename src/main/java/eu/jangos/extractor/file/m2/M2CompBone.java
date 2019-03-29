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
