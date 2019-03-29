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
package eu.jangos.extractor.file.common;

import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class CAaBspNode {
    private short flags;
    private short negChild;
    private short posChild;
    private short nFaces;
    private int faceStart;
    private float planeDist;

    public void read(ByteBuffer data) {
        this.flags = data.getShort();
        this.negChild = data.getShort();
        this.posChild = data.getShort();
        this.nFaces = data.getShort();
        this.faceStart = data.getInt();
        this.planeDist = data.getFloat();
    }
    
    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public short getNegChild() {
        return negChild;
    }

    public void setNegChild(short negChild) {
        this.negChild = negChild;
    }

    public short getPosChild() {
        return posChild;
    }

    public void setPosChild(short posChild) {
        this.posChild = posChild;
    }

    public short getnFaces() {
        return nFaces;
    }

    public void setnFaces(short nFaces) {
        this.nFaces = nFaces;
    }

    public int getFaceStart() {
        return faceStart;
    }

    public void setFaceStart(int faceStart) {
        this.faceStart = faceStart;
    }

    public float getPlaneDist() {
        return planeDist;
    }

    public void setPlaneDist(float planeDist) {
        this.planeDist = planeDist;
    }        
}
