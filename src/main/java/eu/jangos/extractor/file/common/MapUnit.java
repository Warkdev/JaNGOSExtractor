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
package eu.jangos.extractor.file.common;

/**
 * MapUnit represents the common set of units used in the map system of the game.
 * @author Warkdev
 */
public class MapUnit {
    public static final float TILE_SIZE = 533 + 1/3.0f;
    public static final float CHUNK_SIZE = TILE_SIZE / 16.0f;        
    public static final float UNIT_SIZE = CHUNK_SIZE / 8.0f;    
    public static final float ZERO_POINT = 32.0f * TILE_SIZE;   
}
