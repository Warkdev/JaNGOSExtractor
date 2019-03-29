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

/**
 *
 * @author Warkdev
 */
public class M2Light {
    private short type;
    private short bone;
    private Point3D position;
    private M2Track<Point3D> ambientColor;
    private M2Track<Float> ambientIntensity;
    private M2Track<Point3D> diffuseColor;
    private M2Track<Float> diffuseIntensity;
    private M2Track<Float> attenuationStart;
    private M2Track<Float> attenuationEnd;
    private M2Track<Byte> visiblity;

    public M2Light() {
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public short getBone() {
        return bone;
    }

    public void setBone(short bone) {
        this.bone = bone;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public M2Track<Point3D> getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(M2Track<Point3D> ambientColor) {
        this.ambientColor = ambientColor;
    }

    public M2Track<Float> getAmbientIntensity() {
        return ambientIntensity;
    }

    public void setAmbientIntensity(M2Track<Float> ambientIntensity) {
        this.ambientIntensity = ambientIntensity;
    }

    public M2Track<Point3D> getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(M2Track<Point3D> diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public M2Track<Float> getDiffuseIntensity() {
        return diffuseIntensity;
    }

    public void setDiffuseIntensity(M2Track<Float> diffuseIntensity) {
        this.diffuseIntensity = diffuseIntensity;
    }

    public M2Track<Float> getAttenuationStart() {
        return attenuationStart;
    }

    public void setAttenuationStart(M2Track<Float> attenuationStart) {
        this.attenuationStart = attenuationStart;
    }

    public M2Track<Float> getAttenuationEnd() {
        return attenuationEnd;
    }

    public void setAttenuationEnd(M2Track<Float> attenuationEnd) {
        this.attenuationEnd = attenuationEnd;
    }

    public M2Track<Byte> getVisiblity() {
        return visiblity;
    }

    public void setVisiblity(M2Track<Byte> visiblity) {
        this.visiblity = visiblity;
    }
            
    
}
