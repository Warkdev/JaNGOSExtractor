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

import eu.jangos.extractor.file.common.Point4D;
import javafx.geometry.Point3D;

/**
 *
 * @author Warkdev
 */
public class M2TextureTransform {
    private M2Track<Point3D> translation;
    private M2Track<Point4D> rotation;
    private M2Track<Point3D> scaling;

    public M2TextureTransform() {
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

    public M2Track<Point3D> getScaling() {
        return scaling;
    }

    public void setScaling(M2Track<Point3D> scaling) {
        this.scaling = scaling;
    }
    
    
}
