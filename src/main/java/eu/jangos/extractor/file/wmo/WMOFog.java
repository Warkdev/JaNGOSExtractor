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
