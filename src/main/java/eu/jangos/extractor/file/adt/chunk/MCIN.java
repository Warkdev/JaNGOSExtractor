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
