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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Warkdev
 */
public class M2SkinProfile {
    private M2Array<Short> vertices;
    private M2Array<Short> indices;
    private M2Array<Byte> bones;
    private M2Array<M2SkinSection> subMeshes;
    private M2Array<M2Batch> batches;
    private List<Short> listVertices;
    private List<Short> listIndices;
    private List<Byte> listBones;
    private List<M2SkinSection> listSubMeshes;
    private List<M2Batch> listBatches;
    
    private ByteBuffer skinSection;

    public M2SkinProfile() {
        this.vertices = new M2Array<>();
        this.indices = new M2Array<>();
        this.bones = new M2Array<>();
        this.subMeshes = new M2Array<>();
        this.batches = new M2Array<>();
        
        // Caching objects
        this.listVertices = new ArrayList<>();
        this.listIndices = new ArrayList<>();
        this.listBones = new ArrayList<>();
        this.listSubMeshes = new ArrayList<>();
        this.listBatches = new ArrayList<>();
    }

    public void read(ByteBuffer data) {        
        clear();
        this.skinSection = data.asReadOnlyBuffer();           
        this.skinSection.order(ByteOrder.LITTLE_ENDIAN);
        this.vertices.read(this.skinSection);                
        this.indices.read(this.skinSection);
        this.bones.read(this.skinSection);
        this.subMeshes.read(this.skinSection);
        this.batches.read(this.skinSection);  
    }
    
    private void clear() {
        this.listVertices.clear();
        this.listIndices.clear();
        this.listBones.clear();
        this.listSubMeshes.clear();
        this.listBatches.clear();
    }
    
    public List<Short> getVertices() {        
        if(this.listVertices.size() > 0) {
            return this.listVertices;
        }
        
        this.skinSection.position(this.vertices.getOffset());
        for(int i = 0; i < this.vertices.getSize(); i++) {
            this.listVertices.add(this.skinSection.getShort());
        }
                
        return this.listVertices;
    }   
    
    public List<Short> getIndices() {
        if(this.listIndices.size() > 0) {
            return this.listIndices;
        }
        
        this.skinSection.position(this.indices.getOffset());        
        for(int i = 0; i < this.indices.getSize(); i++) {
            listIndices.add(this.skinSection.getShort());
        }
                
        return listIndices;
    }
    
    public List<Byte> getBones() {
        if(this.listBones.size() > 0) {
            return this.listBones;
        }
        
        this.skinSection.position(this.bones.getOffset());
        for(int i = 0; i < this.bones.getSize(); i++) {
            listBones.add(this.skinSection.get());
        }
                
        return listBones;
    }
    
    public List<M2SkinSection> getSubMeshes() {
        if(this.listSubMeshes.size() > 0) {
            return listSubMeshes;
        }
        
        this.skinSection.position(this.subMeshes.getOffset());
        M2SkinSection section;
        for(int i = 0; i < this.subMeshes.getSize(); i++) {
            section = new M2SkinSection();
            section.read(this.skinSection);
            listSubMeshes.add(section);
        }
        
        return listSubMeshes;
    }
    
    public List<M2Batch> getTextureUnit() {
        if(this.listBatches.size() > 0) {
            return listBatches;
        }
        
        this.skinSection.position(this.batches.getOffset());
        M2Batch batch;
        for(int i = 0; i < this.batches.getSize(); i++) {
            batch = new M2Batch();
            batch.read(this.skinSection);
            listBatches.add(batch);
        }
        
        return listBatches;
    }
}
