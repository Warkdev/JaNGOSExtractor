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

import javafx.geometry.Point3D;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Warkdev
 */
public class WMOFog {
    private int flag;
    private Point3D position;
    private float smallerRadius;
    private float largerRadius;    
    private List<Fog> fogList = new ArrayList<>();

    public void read(ByteBuffer data) {
        fogList.clear();
        
        this.flag = data.getInt();
        this.position = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        this.smallerRadius = data.getFloat();
        this.largerRadius = data.getFloat();        
        Fog fog;
        for(int i = 0; i < 2; i++) {
            fog = new Fog();
            fog.read(data);
            this.fogList.add(fog);
        }
    }
    
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public float getSmallerRadius() {
        return smallerRadius;
    }

    public void setSmallerRadius(float smallerRadius) {
        this.smallerRadius = smallerRadius;
    }

    public float getLargerRadius() {
        return largerRadius;
    }

    public void setLargerRadius(float largerRadius) {
        this.largerRadius = largerRadius;
    }

    public List<Fog> getFogList() {
        return fogList;
    }

    public void setFogList(List<Fog> fogList) {
        this.fogList = fogList;
    }
   
}
