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
