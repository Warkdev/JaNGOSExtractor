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
