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
