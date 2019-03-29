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
package eu.jangos.extractor.file.m2;

import javafx.geometry.Point3D;

/**
 * This block specifies a bunch of locations on the body - hands, shoulders, head, back, knees etc. 
 * It is used to put items on a character. This seems very likely as this block also contains positions for sheathed weapons, a shield, etc.
 * @author Warkdev
 */
public class M2Attachment {
    
    // Referenced in the lookup-block below.
    private int id;
    // Attachment base.
    private short bone;
    // see BogBeast.m2 in vanilla for a model having values here
    private short unknown;
    // relative to bone; Often this value is the same as bone's pivot point 
    private Point3D position;
    // whether or not the attached model is animated when this model is. only a bool is used. default is true.
    private M2Track<Boolean> animateAttached;

    public M2Attachment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getBone() {
        return bone;
    }

    public void setBone(short bone) {
        this.bone = bone;
    }

    public short getUnknown() {
        return unknown;
    }

    public void setUnknown(short unknown) {
        this.unknown = unknown;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public M2Track<Boolean> getAnimateAttached() {
        return animateAttached;
    }

    public void setAnimateAttached(M2Track<Boolean> animateAttached) {
        this.animateAttached = animateAttached;
    }
    
    
    
}
