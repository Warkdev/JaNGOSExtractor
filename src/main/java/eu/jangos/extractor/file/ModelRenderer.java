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
package eu.jangos.extractor.file;

import eu.jangos.extractor.file.ObjectRendererIndices;
import eu.jangos.extractor.file.common.Quaternion;
import eu.jangos.extractor.file.exception.FileReaderException;
import eu.jangos.extractor.file.exception.MPQException;
import eu.jangos.extractor.file.exception.ModelRendererException;
import eu.jangos.extractor.file.impl.M2;
import eu.jangos.extractor.rendering.FileType2D;
import eu.jangos.extractor.rendering.FileType3D;
import eu.jangos.extractor.rendering.PolygonMesh;
import eu.jangos.extractor.rendering.Render2DType;
import eu.jangos.extractor.rendering.Render3DType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point3D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Warkdev
 */
public abstract class ModelRenderer {

    private static final Logger logger = LoggerFactory.getLogger(ModelRenderer.class);

    protected NumberFormat formatter = new DecimalFormat("00");
    
    protected PolygonMesh shapeMesh;
    protected List<ObjectRendererIndices> renderedObjectsList;
    protected PolygonMesh liquidMesh;
    protected List<ObjectRendererIndices> renderedLiquidList;
    protected Map<String, M2> modelCache;

    public ModelRenderer() {
        this.liquidMesh = new PolygonMesh();
        this.shapeMesh = new PolygonMesh();
        this.renderedObjectsList = new ArrayList<>();
        this.renderedLiquidList = new ArrayList<>();
    }

    /**
     * Render2D provides a Pane object containing a 2D representation of the
     * requested render type.
     *
     * @param renderType The type of render.
     * @return A Pane object representing the object to be rendered.
     * @throws ModelRendererException This method can throw a converter
     * exception if an error occured during the rendering.
     * @throws FileReaderException This method can throw a file reader exception
     * if an error occured during the reading of the file.
     */
    public abstract Pane render2D(Render2DType renderType) throws ModelRendererException, FileReaderException;

    /**
     * Render3D provides a PolygonMesh object containing a 3D representation of
     * the requested render type.
     *
     * @param renderType The render type that needs to be used when generating
     * this polygon mesh.
     * @param cache A cache of models, in case of generation of mesh with a lot
     * of models, this helps to speed up the mesh generation process.
     * @return A PolygonMesh object representing the object to be rendered.
     * @throws ModelRendererException This method can throw a converter
     * exception if an error occured during the rendering.
     * @throws MPQException This method can throw a MPQ exception if a file to
     * be extracted from the MPQ doesn't exist in the MPQ libraries.
     * @throws FileReaderException This method can throw a File Reader Exception
     * if an extracted file is malformed.
     */
    public abstract PolygonMesh render3D(Render3DType renderType, Map<String, M2> cache) throws ModelRendererException, MPQException, FileReaderException;

    /**
     * Save2D saves an image of a rendered object using the method render2D.
     *
     * @param path The path where the file needs to be stored.
     * @param fileType The file type extension to be saved.
     * @param renderType The render type.
     * @throws ModelRendererException This method throws a converter exception
     * if an error occured during the rendering.
     * @throws FileReaderException This method throws a file reader exception if
     * an error occured during the reading of the file.
     * @return True if the file has been saved, false otherwise.
     */
    public boolean save2D(String path, FileType2D fileType, Render2DType renderType) throws ModelRendererException, FileReaderException {
        switch (fileType) {
            case PNG:
                return savePNG(path, renderType);
            default:
                throw new UnsupportedOperationException("This file type is not supported.");
        }
    }

