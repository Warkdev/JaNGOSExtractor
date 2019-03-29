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
