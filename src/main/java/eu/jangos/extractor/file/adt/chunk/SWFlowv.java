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
