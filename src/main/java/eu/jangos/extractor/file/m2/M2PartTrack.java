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

/**
 *
 * @author Warkdev
 */
public class M2PartTrack<T> {
    private M2Array<Short> times;
    private M2Array<T> values;

    public M2PartTrack() {
    }

    public M2Array<Short> getTimes() {
        return times;
    }

    public void setTimes(M2Array<Short> times) {
        this.times = times;
    }

    public M2Array<T> getValues() {
        return values;
    }

    public void setValues(M2Array<T> values) {
        this.values = values;
    }
    
    
}
