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
import javafx.geometry.BoundingBox;
import javafx.geometry.Point3D;

/**
 *
 * @author Warkdev
 */
public class MODF {
    
    private int mwidEntry;
    private int uniqueId;
    private Point3D position;
    private Point3D orientation;
    private BoundingBox boundingBox; 
    private short flags;
    private short doodadSet;
    private short nameSet;
    private short padding;

    public void read(ByteBuffer data) {
        this.mwidEntry = data.getInt();
        this.uniqueId = data.getInt();
        this.position = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());        
        this.orientation = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());                
        Point3D min = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        Point3D max = new Point3D(data.getFloat(), data.getFloat(), data.getFloat()); 
        this.boundingBox = new BoundingBox(max.getX(), max.getY(), max.getZ(), max.getX() - min.getX(), max.getY() - min.getY(), max.getZ() - min.getZ());        
        this.flags = data.getShort();
        this.doodadSet = data.getShort();
        this.nameSet = data.getShort();
        this.padding = data.getShort();
    }
    
    public int getMwidEntry() {
        return mwidEntry;
    }

    public void setMwidEntry(int mwidEntry) {
        this.mwidEntry = mwidEntry;
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

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }    
    
    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public short getDoodadSet() {
        return doodadSet;
    }

    public void setDoodadSet(short doodadSet) {
        this.doodadSet = doodadSet;
    }

    public short getNameSet() {
        return nameSet;
    }

    public void setNameSet(short nameSet) {
        this.nameSet = nameSet;
    }

    public short getPadding() {
        return padding;
    }

    public void setPadding(short padding) {
        this.padding = padding;
    }
    
    
    
}
