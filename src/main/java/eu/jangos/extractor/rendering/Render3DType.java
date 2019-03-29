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
