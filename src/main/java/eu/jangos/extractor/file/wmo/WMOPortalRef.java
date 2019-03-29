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
package eu.jangos.extractor.file.wmo;

import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class WMOPortalRef {
    private short portalIndex;
    private short groupIndex;
    private short side;
    private short filler;

    public WMOPortalRef() {
    }    
    
    public void read(ByteBuffer data) {
        this.portalIndex = data.getShort();
        this.groupIndex = data.getShort();
        this.side = data.getShort();
        this.filler = data.getShort();
    }
    
    public short getPortalIndex() {
        return portalIndex;
    }

    public void setPortalIndex(short portalIndex) {
        this.portalIndex = portalIndex;
    }

    public short getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(short groupIndex) {
        this.groupIndex = groupIndex;
    }

    public short getSide() {
        return side;
    }

    public void setSide(short side) {
        this.side = side;
    }

    public short getFiller() {
        return filler;
    }

    public void setFiller(short filler) {
        this.filler = filler;
    }        
}
