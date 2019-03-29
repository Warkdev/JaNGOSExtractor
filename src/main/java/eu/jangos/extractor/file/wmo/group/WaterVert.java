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
package eu.jangos.extractor.file.wmo.group;

import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class WaterVert {
    // Note: first short seems to be understood as "depth" by Noggit.
    private short flow1;
    private short flow2;
    private short flow1Pct;
    private short filler;
    private float height;

    public void read(ByteBuffer data) {
        this.flow1 = (short) Byte.toUnsignedInt(data.get());
        this.flow2 = (short) Byte.toUnsignedInt(data.get());
        this.flow1Pct = (short) Byte.toUnsignedInt(data.get());
        this.filler = (short) Byte.toUnsignedInt(data.get());
        this.height = data.getFloat();                
    }
    
    public short getFlow1() {
        return flow1;
    }

    public void setFlow1(short flow1) {
        this.flow1 = flow1;
    }

    public short getFlow2() {
        return flow2;
    }

    public void setFlow2(short flow2) {
        this.flow2 = flow2;
    }

    public short getFlow1Pct() {
        return flow1Pct;
    }

    public void setFlow1Pct(short flow1Pct) {
        this.flow1Pct = flow1Pct;
    }

    public short getFiller() {
        return filler;
    }

    public void setFiller(short filler) {
        this.filler = filler;
    }  

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }        
    
    public String toString() {    
        return "WaterVert [Flow1: "+flow1+" - Flow2: "+flow2+" - Flow1Pct: "+flow1Pct+" - Filler: "+filler+" - Height: "+height+"]";
    }
    
    public String toCSVString() {
        return flow1+";"+flow2+";"+flow1Pct+";"+filler+";"+height;
    }
}