    /**
     * Save3D saves an image of a rendered object using the method render3D.
     *
     * @param path The path where the file needs to be stored.
     * @param fileType The file type extension to be saved.
     * @param renderType The render type to be used.
     * @param addNormals Indicates whether normals information must be added
     * @param addTextures Indicates whether texture informations must be added
     * to the saved file.
     * @throws ModelRendererException This method can throw a converter
     * exception in case of some conversion went wront.
     * @return True if the file has been saved, false otherwise.
     * @throws eu.jangos.extractor.file.exception.MPQException MPQ Exception can
     * be raised in case there's an error to find the requested file.
     * @throws eu.jangos.extractor.file.exception.FileReaderException File
     * Reader exception can be raised in case there's an error while reading the
     * requested file.
     */
    public boolean save3D(String path, FileType3D fileType, Render3DType renderType, boolean addNormals, boolean addTextures) throws ModelRendererException, MPQException, FileReaderException {
        render3D(renderType, modelCache);

        switch (fileType) {
            case OBJ:
                return saveWavefront(path, renderType, addNormals, addTextures);
            default:
                throw new UnsupportedOperationException("This file type is not supported.");
        }
    }

    /**
     * Return the provided mesh under the form of a String representing a
     * WAvefront OBJ format. This method assumes that the provided mesh is
     * complete and that it can be rendered.
     *
     * @param mesh The mesh to convert to Wavefront OBJ format.
     * @param addNormals Specify whether normals must be added to the OBJ format
     * or not.
     * @param addTextures Specify whether texture coordinates must be added to
     * the OBJ format or not.
     * @return A String containing the content of the Wavefront OBJ format.
     */
    private String getMeshAsOBJ(boolean addNormals, boolean addTextures) {
        StringBuilder sb = new StringBuilder();
        List<String> addedObject = new ArrayList<>();

        boolean added = false;
        int index = 0;
        String search = "";
        int offset = this.shapeMesh.getPoints().size() / 3;

        for (ObjectRendererIndices object : renderedObjectsList) {
            if (!addedObject.contains(object.getObjectName())) {
                addedObject.add(object.getObjectName());
                sb.append("o " + object.getObjectName() + "\n");
            } else {
                added = false;
                index = 0;
                while (!added) {
                    index++;
                    search = object.getObjectName() + "_" + index;
                    if (!addedObject.contains(search)) {
                        addedObject.add(search);
                        added = true;
                    }
                }
                sb.append("o " + search + "\n");
            }

            for (int j = object.getStartPoint(); j < object.getEndPoint(); j += 3) {
                sb.append("v ")
                        .append(this.shapeMesh.getPoints().get(j)).append(" ")
                        .append(this.shapeMesh.getPoints().get(j + 1)).append(" ")
                        .append(this.shapeMesh.getPoints().get(j + 2)).append("\n");
            }

            for (int i = object.getStartIndex(); i < object.getEndIndex(); i++) {
                sb.append("f ");
                for (int j = 0; j < this.shapeMesh.faces[i].length; j += 2) {
                    sb.append(this.shapeMesh.faces[i][j] + 1);

                    if (addNormals) {
                        sb.append("/").append(this.shapeMesh.faces[i][j] + 1);
                    }
                    if (addTextures) {
                        sb.append("/").append(this.shapeMesh.faces[i][j + 1]);
                    }
                    sb.append(" ");
                }
                sb.append("\n");

            }
        }        
        
        for (ObjectRendererIndices object : renderedLiquidList) {
            if (!addedObject.contains(object.getObjectName())) {
                addedObject.add(object.getObjectName());
                sb.append("o " + object.getObjectName() + "\n");
            } else {
                added = false;
                index = 0;
                while (!added) {
                    index++;
                    search = object.getObjectName() + "_" + index;
                    if (!addedObject.contains(search)) {
                        addedObject.add(search);
                        added = true;
                    }
                }
                sb.append("o " + search + "\n");
            }

            for (int j = object.getStartPoint(); j < object.getEndPoint(); j += 3) {
                sb.append("v ")
                        .append(this.liquidMesh.getPoints().get(j)).append(" ")
                        .append(this.liquidMesh.getPoints().get(j + 1)).append(" ")
                        .append(this.liquidMesh.getPoints().get(j + 2)).append("\n");
            }

            for (int i = object.getStartIndex(); i < object.getEndIndex(); i++) {
                sb.append("f ");
                for (int j = 0; j < this.liquidMesh.faces[i].length; j += 2) {
                    sb.append(this.liquidMesh.faces[i][j] + 1 + offset);

                    if (addNormals) {
                        sb.append("/").append(this.liquidMesh.faces[i][j] + 1);
                    }
                    if (addTextures) {
                        sb.append("/").append(this.liquidMesh.faces[i][j + 1]);
                    }
                    sb.append(" ");
                }
                sb.append("\n");

            }
        }
        
        return sb.toString();
    }

