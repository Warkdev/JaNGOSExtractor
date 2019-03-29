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

import java.nio.ByteBuffer;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point3D;

/**
 *
 * @author Warkdev
 */
public class WMOGroupInfo {
    private int flags;
    private BoundingBox boundingBox;    
    private int nameOffset;

    public void read(ByteBuffer data) {
        this.flags = data.getInt();
        Point3D min = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        Point3D max = new Point3D(data.getFloat(), data.getFloat(), data.getFloat()); 
        this.boundingBox = new BoundingBox(max.getX(), max.getY(), max.getZ(), max.getX() - min.getX(), max.getY() - min.getY(), max.getZ() - min.getZ());        
        this.nameOffset = data.getInt();
    }
    
    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }    

    public int getNameOffset() {
        return nameOffset;
    }

    public void setNameOffset(int nameOffset) {
        this.nameOffset = nameOffset;
    }        
}
