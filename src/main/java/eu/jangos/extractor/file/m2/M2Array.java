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

import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class M2Array<T> {
    
    private int size;
    // Pointer to T, relative to begin of m2 data block.
    private int offset; 

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return (int) offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public void read(ByteBuffer buffer) {
        this.size = buffer.getInt();
        this.offset = buffer.getInt();
    }
    
}
