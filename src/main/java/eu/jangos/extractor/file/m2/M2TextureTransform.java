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
