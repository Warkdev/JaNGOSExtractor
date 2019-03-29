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
