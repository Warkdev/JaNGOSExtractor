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
package eu.jangos.extractor.file.adt.chunk;

import java.nio.ByteBuffer;

/**
 * This is a lookup table containing absolute offsets and sizes for every map tile in the file. There are 16x16 = 256 entries of 16 bytes each.
 * @author Warkdev
 */
public class MCIN {
    private int offsetMCNK;
    private int size;
    private int flags;
    private int asyncId;
    private static final int OBJECT_SIZE = 16;
    
    public void read(ByteBuffer in) {
        this.offsetMCNK = in.getInt();
        this.size = in.getInt();
        this.flags = in.getInt();
        this.asyncId = in.getInt();
    }
    
    public int getOffsetMCNK() {
        return offsetMCNK;
    }

    public void setOffsetMCNK(int offsetMCNK) {
        this.offsetMCNK = offsetMCNK;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getAsyncId() {
        return asyncId;
    }

    public void setAsyncId(int asyncId) {
        this.asyncId = asyncId;
    }        

    public static int getOBJECT_SIZE() {
        return OBJECT_SIZE;
    }        
}
