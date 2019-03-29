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
public class WMOMaterials {

    private int flags;
    private int shader;
    private int blendMode;
    private int diffuseNameIndex;
    private CImVector sidnColor = new CImVector();
    private CImVector frameSidnColor = new CImVector();
    private int envNameIndex;
    private int diffColor;
    /*
    Foreign key to TerrainTypeRec.dbc
     */
    private int idTerrainTypeRec;
    private int texture2;
    private int color2;
    private int flags2;
    private int[] runTimeData = new int[4];

    public WMOMaterials() {
    }

    public void read(ByteBuffer data) {
        
        this.flags = data.getInt();
        this.shader = data.getInt();
        this.blendMode = data.getInt();
        this.diffuseNameIndex = data.getInt();
        this.sidnColor.setB(data.get());
        this.sidnColor.setG(data.get());
        this.sidnColor.setR(data.get());
        this.sidnColor.setA(data.get());
        this.frameSidnColor.setB(data.get());
        this.frameSidnColor.setG(data.get());
        this.frameSidnColor.setR(data.get());
        this.frameSidnColor.setA(data.get());
        this.envNameIndex = data.getInt();
        this.diffColor = data.getInt();
        this.idTerrainTypeRec = data.getInt();
        this.texture2 = data.getInt();
        this.color2 = data.getInt();
        this.flags2 = data.getInt();
        for (int i = 0; i < runTimeData.length; i++) {
            this.runTimeData[i] = data.getInt();
        }
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }
    
    public int getShader() {
        return shader;
    }

    public void setShader(int shader) {
        this.shader = shader;
    }

    public int getBlendMode() {
        return blendMode;
    }

    public void setBlendMode(int blendMode) {
        this.blendMode = blendMode;
    }

    public int getDiffuseNameIndex() {
        return diffuseNameIndex;
    }

    public void setDiffuseNameIndex(int diffuseNameIndex) {
        this.diffuseNameIndex = diffuseNameIndex;
    }

    public CImVector getSidnColor() {
        return sidnColor;
    }

    public void setSidnColor(CImVector sidnColor) {
        this.sidnColor = sidnColor;
    }

    public CImVector getFrameSidnColor() {
        return frameSidnColor;
    }

    public void setFrameSidnColor(CImVector frameSidnColor) {
        this.frameSidnColor = frameSidnColor;
    }

    public int getEnvNameIndex() {
        return envNameIndex;
    }

    public void setEnvNameIndex(int envNameIndex) {
        this.envNameIndex = envNameIndex;
    }

    public int getDiffColor() {
        return diffColor;
    }

    public void setDiffColor(int diffColor) {
        this.diffColor = diffColor;
    }

    public int getIdTerrainTypeRec() {
        return idTerrainTypeRec;
    }

    public void setIdTerrainTypeRec(int idTerrainTypeRec) {
        this.idTerrainTypeRec = idTerrainTypeRec;
    }

    public int getTexture2() {
        return texture2;
    }

    public void setTexture2(int texture2) {
        this.texture2 = texture2;
    }

    public int getColor2() {
        return color2;
    }

    public void setColor2(int color2) {
        this.color2 = color2;
    }

    public int getFlags2() {
        return flags2;
    }

    public void setFlags2(int flags2) {
        this.flags2 = flags2;
    }

    public int[] getRunTimeData() {
        return runTimeData;
    }

    public void setRunTimeData(int[] runTimeData) {
        this.runTimeData = runTimeData;
    }
}
