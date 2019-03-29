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

import javafx.geometry.Point3D;
import eu.jangos.extractor.file.common.CImVector;
import eu.jangos.extractor.file.common.Quaternion;
import java.nio.ByteBuffer;

/**
 * This object represents a WMO doodad definition. This object hold a M2 Model as part of the WMO
 * with its position, orientation and scale in the WMO.
 * @author Warkdev
 */
public class WMODoodadDef {
    private int nameIndex;
    private int flag;
    private Point3D position;
    private Quaternion orientation = new Quaternion();
    private float scale;
    private CImVector color = new CImVector();

    public WMODoodadDef() {
    }

    public void read(ByteBuffer data) {               
        // Big endian stupid stuff...
        byte b1 = data.get();
        byte b2 = data.get();
        byte b3 = data.get();
        this.nameIndex = (b3 & 0x0F) << 16 | (b2 & 0xFF) << 8 | (b1 & 0xFF);
        this.flag = data.get();
        this.position = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        this.orientation.set(data.getFloat(), data.getFloat(), data.getFloat(), data.getFloat());        
        this.scale = data.getFloat();
        this.color.setB(data.get());
        this.color.setG(data.get());
        this.color.setR(data.get());
        this.color.setA(data.get());
    }
    
    public int getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public Quaternion getOrientation() {
        return orientation;
    }

    public void setOrientation(Quaternion orientation) {
        this.orientation = orientation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public CImVector getColor() {
        return color;
    }

    public void setColor(CImVector color) {
        this.color = color;
    }
    
}
