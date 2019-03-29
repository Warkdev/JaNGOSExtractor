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
