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

import java.util.Map.Entry;

/**
 *
 * @author Warkdev
 */
public class M2TrackBase {
    private short interpolationType;
    private short globalSequence;
    private M2Array<Entry<Integer, Integer>> interpolationRanges;
    private M2Array<Integer> timestamps;

    public M2TrackBase() {
    }

    public short getInterpolationType() {
        return interpolationType;
    }

    public void setInterpolationType(short interpolationType) {
        this.interpolationType = interpolationType;
    }

    public short getGlobalSequence() {
        return globalSequence;
    }

    public void setGlobalSequence(short globalSequence) {
        this.globalSequence = globalSequence;
    }

    public M2Array<Entry<Integer, Integer>> getInterpolationRanges() {
        return interpolationRanges;
    }

    public void setInterpolationRanges(M2Array<Entry<Integer, Integer>> interpolationRanges) {
        this.interpolationRanges = interpolationRanges;
    }

    public M2Array<Integer> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(M2Array<Integer> timestamps) {
        this.timestamps = timestamps;
    }
    
    
}