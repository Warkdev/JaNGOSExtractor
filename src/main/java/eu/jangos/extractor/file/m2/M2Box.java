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
public class M2Box {
    private Point3D modelRotationSpeedMin;
    private Point3D modelRotationSpeedMax;

    public M2Box() {
    }

    public Point3D getModelRotationSpeedMin() {
        return modelRotationSpeedMin;
    }

    public void setModelRotationSpeedMin(Point3D modelRotationSpeedMin) {
        this.modelRotationSpeedMin = modelRotationSpeedMin;
    }

    public Point3D getModelRotationSpeedMax() {
        return modelRotationSpeedMax;
    }

    public void setModelRotationSpeedMax(Point3D modelRotationSpeedMax) {
        this.modelRotationSpeedMax = modelRotationSpeedMax;
    }
    
    
}
