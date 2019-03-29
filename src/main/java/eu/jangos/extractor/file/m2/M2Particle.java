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

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

/**
 *
 * @author Warkdev
 */
public class M2Particle {
    private int particleId;
    private int flags;
    private Point3D position;
    private short bone;
    private short texture;
    private M2Array<Character> geometryModelFilename;
    private M2Array<Character> recursionModelFilename;
    private short blendingType;
    private short emitterType;
    private byte particleType;
    private byte headorTail;
    private short textureTileRotation;
    private short textureDimensionsRows;
    private short textureDimensionsColumns;
    private M2Track<Float> emissionSpeed;
    private M2Track<Float> speedVariation;
    private M2Track<Float> verticalRange;
    private M2Track<Float> horizontalRange;
    private M2Track<Float> gravity;
    private M2Track<Float> lifespan;
    private M2Track<Float> emissionRate;
    private M2Track<Float> emissionAreaLength;
    private M2Track<Float> emissionAreaWidth;
    private M2Track<Float> zSource;
    private float midPoint;
    private byte[][] colorValues = new byte[4][3];
    private float[] scaleValues = new float[3];
    private short[] headCellBegin = new short[2];
    private short between1;
    private short[] headCellEnd = new short[2];
    private short between2;
    private short[] tiles = new short[4];
    private float tailLength;
    private float twinkleSpeed;
    private float twinklePercent;
    private Point2D twinkleScale;
    private float burstMultiplier;
    private float drag;
    private float spin;
    private M2Box tumble;
    private Point3D windVector;
    private float windTime;
    private float followSpeed1;
    private float followScale1;
    private float followSpeed2;
    private float followScale2;
    private M2Array<Point3D> splinePoints;
    private M2Track<Boolean> enableInd;

    public M2Particle() {
    }

    public int getParticleId() {
        return particleId;
    }

