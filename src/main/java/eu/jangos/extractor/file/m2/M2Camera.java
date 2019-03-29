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
