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
 *
 * @author Warkdev
 */
public class M2Color {
    private M2Track<Point3D> color;
    private M2Track<Short> alpha;

    public M2Color() {
    }

    public M2Track<Point3D> getColor() {
        return color;
    }

    public void setColor(M2Track<Point3D> color) {
        this.color = color;
    }

    public M2Track<Short> getAlpha() {
        return alpha;
    }

    public void setAlpha(M2Track<Short> alpha) {
        this.alpha = alpha;
    }
    
    
}
