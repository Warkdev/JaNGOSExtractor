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

import eu.jangos.utils.FlagUtils;
import java.nio.ByteBuffer;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point3D;

/**
 *
 * @author Warkdev
 */
public class MOGP {        
    private static final int HAS_LIQUID = 0x0F;
    
    private int groupName;
    private int descriptiveGroupName;
    private int flags;    
    private BoundingBox boundingBox;
    private short portalStart;
    private short portalCount;
    private short transBatchCount;
    private short intBatchCount;
    private short extBatchCount;
    private short padding;
    private byte[] fogsIds = new byte[4];
    private int groupLiquid;
    private int wmoAreaTableRecId;   
    
    public void read(ByteBuffer data) {
        this.groupName = data.getInt();
        this.descriptiveGroupName = data.getInt();
        this.flags = data.getInt();
        Point3D min = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        Point3D max = new Point3D(data.getFloat(), data.getFloat(), data.getFloat()); 
        this.boundingBox = new BoundingBox(max.getX(), max.getY(), max.getZ(), max.getX() - min.getX(), max.getY() - min.getY(), max.getZ() - min.getZ());        
        this.portalStart = data.getShort();
        this.portalCount = data.getShort();
        this.transBatchCount = data.getShort();
        this.intBatchCount = data.getShort();
        this.extBatchCount = data.getShort();
        this.padding = data.getShort();
        data.get(fogsIds);
        this.groupLiquid = data.getInt();
        this.wmoAreaTableRecId = data.getInt();
        // Skipping 2 null values at the end of the chunk.
        data.getInt();
        data.getInt();                    
    }
    
    public int getGroupName() {
        return groupName;
    }

    public void setGroupName(int groupName) {
        this.groupName = groupName;
    }

    public int getDescriptiveGroupName() {
        return descriptiveGroupName;
    }

    public void setDescriptiveGroupName(int descriptiveGroupName) {
        this.descriptiveGroupName = descriptiveGroupName;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public short getPortalStart() {
        return portalStart;
    }

    public void setPortalStart(short portalStart) {
        this.portalStart = portalStart;
    }

    public short getPortalCount() {
        return portalCount;
    }

    public void setPortalCount(short portalCount) {
        this.portalCount = portalCount;
    }       

    public short getTransBatchCount() {
        return transBatchCount;
    }

    public void setTransBatchCount(short transBatchCount) {
        this.transBatchCount = transBatchCount;
    }

    public short getIntBatchCount() {
        return intBatchCount;
    }

    public void setIntBatchCount(short intBatchCount) {
        this.intBatchCount = intBatchCount;
    }

    public short getExtBatchCount() {
        return extBatchCount;
    }

    public void setExtBatchCount(short extBatchCount) {
        this.extBatchCount = extBatchCount;
    }

    public short getPadding() {
        return padding;
    }

    public void setPadding(short padding) {
        this.padding = padding;
    }

    public byte[] getFogsIds() {
        return fogsIds;
    }

    public void setFogsIds(byte[] fogsIds) {
        this.fogsIds = fogsIds;
    }

    public int getGroupLiquid() {
        return groupLiquid;
    }

    public void setGroupLiquid(int groupLiquid) {
        this.groupLiquid = groupLiquid;
    }

    public boolean hasLiquid() {                
        return FlagUtils.hasFlag(this.groupLiquid, HAS_LIQUID);
    }
    
    public int getWmoAreaTableRecId() {
        return wmoAreaTableRecId;
    }

    public void setWmoAreaTableRecId(int wmoAreaTableRecId) {
        this.wmoAreaTableRecId = wmoAreaTableRecId;
    }
}
