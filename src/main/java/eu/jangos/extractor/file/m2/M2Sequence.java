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

/**
 *
 * @author Warkdev
 */
public class M2Sequence {
    private short id;
    private short variationIndex;
    private int startTimestamp;
    private int endTimestamp;
    private float moveSpeed;
    private int flags;
    private short frequency;
    private short padding;
    private M2Range replay;
    private short blendTimeIn;
    private short blendTimeOut;
    private M2Bounds bounds;
    private short variationNext;
    private short aliasNext;

    public M2Sequence() {
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public short getVariationIndex() {
        return variationIndex;
    }

    public void setVariationIndex(short variationIndex) {
        this.variationIndex = variationIndex;
    }

    public int getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(int startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public int getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(int endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public short getFrequency() {
        return frequency;
    }

    public void setFrequency(short frequency) {
        this.frequency = frequency;
    }

    public short getPadding() {
        return padding;
    }

    public void setPadding(short padding) {
        this.padding = padding;
    }

    public M2Range getReplay() {
        return replay;
    }

    public void setReplay(M2Range replay) {
        this.replay = replay;
    }

    public short getBlendTimeIn() {
        return blendTimeIn;
    }

    public void setBlendTimeIn(short blendTimeIn) {
        this.blendTimeIn = blendTimeIn;
    }

    public short getBlendTimeOut() {
        return blendTimeOut;
    }

    public void setBlendTimeOut(short blendTimeOut) {
        this.blendTimeOut = blendTimeOut;
    }

    public M2Bounds getBounds() {
        return bounds;
    }

    public void setBounds(M2Bounds bounds) {
        this.bounds = bounds;
    }

    public short getVariationNext() {
        return variationNext;
    }

    public void setVariationNext(short variationNext) {
        this.variationNext = variationNext;
    }

    public short getAliasNext() {
        return aliasNext;
    }

    public void setAliasNext(short aliasNext) {
        this.aliasNext = aliasNext;
    }
    
    
}
