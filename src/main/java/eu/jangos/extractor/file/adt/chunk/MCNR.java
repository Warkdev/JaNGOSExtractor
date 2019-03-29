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
package eu.jangos.extractor.file.adt.chunk;

import java.nio.ByteBuffer;
import javafx.geometry.Point3D;

/**
 *
 * @author Warkdev
 */
public class MCNR {    
    private Point3D[] points = new Point3D[145];

    public void read(ByteBuffer in) {
        for (int j = 0; j < points.length; j++) {
            this.points[j] = new Point3D((double) in.get(), (double) in.get(), (double) in.get());
        }
    }
    
    public Point3D[] getPoints() {
        return points;
    }

    public void setPoints(Point3D[] points) {
        this.points = points;
    }
    
    
}
