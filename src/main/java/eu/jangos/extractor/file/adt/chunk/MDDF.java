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
public class MDDF {
    
    private int mmidEntry;
    private int uniqueId;
    private Point3D position;
    private Point3D orientation;
    private short scale;
    private short flags;

    public void read(ByteBuffer in) {        
        this.mmidEntry = in.getInt();
        this.uniqueId = in.getInt();
        this.position = new Point3D(in.getFloat(), in.getFloat(), in.getFloat());        
        this.orientation = new Point3D(in.getFloat(), in.getFloat(), in.getFloat());                
        this.scale = in.getShort();
        this.flags = in.getShort();                    
    }
    
    public int getMmidEntry() {
        return mmidEntry;
    }

    public void setMmidEntry(int mmidEntry) {
        this.mmidEntry = mmidEntry;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public Point3D getOrientation() {
        return orientation;
    }

    public void setOrientation(Point3D orientation) {
        this.orientation = orientation;
    }    

    public short getScale() {
        return scale;
    }

    public void setScale(short scale) {
        this.scale = scale;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }
    
    
    
}
