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
