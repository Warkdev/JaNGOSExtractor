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
package eu.jangos.extractor.rendering.symbolic;

import eu.jangos.extractor.rendering.PolygonMesh;

public class OriginalPointArray extends SymbolicPointArray {
    PolygonMesh mesh;

    public OriginalPointArray(PolygonMesh mesh) {
        super(new float[mesh.getPoints().size()]);
        this.mesh = mesh;
    }

    @Override
    public void update() {
        mesh.getPoints().copyTo(0, data, 0, data.length);
    }
}
