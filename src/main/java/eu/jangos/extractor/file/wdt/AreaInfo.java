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
package eu.jangos.extractor.file.wdt;

import eu.jangos.utils.FlagUtils;
import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class AreaInfo {
    
    private static final int FLAG_HAS_ADT = 0x1;
    
    private int flags;
    private int asyncId;
       
    public void read(ByteBuffer in) {
        this.flags = in.getInt();
        this.asyncId = in.getInt();
    }

    public boolean hasADT() {
        return FlagUtils.hasFlag(this.flags, FLAG_HAS_ADT);
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
}
