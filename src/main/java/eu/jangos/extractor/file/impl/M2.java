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
package eu.jangos.extractor.file.impl;

import eu.jangos.extractor.file.FileReader;
import eu.jangos.extractor.file.exception.FileReaderException;
import eu.jangos.extractor.file.exception.M2Exception;
import eu.jangos.extractor.file.exception.MPQException;
import eu.jangos.extractor.file.exception.ModelRendererException;
import eu.jangos.extractor.file.m2.M2Array;
import eu.jangos.extractor.file.m2.M2Attachment;
import eu.jangos.extractor.file.m2.M2Camera;
import eu.jangos.extractor.file.m2.M2Color;
import eu.jangos.extractor.file.m2.M2CompBone;
import eu.jangos.extractor.file.m2.M2Event;
import eu.jangos.extractor.file.m2.M2Light;
import eu.jangos.extractor.file.m2.M2Loop;
import eu.jangos.extractor.file.m2.M2Material;
import eu.jangos.extractor.file.m2.M2Particle;
import eu.jangos.extractor.file.m2.M2Ribbon;
import eu.jangos.extractor.file.m2.M2Sequence;
import eu.jangos.extractor.file.m2.M2SkinProfile;
import eu.jangos.extractor.file.m2.M2SkinSection;
import eu.jangos.extractor.file.m2.M2Texture;
import eu.jangos.extractor.file.m2.M2TextureTransform;
import eu.jangos.extractor.file.m2.M2TextureWeight;
import eu.jangos.extractor.file.m2.M2Vertex;
import eu.jangos.extractor.file.mpq.MPQManager;
import eu.jangos.extractor.rendering.PolygonMesh;
import eu.jangos.extractor.rendering.Render2DType;
import eu.jangos.extractor.rendering.Render3DType;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point3D;
import javafx.scene.layout.Pane;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Warkdev
 */
public class M2 extends FileReader {

    private static final Logger logger = LoggerFactory.getLogger(M2.class);

    private static final String HEADER_MD20 = "MD20";
    private static final int SUPPORTED_VERSION = 256;

    // Magic & version.
    private int version;

    // Header info.
    private M2Array<Character> name;
    private int globalFlags;
    private M2Array<M2Loop> globalLoops;
    private M2Array<M2Sequence> sequences;
    private M2Array<Short> sequenceLookups;
    private M2Array<?> playableAnimationLookup;
    private M2Array<M2CompBone> bones;
    private M2Array<Short> keyBoneLookup;
    private M2Array<M2Vertex> vertices;
    private M2Array<M2SkinProfile> skinProfiles;
    private M2Array<M2Color> colors;
    private M2Array<M2Texture> textures;
    private M2Array<M2TextureWeight> textureWeights;
    private M2Array<?> unknown;
    private M2Array<M2TextureTransform> textureTransforms;
    private M2Array<Short> replacableTextureLookup;
    private M2Array<M2Material> materials;
    private M2Array<Short> boneLookupTable;
    private M2Array<Short> textureLookupTable;
    private M2Array<Short> texUnitLookupTable;
    private M2Array<Short> transparencyLookupTable;
    private M2Array<Short> textureTransformsLookupTable;
    private BoundingBox boundingBox;
    private float boundingSphereRadius;
    private BoundingBox collisionBox;
    private float collisionSphereRadius;
    private M2Array<Short> collisionTriangles;
    private M2Array<Point3D> collisionVertices;
    private M2Array<Point3D> collisionNormals;
    private M2Array<M2Attachment> attachments;
    private M2Array<Short> attachmentLookupTable;
    private M2Array<M2Event> events;
    private M2Array<M2Light> lights;
    private M2Array<M2Camera> cameras;
    private M2Array<Short> cameraLookupTable;
    private M2Array<M2Ribbon> ribbonEmitters;
    private M2Array<M2Particle> particleEmitters;
    private M2Array<Short> textureCombinerCombos;
    private List<M2Vertex> listVertices;
    private List<M2SkinProfile> listSkinProfiles;
    private List<Point3D> listCollisionVertices;
    private List<Point3D> listCollisionNormals;
    private List<Short> listCollisionTriangles;    

    // Render variables.
    private boolean cut = false;
    private int view = 0;
    private float maxHeight = 0;

