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
package eu.jangos.extractor.file.wmo.group;

import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class MOPY {
    
    private byte flags;
    private byte materialId;

    public void read(ByteBuffer data) {
        this.flags = data.get();
        this.materialId = data.get();
    }
    
    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public byte getMaterialId() {
        return materialId;
    }

    public void setMaterialId(byte materialId) {
        this.materialId = materialId;
    }
            
}
