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