    public M2() {
        this.name = new M2Array<>();
        this.globalLoops = new M2Array<>();
        this.sequences = new M2Array<>();
        this.sequenceLookups = new M2Array<>();
        this.playableAnimationLookup = new M2Array<Object>();
        this.keyBoneLookup = new M2Array<>();
        this.vertices = new M2Array<>();
        this.bones = new M2Array<>();
        this.skinProfiles = new M2Array<>();
        this.colors = new M2Array<>();
        this.textures = new M2Array<>();
        this.textureWeights = new M2Array<>();
        this.unknown = new M2Array<Object>();
        this.textureTransforms = new M2Array<>();
        this.replacableTextureLookup = new M2Array<>();
        this.materials = new M2Array<>();
        this.boneLookupTable = new M2Array<>();
        this.textureLookupTable = new M2Array<>();
        this.texUnitLookupTable = new M2Array<>();
        this.transparencyLookupTable = new M2Array<>();
        this.textureTransformsLookupTable = new M2Array<>();        
        this.boundingSphereRadius = 0;        
        this.collisionSphereRadius = 0;
        this.collisionTriangles = new M2Array<>();
        this.collisionVertices = new M2Array<>();
        this.collisionNormals = new M2Array<>();
        this.attachments = new M2Array<>();
        this.attachmentLookupTable = new M2Array<>();
        this.events = new M2Array<>();
        this.lights = new M2Array<>();
        this.cameras = new M2Array<>();
        this.cameraLookupTable = new M2Array<>();
        this.ribbonEmitters = new M2Array<>();
        this.particleEmitters = new M2Array<>();
        this.textureCombinerCombos = new M2Array<>();

        // Caching objects.
        this.listVertices = new ArrayList<>();
        this.listSkinProfiles = new ArrayList<>();
        this.listCollisionVertices = new ArrayList<>();
        this.listCollisionTriangles = new ArrayList<>();
        this.listCollisionNormals = new ArrayList<>();
    }

    public void init(MPQManager manager, String filename) throws IOException, FileReaderException, MPQException {
        init(manager, filename, false);
    }

    @Override
    public void init(MPQManager manager, String filename, boolean loadChildren) throws IOException, FileReaderException, MPQException {
        super.init = false;

        super.data = ByteBuffer.wrap(manager.getMPQForFile(filename).extractFileAsBytes(filename));
        if (data.remaining() == 0) {
            logger.error("Data array for ADT " + filename + " is empty.");
            throw new M2Exception("Data array is empty.");
        }
        super.data.order(ByteOrder.LITTLE_ENDIAN);
        super.filename = filename;

        clear();
        readHeader();
        super.init = true;
    }

    private void readHeader() throws FileReaderException {
        checkHeader(HEADER_MD20, false);

        // Version.
        int version = super.data.getInt();
        if (SUPPORTED_VERSION != version) {
            throw new M2Exception("Expected version " + SUPPORTED_VERSION + ", version found: " + version);
        }

        // Reading now all offsets & size of chunks data in the M2.
        this.name.read(super.data);
        this.globalFlags = super.data.getInt();
        this.globalLoops.read(super.data);
        this.sequences.read(super.data);
        this.sequenceLookups.read(super.data);
        this.playableAnimationLookup.read(super.data);
        this.bones.read(super.data);
        this.keyBoneLookup.read(super.data);
        this.vertices.read(super.data);
        this.skinProfiles.read(super.data);
        this.colors.read(super.data);
        this.textures.read(super.data);
        this.textureWeights.read(super.data);
        this.unknown.read(super.data);
        this.textureTransforms.read(super.data);
        this.replacableTextureLookup.read(super.data);
        this.materials.read(super.data);
        this.boneLookupTable.read(super.data);
        this.textureLookupTable.read(super.data);
        this.texUnitLookupTable.read(super.data);
        this.transparencyLookupTable.read(super.data);
        this.textureTransformsLookupTable.read(super.data);
        Point3D min = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        Point3D max = new Point3D(data.getFloat(), data.getFloat(), data.getFloat()); 
        this.boundingBox = new BoundingBox(min.getZ(), min.getX(), min.getY(), max.getZ() - min.getZ(), max.getX() - min.getX(), max.getY() - min.getY());        
        this.boundingSphereRadius = super.data.getFloat();
        max = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        min = new Point3D(data.getFloat(), data.getFloat(), data.getFloat()); 
        this.collisionBox = new BoundingBox(min.getZ(), min.getX(), min.getY(), max.getZ() - min.getZ(), max.getX() - min.getX(), max.getY() - min.getY());        
        this.collisionSphereRadius = super.data.getFloat();
        this.collisionTriangles.read(super.data);
        this.collisionVertices.read(super.data);
        this.collisionNormals.read(super.data);
        this.attachments.read(super.data);
        this.attachmentLookupTable.read(super.data);
        this.events.read(super.data);
        this.lights.read(super.data);
        this.cameras.read(super.data);
        this.cameraLookupTable.read(super.data);
        this.ribbonEmitters.read(super.data);
        this.particleEmitters.read(super.data);
        this.textureCombinerCombos.read(super.data);
    }

