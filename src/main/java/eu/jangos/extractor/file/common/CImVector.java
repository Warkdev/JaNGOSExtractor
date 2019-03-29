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
package eu.jangos.extractor.file.common;

import java.nio.ByteBuffer;

/**
 *
 * @author Warkdev
 */
public class CImVector {
    private byte b;
    private byte g;
    private byte r;
    private byte a;

    public void read(ByteBuffer data) {
        this.b = data.get();
        this.g = data.get();
        this.r = data.get();
        this.a = data.get();
    }
    
    public byte getB() {
        return b;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public byte getG() {
        return g;
    }

    public void setG(byte g) {
        this.g = g;
    }

    public byte getR() {
        return r;
    }

    public void setR(byte r) {
        this.r = r;
    }

    public byte getA() {
        return a;
    }

    public void setA(byte a) {
        this.a = a;
    }
    
    
}
