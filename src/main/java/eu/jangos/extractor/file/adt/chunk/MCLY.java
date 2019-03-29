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
 *
 * @author Warkdev
 */
public class MCLY {
    private int textureId;
    private int flags;
    private int offsetinMCAL;
    private int effectId;

    public void read(ByteBuffer in) {
        this.textureId = in.getInt();
        this.flags = in.getInt();
        this.offsetinMCAL = in.getInt();
        this.effectId = in.getInt();        
    }
    
    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getOffsetinMCAL() {
        return offsetinMCAL;
    }

    public void setOffsetinMCAL(int offsetinMCAL) {
        this.offsetinMCAL = offsetinMCAL;
    }

    public int getEffectId() {
        return effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }
    
    
}