    public List<M2Vertex> getVertices() throws M2Exception {
        if (!init) {
            throw new M2Exception("M2 file has not been initialized, please use init(data) function to initialize your M2 file !");
        }

        // Prefer to use cached objects as they will be re-used many times.
        if (this.listVertices.size() > 0) {
            return this.listVertices;
        }

        M2Vertex vertex;

        super.data.position(this.vertices.getOffset());
        for (int i = 0; i < this.vertices.getSize(); i++) {
            vertex = new M2Vertex();
            vertex.read(super.data);
            this.listVertices.add(vertex);
        }

        return this.listVertices;
    }

    public List<Point3D> getCollisionVertices() throws M2Exception {
        if (!init) {
            throw new M2Exception("M2 file has not been initialized, please use init(data) function to initialize your M2 file !");
        }

        // Prefer to use cached objects as they will be re-used many times.
        if (this.listCollisionVertices.size() > 0) {
            return this.listCollisionVertices;
        }        

        super.data.position(this.collisionVertices.getOffset());
        for (int i = 0; i < this.collisionVertices.getSize(); i++) {
            this.listCollisionVertices.add(new Point3D(super.data.getFloat(), super.data.getFloat(), super.data.getFloat()));
        }

        return this.listCollisionVertices;
    }
    
    public List<Point3D> getCollisionNormals() throws M2Exception {
        if (!init) {
            throw new M2Exception("M2 file has not been initialized, please use init(data) function to initialize your M2 file !");
        }

        // Prefer to use cached objects as they will be re-used many times.
        if (this.listCollisionNormals.size() > 0) {
            return this.listCollisionNormals;
        }        

        super.data.position(this.collisionNormals.getOffset());
        for (int i = 0; i < this.collisionNormals.getSize(); i++) {
            this.listCollisionNormals.add(new Point3D(super.data.getFloat(), super.data.getFloat(), super.data.getFloat()));
        }

        return this.listCollisionNormals;
    }
    
    public List<Short> getCollisionTriangles() throws M2Exception {
        if (!init) {
            throw new M2Exception("M2 file has not been initialized, please use init(data) function to initialize your M2 file !");
        }

        // Prefer to use cached objects as they will be re-used many times.
        if (this.listCollisionTriangles.size() > 0) {
            return this.listCollisionTriangles;
        }        

        super.data.position(this.collisionTriangles.getOffset());
        for (int i = 0; i < this.collisionTriangles.getSize(); i++) {
            this.listCollisionTriangles.add(super.data.getShort());
        }

        return this.listCollisionTriangles;
    }
    
    public List<M2SkinProfile> getSkins() throws M2Exception {
        if (!init) {
            throw new M2Exception("M2 file has not been initialized, please use init(data) function to initialize your M2 file !");
        }

        // Prefer to use cached objects as they will be re-used many times.
        if (this.listSkinProfiles.size() > 0) {
            return this.listSkinProfiles;
        }

        M2SkinProfile skin;

        super.data.position(this.skinProfiles.getOffset());
        for (int i = 0; i < this.skinProfiles.getSize(); i++) {
            skin = new M2SkinProfile();
            skin.read(super.data);
            this.listSkinProfiles.add(skin);
        }

        return this.listSkinProfiles;
    }

