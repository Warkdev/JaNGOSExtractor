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
package eu.jangos.extractor.file.m2;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class M2Vertex {
    private Point3D position;
    private byte[] boneWeights = new byte[4];
    private byte[] boneIndices = new byte[4];
    private Point3D normal;
    private Point2D[] texCoords = new Point2D[2];    

    public void read(ByteBuffer data) {
        this.position = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        data.get(this.boneWeights);
        data.get(this.boneIndices);
        this.normal = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        for(int i = 0; i < this.texCoords.length; i++) {
            texCoords[i] = new Point2D(data.getFloat(), data.getFloat());
        }
    }
    
    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public byte[] getBoneWeights() {
        return boneWeights;
    }

    public void setBoneWeights(byte[] boneWeights) {
        this.boneWeights = boneWeights;
    }

    public byte[] getBoneIndices() {
        return boneIndices;
    }

    public void setBoneIndices(byte[] boneIndices) {
        this.boneIndices = boneIndices;
    }

    public Point3D getNormal() {
        return normal;
    }

    public void setNormal(Point3D normal) {
        this.normal = normal;
    }

    public Point2D[] getTexCoords() {
        return texCoords;
    }

    public void setTexCoords(Point2D[] texCoords) {
        this.texCoords = texCoords;
    }        
}
