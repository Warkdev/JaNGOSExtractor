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
package eu.jangos.extractor.file.common;

import java.nio.ByteBuffer;
import javafx.geometry.Point3D;

/**
 *
 * @author Warkdev
 */
public class CAaSphere {
    
    private Point3D position;
    private float radius;

    public void read(ByteBuffer data) {
        this.position = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());       
        this.radius = data.getFloat();
    }
    
    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }            
}
