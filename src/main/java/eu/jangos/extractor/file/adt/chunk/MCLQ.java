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
package eu.jangos.extractor.file.adt.chunk;

import javafx.geometry.Point3D;
import eu.jangos.extractor.file.ChunkLiquidRenderer;
import eu.jangos.extractor.file.common.MapUnit;
import eu.jangos.extractor.file.exception.FileReaderException;
import eu.jangos.extractor.file.exception.MPQException;
import eu.jangos.extractor.file.exception.ModelRendererException;
import eu.jangos.extractor.file.impl.M2;
import eu.jangos.extractor.rendering.PolygonMesh;
import eu.jangos.extractor.rendering.Render2DType;
import eu.jangos.extractor.rendering.Render3DType;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author Warkdev
 */
public class MCLQ extends ChunkLiquidRenderer {

    private float minHeight;
    private float maxHeight;
    private List<Integer> light;
    private List<Float> height;
    private int nFlowvs;
    private List<SWFlowv> flowvs;

    public MCLQ() {
        this.light = new ArrayList<>();
        this.height = new ArrayList<>();
        super.flags = new ArrayList<>();
        this.flowvs = new ArrayList<>();
    }

    public void read(ByteBuffer data) {
        clear();

        this.minHeight = data.getFloat();
        this.maxHeight = data.getFloat();
        for (int i = 0; i < LIQUID_DATA_LENGTH; i++) {
            for (int j = 0; j < LIQUID_DATA_LENGTH; j++) {
                this.light.add(data.getInt());
                this.height.add(data.getFloat());
            }
        }

        for (int i = 0; i < LIQUID_FLAG_LENGTH; i++) {
            for (int j = 0; j < LIQUID_FLAG_LENGTH; j++) {
                super.flags.add((short) Byte.toUnsignedInt(data.get()));
            }
        }

        this.nFlowvs = data.getInt();
        SWFlowv flow;
        for (int i = 0; i < (nFlowvs == 0 ? 2 : nFlowvs); i++) {
            flow = new SWFlowv();
            flow.read(data);
            this.flowvs.add(flow);
        }
    }

    public float getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public List<Integer> getLight() {
        return light;
    }

    public void setLight(List<Integer> light) {
        this.light = light;
    }

    public List<Float> getHeight() {
        return height;
    }

    public void setHeight(List<Float> height) {
        this.height = height;
    }

    private void clear() {
        this.light.clear();
        this.height.clear();
    }

    private PolygonMesh renderLiquid() {
        clearLiquidMesh();        
        
        // Starting with vertices.   
        List<Point3D> tempVertices = new ArrayList<>();
        for (int x = 0; x < LIQUID_DATA_LENGTH; x++) {
            for (int y = 0; y < LIQUID_DATA_LENGTH; y++) {                                
                tempVertices.add(new Point3D(
                        MapUnit.UNIT_SIZE * x,
                        MapUnit.UNIT_SIZE * y,
                        this.height.get(x * LIQUID_DATA_LENGTH + y)));                
            }                                    
        }

        // Then with indices & texture coord.
        short index = 0;
        int pos;
        List<Short> liquidIndicesList = new ArrayList<>();
        for (int x = 0; x < LIQUID_FLAG_LENGTH; x++) {
            for (int y = 0; y < LIQUID_FLAG_LENGTH; y++) {                
                if (!hasNoLiquid(x, y)) {                    
                    pos = x * (LIQUID_DATA_LENGTH) + y;
                    this.liquidMesh.getPoints().addAll((float) tempVertices.get(pos + 1).getY(), (float) tempVertices.get(pos + 1).getZ(), (float) tempVertices.get(pos + 1).getX());
                    liquidIndicesList.add(index++);

                    this.liquidMesh.getPoints().addAll((float) tempVertices.get(pos).getY(), (float) tempVertices.get(pos).getZ(), (float) tempVertices.get(pos).getX());
                    liquidIndicesList.add(index++);

                    this.liquidMesh.getPoints().addAll((float) tempVertices.get(pos + LIQUID_DATA_LENGTH + 1).getY(), (float) tempVertices.get(pos + LIQUID_DATA_LENGTH + 1).getZ(), (float) tempVertices.get(pos + LIQUID_DATA_LENGTH + 1).getX());
                    liquidIndicesList.add(index++);

                    this.liquidMesh.getPoints().addAll((float) tempVertices.get(pos + LIQUID_DATA_LENGTH).getY(), (float) tempVertices.get(pos + LIQUID_DATA_LENGTH).getZ(), (float) tempVertices.get(pos + LIQUID_DATA_LENGTH).getX());
                    liquidIndicesList.add(index++);
                }
            }            
        }

        int[][] faces = new int[liquidIndicesList.size() / 4][8];        
        for (int face = 0, idx = 0; face < liquidIndicesList.size(); face += 4, idx++) {
            faces[idx][0] = liquidIndicesList.get(face);
            faces[idx][1] = liquidIndicesList.get(face);
            faces[idx][2] = liquidIndicesList.get(face + 2);
            faces[idx][3] = liquidIndicesList.get(face + 2);
            faces[idx][4] = liquidIndicesList.get(face + 3);
            faces[idx][5] = liquidIndicesList.get(face + 3);
            faces[idx][6] = liquidIndicesList.get(face + 1);
            faces[idx][7] = liquidIndicesList.get(face + 1);
            liquidMesh.getFaceSmoothingGroups().addAll(0);
        }
        liquidMesh.faces = ArrayUtils.addAll(liquidMesh.faces, faces);

        return this.liquidMesh;
    }    
    
    public void printFlags(int flag) {
        for(int i = 0; i < LIQUID_FLAG_LENGTH; i++) {
            for (int j = 0; j < LIQUID_FLAG_LENGTH; j++) {
                System.out.print((this.flags.get(i * LIQUID_FLAG_LENGTH + j) & flag)+";");
            }
            System.out.println();
        }
    }
    
    public void printHeight() {
        for(int i = 0; i < LIQUID_DATA_LENGTH; i++) {
            for (int j = 0; j < LIQUID_DATA_LENGTH; j++) {
                System.out.print(this.height.get(i * LIQUID_DATA_LENGTH + j)+";");
            }
            System.out.println();
        }
    }
    
    @Override
    public Pane render2D(Render2DType renderType) throws ModelRendererException, FileReaderException {
        switch (renderType) {
            case RENDER_TILEMAP_LIQUID_ANIMATED:
            case RENDER_TILEMAP_LIQUID_FISHABLE:
            case RENDER_TILEMAP_LIQUID_TYPE:
            case RENDER_TILEMAP_LIQUID_HEIGHTMAP:
                return renderLiquidTileMap(renderType);
            default:
                throw new UnsupportedOperationException("Render type is not supported.");
        }
    }

    @Override
    public PolygonMesh render3D(Render3DType renderType, Map<String, M2> cache) throws ModelRendererException, MPQException, FileReaderException {
        switch (renderType) {
            case LIQUID:
            case COLLISION_TERRAIN:
                return renderLiquid();
            default:
                throw new UnsupportedOperationException("Render type is not supported.");
        }
    }

}