    public void setParticleId(int particleId) {
        this.particleId = particleId;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public short getBone() {
        return bone;
    }

    public void setBone(short bone) {
        this.bone = bone;
    }

    public short getTexture() {
        return texture;
    }

    public void setTexture(short texture) {
        this.texture = texture;
    }

    public M2Array<Character> getGeometryModelFilename() {
        return geometryModelFilename;
    }

    public void setGeometryModelFilename(M2Array<Character> geometryModelFilename) {
        this.geometryModelFilename = geometryModelFilename;
    }

    public M2Array<Character> getRecursionModelFilename() {
        return recursionModelFilename;
    }

    public void setRecursionModelFilename(M2Array<Character> recursionModelFilename) {
        this.recursionModelFilename = recursionModelFilename;
    }

    public short getBlendingType() {
        return blendingType;
    }

    public void setBlendingType(short blendingType) {
        this.blendingType = blendingType;
    }

    public short getEmitterType() {
        return emitterType;
    }

    public void setEmitterType(short emitterType) {
        this.emitterType = emitterType;
    }

    public byte getParticleType() {
        return particleType;
    }

    public void setParticleType(byte particleType) {
        this.particleType = particleType;
    }

    public byte getHeadorTail() {
        return headorTail;
    }

    public void setHeadorTail(byte headorTail) {
        this.headorTail = headorTail;
    }

    public short getTextureTileRotation() {
        return textureTileRotation;
    }

    public void setTextureTileRotation(short textureTileRotation) {
        this.textureTileRotation = textureTileRotation;
    }

    public short getTextureDimensionsRows() {
        return textureDimensionsRows;
    }

    public void setTextureDimensionsRows(short textureDimensionsRows) {
        this.textureDimensionsRows = textureDimensionsRows;
    }

    public short getTextureDimensionsColumns() {
        return textureDimensionsColumns;
    }

    public void setTextureDimensionsColumns(short textureDimensionsColumns) {
        this.textureDimensionsColumns = textureDimensionsColumns;
    }

    public M2Track<Float> getEmissionSpeed() {
        return emissionSpeed;
    }

    public void setEmissionSpeed(M2Track<Float> emissionSpeed) {
        this.emissionSpeed = emissionSpeed;
    }

    public M2Track<Float> getSpeedVariation() {
        return speedVariation;
    }

    public void setSpeedVariation(M2Track<Float> speedVariation) {
        this.speedVariation = speedVariation;
    }

    public M2Track<Float> getVerticalRange() {
        return verticalRange;
    }

    public void setVerticalRange(M2Track<Float> verticalRange) {
        this.verticalRange = verticalRange;
    }

    public M2Track<Float> getHorizontalRange() {
        return horizontalRange;
    }

    public void setHorizontalRange(M2Track<Float> horizontalRange) {
        this.horizontalRange = horizontalRange;
    }

    public M2Track<Float> getGravity() {
        return gravity;
    }

    public void setGravity(M2Track<Float> gravity) {
        this.gravity = gravity;
    }

    public M2Track<Float> getLifespan() {
        return lifespan;
    }

    public void setLifespan(M2Track<Float> lifespan) {
        this.lifespan = lifespan;
    }

    public M2Track<Float> getEmissionRate() {
        return emissionRate;
    }

    public void setEmissionRate(M2Track<Float> emissionRate) {
        this.emissionRate = emissionRate;
    }

    public M2Track<Float> getEmissionAreaLength() {
        return emissionAreaLength;
    }

    public void setEmissionAreaLength(M2Track<Float> emissionAreaLength) {
        this.emissionAreaLength = emissionAreaLength;
    }

    public M2Track<Float> getEmissionAreaWidth() {
        return emissionAreaWidth;
    }

    public void setEmissionAreaWidth(M2Track<Float> emissionAreaWidth) {
        this.emissionAreaWidth = emissionAreaWidth;
    }

    public M2Track<Float> getzSource() {
        return zSource;
    }

    public void setzSource(M2Track<Float> zSource) {
        this.zSource = zSource;
    }

    public float getMidPoint() {
        return midPoint;
    }

    public void setMidPoint(float midPoint) {
        this.midPoint = midPoint;
    }

    public byte[][] getColorValues() {
        return colorValues;
    }

    public void setColorValues(byte[][] colorValues) {
        this.colorValues = colorValues;
    }

    public float[] getScaleValues() {
        return scaleValues;
    }

    public void setScaleValues(float[] scaleValues) {
        this.scaleValues = scaleValues;
    }

    public short[] getHeadCellBegin() {
        return headCellBegin;
    }

    public void setHeadCellBegin(short[] headCellBegin) {
        this.headCellBegin = headCellBegin;
    }

    public short getBetween1() {
        return between1;
    }

    public void setBetween1(short between1) {
        this.between1 = between1;
    }

    public short[] getHeadCellEnd() {
        return headCellEnd;
    }

    public void setHeadCellEnd(short[] headCellEnd) {
        this.headCellEnd = headCellEnd;
    }

    public short getBetween2() {
        return between2;
    }

    public void setBetween2(short between2) {
        this.between2 = between2;
    }

    public short[] getTiles() {
        return tiles;
    }

    public void setTiles(short[] tiles) {
        this.tiles = tiles;
    }

    public float getTailLength() {
        return tailLength;
    }

    public void setTailLength(float tailLength) {
        this.tailLength = tailLength;
    }

    public float getTwinkleSpeed() {
        return twinkleSpeed;
    }

    public void setTwinkleSpeed(float twinkleSpeed) {
        this.twinkleSpeed = twinkleSpeed;
    }

    public float getTwinklePercent() {
        return twinklePercent;
    }

    public void setTwinklePercent(float twinklePercent) {
        this.twinklePercent = twinklePercent;
    }

    public Point2D getTwinkleScale() {
        return twinkleScale;
    }

    public void setTwinkleScale(Point2D twinkleScale) {
        this.twinkleScale = twinkleScale;
    }

    public float getBurstMultiplier() {
        return burstMultiplier;
    }

    public void setBurstMultiplier(float burstMultiplier) {
        this.burstMultiplier = burstMultiplier;
    }

    public float getDrag() {
        return drag;
    }

    public void setDrag(float drag) {
        this.drag = drag;
    }

    public float getSpin() {
        return spin;
    }

    public void setSpin(float spin) {
        this.spin = spin;
    }

    public M2Box getTumble() {
        return tumble;
    }

    public void setTumble(M2Box tumble) {
        this.tumble = tumble;
    }

    public Point3D getWindVector() {
        return windVector;
    }

    public void setWindVector(Point3D windVector) {
        this.windVector = windVector;
    }

    public float getWindTime() {
        return windTime;
    }

    public void setWindTime(float windTime) {
        this.windTime = windTime;
    }

    public float getFollowSpeed1() {
        return followSpeed1;
    }

    public void setFollowSpeed1(float followSpeed1) {
        this.followSpeed1 = followSpeed1;
    }

    public float getFollowScale1() {
        return followScale1;
    }

    public void setFollowScale1(float followScale1) {
        this.followScale1 = followScale1;
    }

    public float getFollowSpeed2() {
        return followSpeed2;
    }

    public void setFollowSpeed2(float followSpeed2) {
        this.followSpeed2 = followSpeed2;
    }

    public float getFollowScale2() {
        return followScale2;
    }

    public void setFollowScale2(float followScale2) {
        this.followScale2 = followScale2;
    }

    public M2Array<Point3D> getSplinePoints() {
        return splinePoints;
    }

    public void setSplinePoints(M2Array<Point3D> splinePoints) {
        this.splinePoints = splinePoints;
    }

    public M2Track<Boolean> getEnableInd() {
        return enableInd;
    }

    public void setEnableInd(M2Track<Boolean> enableInd) {
        this.enableInd = enableInd;
    }
    
    
}
