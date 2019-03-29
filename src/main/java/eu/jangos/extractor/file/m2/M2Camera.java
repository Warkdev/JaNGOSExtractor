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
public class M2Camera {
    private int type;
    private float fov;
    private float farClip;
    private float nearClip;
    private M2Track<M2SplineKey<Point3D>> positions;
    private Point3D positionBase;
    private M2Track<M2SplineKey<Point3D>> targetPosition;
    private Point3D targetPositionBase;
    private M2Track<M2SplineKey<Float>> roll;

    public M2Camera() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getFarClip() {
        return farClip;
    }

    public void setFarClip(float farClip) {
        this.farClip = farClip;
    }

    public float getNearClip() {
        return nearClip;
    }

    public void setNearClip(float nearClip) {
        this.nearClip = nearClip;
    }

    public M2Track<M2SplineKey<Point3D>> getPositions() {
        return positions;
    }

    public void setPositions(M2Track<M2SplineKey<Point3D>> positions) {
        this.positions = positions;
    }

    public Point3D getPositionBase() {
        return positionBase;
    }

    public void setPositionBase(Point3D positionBase) {
        this.positionBase = positionBase;
    }

    public M2Track<M2SplineKey<Point3D>> getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(M2Track<M2SplineKey<Point3D>> targetPosition) {
        this.targetPosition = targetPosition;
    }

    public Point3D getTargetPositionBase() {
        return targetPositionBase;
    }

    public void setTargetPositionBase(Point3D targetPositionBase) {
        this.targetPositionBase = targetPositionBase;
    }

    public M2Track<M2SplineKey<Float>> getRoll() {
        return roll;
    }

    public void setRoll(M2Track<M2SplineKey<Float>> roll) {
        this.roll = roll;
    }
    
    
}
