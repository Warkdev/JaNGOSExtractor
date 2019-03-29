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
