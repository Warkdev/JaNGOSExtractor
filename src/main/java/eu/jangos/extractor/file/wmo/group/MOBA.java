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
package eu.jangos.extractor.file.wmo.group;

import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class MOBA {
    private short bx;
    private short by;
    private short bz;
    private short tx;
    private short ty;
    private short tz;
    private int startIndex;
    private short count;
    private short minIndex;
    private short maxIndex;
    private byte flag;
    private byte padding;

    public MOBA() {
    }

    public void read(ByteBuffer data) {
        this.bx = data.getShort();
        this.by = data.getShort();
        this.bz = data.getShort();
        this.tx = data.getShort();
        this.ty = data.getShort();
        this.tz = data.getShort();
        this.startIndex = data.getInt();
        this.count = data.getShort();
        this.minIndex = data.getShort();
        this.maxIndex = data.getShort();
        this.flag = data.get();
        this.padding = data.get();
    }
    
    public short getBx() {
        return bx;
    }

    public void setBx(short bx) {
        this.bx = bx;
    }

    public short getBy() {
        return by;
    }

    public void setBy(short by) {
        this.by = by;
    }

    public short getBz() {
        return bz;
    }

    public void setBz(short bz) {
        this.bz = bz;
    }

    public short getTx() {
        return tx;
    }

    public void setTx(short tx) {
        this.tx = tx;
    }

    public short getTy() {
        return ty;
    }

    public void setTy(short ty) {
        this.ty = ty;
    }

    public short getTz() {
        return tz;
    }

    public void setTz(short tz) {
        this.tz = tz;
    }    
    
    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public short getCount() {
        return count;
    }

    public void setCount(short count) {
        this.count = count;
    }

    public short getMinIndex() {
        return minIndex;
    }

    public void setMinIndex(short minIndex) {
        this.minIndex = minIndex;
    }

    public short getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(short maxIndex) {
        this.maxIndex = maxIndex;
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public byte getPadding() {
        return padding;
    }

    public void setPadding(byte padding) {
        this.padding = padding;
    }    
}