    public List<Short> getTextureLookup() {
        List<Short> listTextureLookup = new ArrayList<>();

        super.data.position(this.textureLookupTable.getOffset());
        for (int i = 0; i < this.textureLookupTable.getSize(); i++) {
            listTextureLookup.add(super.data.getShort());
        }

        return listTextureLookup;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public BoundingBox getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(BoundingBox collisionBox) {
        this.collisionBox = collisionBox;
    }
        
    private void clear() {
        this.listVertices.clear();
        this.listSkinProfiles.clear();
        this.listCollisionVertices.clear();
        this.listCollisionNormals.clear();
        this.listCollisionTriangles.clear();
    }

    @Override
    public Pane render2D(Render2DType renderType) throws ModelRendererException, FileReaderException {
        throw new UnsupportedOperationException("This render is not supported for models");
    }

    @Override
    public PolygonMesh render3D(Render3DType renderType, Map<String, M2> cache) throws ModelRendererException, MPQException, FileReaderException {
        switch (renderType) {
            case MODEL:
                return renderModel();
            case COLLISION_MODEL:
                return renderCollisionModel();
            default:
                throw new UnsupportedOperationException("This render type is not supported for models");
        }
    }

    private PolygonMesh renderCollisionModel() throws ModelRendererException {
        if (init == false) {
            throw new ModelRendererException("M2FileReader is null");
        }
        
        clearShapeMesh();

        try {
            // The model doesn't have any vertice.
            if (getCollisionVertices().isEmpty()) {                                
                return this.shapeMesh;
            }            

            for (Point3D point : getCollisionVertices()) {
                shapeMesh.getPoints().addAll((float) point.getY(), (float) point.getZ(), (float) point.getX());                
                //shapeMesh.getTexCoords().addAll(v.getTexCoords()[0].x, v.getTexCoords()[0].y);
            }
            
            for (Point3D point : getCollisionNormals()) {
                shapeMesh.getNormals().addAll((float) point.getY(), (float) point.getZ(), (float) point.getX());
            }
            
            List<Short> listIndices = getCollisionTriangles();
            int faces[][] = new int[this.listCollisionTriangles.size() / 3][6];
            int idx = 0;
            for (int face = 0; face < listIndices.size(); face += 3) {
                faces[idx][5] = listIndices.get(face + 2);
                faces[idx][4] = listIndices.get(face + 2);
                faces[idx][3] = listIndices.get(face + 1);
                faces[idx][2] = listIndices.get(face + 1);
                faces[idx][1] = listIndices.get(face);
                faces[idx][0] = listIndices.get(face);
                idx++;
                
                shapeMesh.getFaceSmoothingGroups().addAll(0);
            }
            
            shapeMesh.faces = faces;                        
        } catch (M2Exception ex) {
            logger.error("Error while reading the M2 content " + ex.getMessage());
            throw new ModelRendererException("Error while reading the M2 content " + ex.getMessage());
        }

        return shapeMesh;
    }
    
    private PolygonMesh renderModel() throws ModelRendererException {
        if (init == false) {
            throw new ModelRendererException("M2FileReader is null");
        }

        if (view < 0 || view > 3) {
            throw new ModelRendererException("View number must be between 0 and 3");
        }

        clearShapeMesh();

        try {
            // The model doesn't have any vertice.
            if (getVertices().isEmpty()) {
                logger.debug("[Model: "+this.filename+" cannot be rendered, it doesn't have vertex.");
                throw new ModelRendererException("The model cannot be rendered as it doesn't have any vertex");
            }            

            for (M2Vertex v : getVertices()) {
                shapeMesh.getPoints().addAll((float) v.getPosition().getY(), (float) v.getPosition().getZ(), (float) v.getPosition().getX());
                shapeMesh.getNormals().addAll((float) v.getNormal().getY(), (float) v.getNormal().getZ(), (float) v.getNormal().getX());
                shapeMesh.getTexCoords().addAll((float) v.getTexCoords()[0].getX(), (float) v.getTexCoords()[0].getY());
            }

            List<M2SkinProfile> listSkins = getSkins();
            for (int i = 0; i < listSkins.get(view).getSubMeshes().size(); i++) {
                List<M2SkinSection> listSkinSections = listSkins.get(view).getSubMeshes();
                List<Short> listIndices = listSkins.get(view).getIndices();

                // Adding the faces counter clock-wise.
                int[][] faces = new int[listSkinSections.get(i).getIndexCount() / 3][6];
                for (int face = listSkinSections.get(i).getIndexStart(), idx = 0; face < listSkinSections.get(i).getIndexStart() + listSkinSections.get(i).getIndexCount(); face += 3, idx++) {
                    faces[idx][5] = listIndices.get(face + 2);
                    faces[idx][4] = listIndices.get(face + 2);
                    faces[idx][3] = listIndices.get(face + 1);
                    faces[idx][2] = listIndices.get(face + 1);
                    faces[idx][1] = listIndices.get(face);
                    faces[idx][0] = listIndices.get(face);

                    shapeMesh.getFaceSmoothingGroups().addAll(0);
                }

                shapeMesh.faces = ArrayUtils.addAll(shapeMesh.faces, faces);
            }
        } catch (M2Exception ex) {
            logger.error("Error while reading the M2 content " + ex.getMessage());
            throw new ModelRendererException("Error while reading the M2 content " + ex.getMessage());
        }

        return shapeMesh;
    }
}
