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
