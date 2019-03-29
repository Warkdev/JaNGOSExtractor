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
