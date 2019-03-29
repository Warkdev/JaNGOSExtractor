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
package eu.jangos.extractor.file.wmo;

import eu.jangos.extractor.file.common.C4Plane;
import java.nio.ByteBuffer;
import javafx.geometry.Point3D;

/**
 *
 * @author Warkdev
 */
public class WMOPortal {
    private short startVertex;
    private short count;
    private C4Plane plane = new C4Plane();            

    public void read(ByteBuffer data) {
        this.startVertex = data.getShort();
        this.count = data.getShort();
        this.plane.setNormal(new Point3D(data.getFloat(), data.getFloat(), data.getFloat()));
        this.plane.setDistance(data.getFloat());
    }
    
    public short getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(short startVertex) {
        this.startVertex = startVertex;
    }

    public short getCount() {
        return count;
    }

    public void setCount(short count) {
        this.count = count;
    }

    public C4Plane getPlane() {
        return plane;
    }

    public void setPlane(C4Plane plane) {
        this.plane = plane;
    }   
    
}
