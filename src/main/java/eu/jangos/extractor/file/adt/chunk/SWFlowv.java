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

import eu.jangos.extractor.file.common.CAaSphere;
import java.nio.ByteBuffer;
import javafx.geometry.Point3D;

/**
 *
 * @author Warkdev
 */
public class SWFlowv {
    private CAaSphere sphere = new CAaSphere();
    private Point3D direction;
    private float velocity;
    private float amplitude;
    private float frequency;

    public void read(ByteBuffer data) {
        this.sphere.read(data);
        this.direction = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());        
        this.velocity = data.getFloat();
        this.amplitude = data.getFloat();
        this.frequency = data.getFloat();
    }
    
    public CAaSphere getSphere() {
        return sphere;
    }

    public void setSphere(CAaSphere sphere) {
        this.sphere = sphere;
    }

    public Point3D getDirection() {
        return direction;
    }

    public void setDirection(Point3D direction) {
        this.direction = direction;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }        
}
