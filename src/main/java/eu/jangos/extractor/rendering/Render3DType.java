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
package eu.jangos.extractor.rendering;

/**
 * RenderingType indicates which rendering is wished from the object.
 * @author Warkdev
 */
public enum Render3DType {
    // Render the liquid in 3D.    
    LIQUID,
    // Render the terrain data in 3D.
    TERRAIN,
    // Render the model data.
    MODEL,
    // Render the collision model data.
    COLLISION_MODEL,
    // Render the collision terrain data.
    COLLISION_TERRAIN;
}