    /**
     * This method is saving the OBJ file structure to the file given in
     * parameters.
     *
     * @param file The OBJ file (including path) where the structure needs to be
     * saved.
     * @param renderType
     * @param addNormals
     * @param addTextures
     * @throws ModelRendererException
     */
    private boolean saveWavefront(String file, Render3DType renderType, boolean addNormals, boolean addTextures) throws ModelRendererException {
        if (file == null || file.isEmpty()) {
            throw new ModelRendererException("Provided file is null or empty.");
        }

        String content;

        switch (renderType) {
            case MODEL:
            case TERRAIN:
            case COLLISION_MODEL:                
            case COLLISION_TERRAIN:                                                                                
            case LIQUID:
                content = getMeshAsOBJ(addNormals, addTextures);
                break;
            default:
                throw new ModelRendererException("This render type is not supported");
        }

        File objFile = new File(file);
        if (objFile.exists()) {
            objFile.delete();
        } else {
            objFile.getParentFile().mkdirs();
        }

        try {
            try (OutputStreamWriter writer = new FileWriter(objFile)) {
                writer.write(content);
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Save a JavaFX Pane object to PNG file, this method will also scale down
     * any too large image to a maximum of 5000 * 5000 pixels.
     *
     * @param path The file where this image must be saved.
     * @param renderType The desired render type that will be used to render
     * this object.
     * @return True if the file has been saved, false otherwise.
     * @throws ModelRendererException
     */
    private boolean savePNG(String path, Render2DType renderType) throws ModelRendererException, FileReaderException {
        if (path == null || path.isEmpty()) {
            throw new ModelRendererException("Provided file is null or empty.");
        }

        Pane pane = render2D(renderType);
        double scale = 1;
        int maxSize = 5000;

        if (pane.getPrefHeight() <= 0) {
            pane.setPrefHeight(1);
        }

        if (pane.getPrefWidth() <= 0) {
            pane.setPrefWidth(1);
        }

        if (pane.getPrefWidth() >= maxSize) {
            scale = maxSize / pane.getPrefWidth();
        } else if (pane.getPrefHeight() >= maxSize) {
            scale = maxSize / pane.getPrefHeight();
        }

        logger.debug("Pane size: " + pane.getPrefHeight() + ", " + pane.getPrefWidth());
        WritableImage image = new WritableImage((int) (pane.getPrefWidth() * scale), (int) (pane.getPrefHeight() * scale));
        SnapshotParameters params = new SnapshotParameters();
        params.setTransform(Transform.scale(scale, scale));
        pane.snapshot(params, image);

        File file = new File(path);
        if (file.exists()) {
            file.delete();
        } else {
            file.getParentFile().mkdirs();
        }
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return false;
        }

        return true;
    }

    protected Rotate getAngleAndAxis(Quaternion q) {
        Rotate rotate = new Rotate();

        if (q.getW() > 1) {
            q.normalize(); // if w>1 acos and sqrt will produce errors, this cant happen if quaternion is normalised
        }
        double angle = Math.toDegrees(2 * Math.acos(q.getW()));
        double s = Math.sqrt(1 - q.getW() * q.getW()); // assuming quaternion normalised then w is less than 1, so term always positive.
        double x = q.getX(); // if it is important that axis is normalised then replace with x=1; y=z=0;
        double y = q.getY();
        double z = q.getZ();
        if (s > 0.001) { // test to avoid divide by zero, s is always positive due to sqrt                    
            x = q.getX() / s; // normalise axis
            y = q.getY() / s;
            z = q.getZ() / s;
        }

        rotate.setAngle(angle);
        rotate.setAxis(new Point3D(x, y, z));

        return rotate;
    }

    /**
     * Provides a rotation object matching the Euler angles provided for
     * rotation. This method assumes that the provided information are in
     * degrees. Tip: WoW rotation for Euler angle is YXZ. ZXY. -- XZY
     *
     * @param attitude The first rotation angle.
     * @param heading The second rotation angle.
     * @param bank The last rotation angle.
     * @return A Rotate object with Axis and Rotation angle around that axis.
     */
    protected Rotate getAngleAndPivot(double attitude, double heading, double bank) {
        Rotate rotate = new Rotate();

        heading = Math.toRadians(heading);
        attitude = Math.toRadians(attitude);
        bank = Math.toRadians(bank);

        // Assuming the angles are in radians.
        double c1 = Math.cos(heading / 2);
        double s1 = Math.sin(heading / 2);
        double c2 = Math.cos(attitude / 2);
        double s2 = Math.sin(attitude / 2);
        double c3 = Math.cos(bank / 2);
        double s3 = Math.sin(bank / 2);
        double c1c2 = c1 * c2;
        double s1s2 = s1 * s2;
        double w = c1c2 * c3 - s1s2 * s3;
        double x = c1c2 * s3 + s1s2 * c3;
        double y = s1 * c2 * c3 + c1 * s2 * s3;
        double z = c1 * s2 * c3 - s1 * c2 * s3;
        double angle = 2 * Math.acos(w);
        double norm = x * x + y * y + z * z;
        if (norm < 0.001) { // when all euler angles are zero angle =0 so
            // we can set axis to anything to avoid divide by zero
            x = 1;
            y = z = 0;
        } else {
            norm = Math.sqrt(norm);
            x /= norm;
            y /= norm;
            z /= norm;
        }

        rotate.setAngle(Math.toDegrees(angle));
        rotate.setAxis(new Point3D(x, y, z));

        return rotate;
    }

    protected void clearMesh() {
        clearLiquidMesh();
        clearShapeMesh();
    }

    protected void clearLiquidMesh() {
        this.liquidMesh.getPoints().clear();
        this.liquidMesh.getNormals().clear();
        this.liquidMesh.getFaceSmoothingGroups().clear();
        this.liquidMesh.getTexCoords().clear();
        this.liquidMesh.faces = null;
    }

    protected void clearShapeMesh() {
        this.shapeMesh.getPoints().clear();
        this.shapeMesh.getNormals().clear();
        this.shapeMesh.getFaceSmoothingGroups().clear();
        this.shapeMesh.getTexCoords().clear();
        this.shapeMesh.faces = null;
    }

    public PolygonMesh getShapeMesh() {
        return shapeMesh;
    }

    public void setShapeMesh(PolygonMesh shapeMesh) {
        this.shapeMesh = shapeMesh;
    }

    public PolygonMesh getLiquidMesh() {
        return liquidMesh;
    }

    public void setLiquidMesh(PolygonMesh liquidMesh) {
        this.liquidMesh = liquidMesh;
    }

    public Map<String, M2> getModelCache() {
        return modelCache;
    }

    public void setModelCache(Map<String, M2> modelCache) {
        this.modelCache = modelCache;
    }

    public List<ObjectRendererIndices> getRenderedObjectsList() {
        return renderedObjectsList;
    }

    public List<ObjectRendererIndices> getRenderedLiquidList() {
        return renderedLiquidList;
    }       
}
