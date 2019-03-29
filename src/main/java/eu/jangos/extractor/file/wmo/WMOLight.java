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
import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class WMOLight {
    
    // Document ME, see Wowwiki.
    private int flags;
    private CImVector color = new CImVector();
    private Point3D position;
    private float intensity;
    private float[] unknown = new float[4];
    private float attenStart;
    private float attenEnd;

    public void read(ByteBuffer data) {
        this.flags = data.getInt();
        this.color.setB(data.get());
        this.color.setG(data.get());        
        this.color.setR(data.get());
        this.color.setA(data.get());
        this.position = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        this.intensity = data.getFloat();
        for(int i = 0; i < unknown.length; i++) {
            this.unknown[i] = data.getFloat();
        }
        this.attenStart = data.getFloat();
        this.attenEnd = data.getFloat();
    }
    
    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public CImVector getColor() {
        return color;
    }

    public void setColor(CImVector color) {
        this.color = color;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float[] getUnknown() {
        return unknown;
    }

    public void setUnknown(float[] unknown) {
        this.unknown = unknown;
    }

    public float getAttenStart() {
        return attenStart;
    }

    public void setAttenStart(float attenStart) {
        this.attenStart = attenStart;
    }

    public float getAttenEnd() {
        return attenEnd;
    }

    public void setAttenEnd(float attenEnd) {
        this.attenEnd = attenEnd;
    }                
}
