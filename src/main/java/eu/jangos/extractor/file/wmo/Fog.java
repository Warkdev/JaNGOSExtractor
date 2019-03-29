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
package eu.jangos.extractor.file.wmo;

import eu.jangos.extractor.file.common.CImVector;
import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class Fog {

    private float end;
    private float startScalar;
    private CImVector color = new CImVector();

    public void read(ByteBuffer data) {
        this.end = data.getFloat();
        this.startScalar = data.getFloat();
        this.color.setB(data.get());
        this.color.setG(data.get());
        this.color.setR(data.get());
        this.color.setA(data.get());
    }

    public float getEnd() {
        return end;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    public float getStartScalar() {
        return startScalar;
    }

    public void setStartScalar(float startScalar) {
        this.startScalar = startScalar;
    }

    public CImVector getColor() {
        return color;
    }

    public void setColor(CImVector color) {
        this.color = color;
    }

}
