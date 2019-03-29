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
