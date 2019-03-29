/**
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
package eu.jangos.extractor.rendering;

import javafx.collections.FXCollections;
import javafx.collections.ObservableFloatArray;
import javafx.collections.ObservableIntegerArray;

/**
 * A Mesh where each face can be a Polygon
 *
 * can convert to using ObservableIntegerArray
 * @author Oracle.
 */
public class PolygonMesh {
    private final ObservableFloatArray points = FXCollections.observableFloatArray();
    private final ObservableFloatArray normals = FXCollections.observableFloatArray();
    private final ObservableFloatArray texCoords = FXCollections.observableFloatArray();
    public int[][] faces = new int[0][0];
    private final ObservableIntegerArray faceSmoothingGroups = FXCollections.observableIntegerArray();
    protected int numEdgesInFaces = -1; // TODO invalidate automatically by listening to faces (whenever it is an observable)

    public PolygonMesh() {}

    public PolygonMesh(float[] points, float[] normals, float[] texCoords, int[][] faces) {
        this.points.addAll(points);
        this.normals.addAll(normals);
        this.texCoords.addAll(texCoords);
        this.faces = faces;
    }

    public ObservableFloatArray getPoints() {
        return points;
    }

    public ObservableFloatArray getNormals() {
        return normals;
    }
    
    public ObservableFloatArray getTexCoords() {
        return texCoords;
    }

    public ObservableIntegerArray getFaceSmoothingGroups() {
        return faceSmoothingGroups;
    }

    public int getNumEdgesInFaces() {
        if (numEdgesInFaces == -1) {
            numEdgesInFaces = 0;
            for(int[] face : faces) {
                numEdgesInFaces += face.length;
            }
           numEdgesInFaces /= 2;
        }
        return numEdgesInFaces;
    }

    // TODO: Hardcode to constants for FX 8 (only one vertex format)
    private static final int NUM_COMPONENTS_PER_POINT = 3;
    private static final int NUM_COMPONENTS_PER_NORMAL = 3;
    private static final int NUM_COMPONENTS_PER_TEXCOORD = 2;
    private static final int NUM_COMPONENTS_PER_FACE = 6;    

    public int getPointElementSize() {
        return NUM_COMPONENTS_PER_POINT;
    }

    public int getNormalElementSize() {
        return NUM_COMPONENTS_PER_NORMAL;
    }
    
    public int getTexCoordElementSize() {
        return NUM_COMPONENTS_PER_TEXCOORD;
    }

    public int getFaceElementSize() {
        return NUM_COMPONENTS_PER_FACE;
    }
}
