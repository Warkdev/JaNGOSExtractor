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
 * This enum represents the possible render for a 2D renderer tilemap.
 * @author Warkdev
 */
public enum Render2DType {
    /**
     * Render a 2D view tilemap giving the liquid type.
     */
    RENDER_TILEMAP_LIQUID_TYPE,
    /**
     * Render a 2D view tilemap giving the fishable areas.
     */
    RENDER_TILEMAP_LIQUID_FISHABLE,
    /**
     * Render a 2D view tilemap giving the animated water areas.
     */
    RENDER_TILEMAP_LIQUID_ANIMATED,
    /**
     * Render a 2D view tilemap giving the liquid heightmap.
     */
    RENDER_TILEMAP_LIQUID_HEIGHTMAP,    
    /**
     * Render a 2D view tilemap giving the terrain presence.
     */
    RENDER_TILEMAP_TERRAIN,
    /**
     * Render a 2D view tilemap giving the terrain heightmap.
     */
    RENDER_TILEMAP_TERRAIN_HEIGHTMAP,
    /**
     * Render a 2D view tilemap giving the holes in the terrain.
     */
    RENDER_TILEMAP_TERRAIN_HOLEMAP;
}
