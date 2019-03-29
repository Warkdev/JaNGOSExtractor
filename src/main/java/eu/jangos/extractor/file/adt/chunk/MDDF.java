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
