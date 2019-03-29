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
