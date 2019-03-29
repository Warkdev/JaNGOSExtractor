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
