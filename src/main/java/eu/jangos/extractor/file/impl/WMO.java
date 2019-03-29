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
import eu.jangos.extractor.file.common.C4Plane;
import eu.jangos.extractor.file.common.CArgb;
import eu.jangos.extractor.file.common.MapUnit;
import eu.jangos.extractor.file.exception.FileReaderException;
import eu.jangos.extractor.file.exception.MPQException;
import eu.jangos.extractor.file.exception.ModelRendererException;
import eu.jangos.extractor.file.exception.WMOException;
import eu.jangos.extractor.file.mpq.MPQManager;
import eu.jangos.extractor.file.wmo.WMOBlock;
import eu.jangos.extractor.file.wmo.WMODoodadDef;
import eu.jangos.extractor.file.wmo.WMODoodadSet;
import eu.jangos.extractor.file.wmo.WMOFog;
import eu.jangos.extractor.file.wmo.WMOGroupInfo;
import eu.jangos.extractor.file.wmo.WMOLight;
import eu.jangos.extractor.file.wmo.WMOMaterials;
import eu.jangos.extractor.file.wmo.WMOPortal;
import eu.jangos.extractor.file.wmo.WMOPortalRef;
import eu.jangos.extractor.rendering.PolygonMesh;
import eu.jangos.extractor.rendering.Render2DType;
import eu.jangos.extractor.rendering.Render3DType;
import eu.jangos.utils.FlagUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import systems.crigges.jmpq3.JMpqException;

/**
 *
 * @author Warkdev
 */
public class WMO extends FileReader {

    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);

    // Section for WMO ROOT File.
    private static final String HEADER_MVER = "MVER";
    private static final String HEADER_MOHD = "MOHD";
    private static final String HEADER_MOTX = "MOTX";
    private static final String HEADER_MOMT = "MOMT";
    private static final int SIZE_MOMT = 64;
    private static final String HEADER_MOGN = "MOGN";
    private static final String HEADER_MOGI = "MOGI";
    private static final int SIZE_MOGI = 32;
    private static final String HEADER_MOSB = "MOSB";
    private static final String HEADER_MOPV = "MOPV";
    private static final int SIZE_MOPV = 12;
    private static final String HEADER_MOPT = "MOPT";
    private static final int SIZE_MOPT = 20;
    private static final String HEADER_MOPR = "MOPR";
    private static final int SIZE_MOPR = 8;
    private static final String HEADER_MOVV = "MOVV";
    private static final int SIZE_MOVV = 12;
    private static final String HEADER_MOVB = "MOVB";
    private static final int SIZE_MOVB = 4;
    private static final String HEADER_MOLT = "MOLT";
    private static final int SIZE_MOLT = 48;
    private static final String HEADER_MODS = "MODS";
    private static final int SIZE_MODS = 32;
    private static final String HEADER_MODN = "MODN";
    private static final String HEADER_MODD = "MODD";
    private static final int SIZE_MODD = 40;
    private static final String HEADER_MFOG = "MFOG";
    private static final int SIZE_MFOG = 48;
    private static final String HEADER_MCVP = "MCVP";
    private static final int SIZE_MCVP = 16;

    public static final int FLAG_DO_NOT_ATTENUATE_VERTICES = 0x1;
    public static final int FLAG_USE_UNIFIED_RENDER_PATH = 0x2;
    public static final int FLAG_USE_LIQUID_FROM_DBC = 0x4;
    public static final int FLAG_DO_NOT_FIX_VERTEX_COLOR_ALPHA = 0x8;

    private static final int SUPPORTED_VERSION = 17;

    private NumberFormat formatter = new DecimalFormat("000");

    private int nMaterials;
    private int nGroups;
    private int nPortals;
    private int nLights;
    private int nModels;
    private int nDoodads;
    private int nDoodaSets;

    private CArgb ambColor = new CArgb();

    /**
     * Foreign key to WMOAreaTAble.dbc.
     */
    private int wmoAreaTableID;
    private BoundingBox boundingBox;
    private short flags;
    private short numLod;

    private List<String> textureNameList = new ArrayList<>();
    private List<WMOMaterials> materials = new ArrayList<>();
    private List<String> groupNameList = new ArrayList<>();
    private List<WMOGroupInfo> groupInfoList = new ArrayList<>();
    private String skyBoxName;
    private List<Point3D> portalVertexList = new ArrayList<>();
    private List<WMOPortal> portalList = new ArrayList<>();
    private List<WMOPortalRef> portalRefList = new ArrayList<>();
    private List<Point3D> visibleBlockVerticesList = new ArrayList<>();
    private List<WMOBlock> visibleBlockList = new ArrayList<>();
    private List<WMOLight> lightList = new ArrayList<>();
    private List<WMODoodadSet> doodadSetList = new ArrayList<>();
    private Map<Integer, String> doodadNameMap = new HashMap<>();
    private List<WMODoodadDef> doodadDefList = new ArrayList<>();
    private List<WMOFog> fogList = new ArrayList<>();
    private List<C4Plane> convexVolumePlanesList = new ArrayList<>();

    private List<WMOGroup> wmoGroupList = new ArrayList<>();
    
    // Options for rendering.
    private boolean addModels = false;
    private boolean renderWMOBoundaries = false;
    private boolean renderGroup = false;
    private boolean renderNonLiquidGroup = false;
    private boolean displayGroupNumber = false;
    
    public boolean isRootFile(byte[] array) throws FileReaderException {
        super.data = ByteBuffer.wrap(array);
        super.data.order(ByteOrder.LITTLE_ENDIAN);
        logger.debug("Checking is filename " + filename + " is a root WMO.");

        if (array.length == 0) {
            logger.error("Data array for file " + filename + " is empty");
            throw new WMOException("Data array is empty.");
        }

        checkHeader(HEADER_MVER);
        // Skip the size.
        super.data.getInt();
        int version = super.data.getInt();
        if (version != SUPPORTED_VERSION) {
            throw new WMOException("WMO Version is not supported by this reader.");
        }

        StringBuilder sb = new StringBuilder();
        byte[] header = new byte[4];

        data.get(header);

        sb = sb.append(new String(header)).reverse();

        if (!sb.toString().equals(HEADER_MOHD)) {
            return false;
        }

        return true;
    }

    public void init(MPQManager manager, String filename) throws JMpqException, FileReaderException, MPQException {
        init(manager, filename, false);
    }

    @Override
    public void init(MPQManager manager, String filename, boolean loadChildren) throws FileReaderException, MPQException, JMpqException {
        super.data = ByteBuffer.wrap(manager.getMPQForFile(filename).extractFileAsBytes(filename));

        if (data.remaining() == 0) {
            logger.error("Data array for file " + filename + " is empty");
            throw new WMOException("Data array is empty.");
        }

        super.data.order(ByteOrder.LITTLE_ENDIAN);
        super.filename = filename;
        super.manager = manager;
        clear();

        checkHeader(HEADER_MVER);
        // Skip the size.
        super.data.getInt();
        int version = super.data.getInt();
        if (version != SUPPORTED_VERSION) {
            throw new WMOException("WMO Version is not supported by this reader.");
        }

        int chunkSize = 0;

        // We're now reading the header of the file.
        checkHeader(HEADER_MOHD);
        // Skip the size.
        super.data.getInt();

        this.nMaterials = super.data.getInt();
        this.nGroups = super.data.getInt();
        this.nPortals = super.data.getInt();
        this.nLights = super.data.getInt();
        this.nModels = super.data.getInt();
        this.nDoodads = super.data.getInt(); // Suppossingly amount of MODD
        this.nDoodaSets = super.data.getInt(); // Supposingly amount of MODS
        this.ambColor.setR(super.data.get());
        this.ambColor.setG(super.data.get());
        this.ambColor.setB(super.data.get());
        this.ambColor.setA(super.data.get());
        this.wmoAreaTableID = super.data.getInt();
        Point3D min = new Point3D(data.getFloat(), data.getFloat(), data.getFloat());
        Point3D max = new Point3D(data.getFloat(), data.getFloat(), data.getFloat()); 
        this.boundingBox = new BoundingBox(min.getY(), min.getZ(), min.getX(), max.getY() - min.getY(), max.getZ() - min.getZ(), max.getX() - min.getX());        
        this.flags = super.data.getShort();
        this.numLod = super.data.getShort();

        this.textureNameList = readStringChunk(super.data.position(), HEADER_MOTX);

        checkHeader(HEADER_MOMT);
        chunkSize = data.getInt() / SIZE_MOMT;

        WMOMaterials material;
        for (int i = 0; i < nMaterials; i++) {
            material = new WMOMaterials();
            material.read(super.data);
            this.materials.add(material);
        }

        this.groupNameList = readStringChunk(super.data.position(), HEADER_MOGN);

        checkHeader(HEADER_MOGI);
        chunkSize = data.getInt() / SIZE_MOGI;

        WMOGroupInfo groupInfo;
        for (int i = 0; i < nGroups; i++) {
            groupInfo = new WMOGroupInfo();
            groupInfo.read(super.data);
            groupInfoList.add(groupInfo);
        }

        checkHeader(HEADER_MOSB);
        chunkSize = super.data.getInt();
        this.skyBoxName = readString(super.data);
        // There are 0 padding at the end of the String.
        if (this.skyBoxName.length() < chunkSize) {
            super.data.position(super.data.position() + (chunkSize - this.skyBoxName.length()) - 1);
        }

        checkHeader(HEADER_MOPV);
        chunkSize = super.data.getInt() / SIZE_MOPV;

        Point3D portalVertex;
        for (int i = 0; i < chunkSize; i++) {
            portalVertex = new Point3D(super.data.getFloat(), super.data.getFloat(), super.data.getFloat());
            this.portalVertexList.add(portalVertex);
        }

        checkHeader(HEADER_MOPT);
        chunkSize = super.data.getInt() / SIZE_MOPT;

        WMOPortal portal;
        for (int i = 0; i < nPortals; i++) {
            portal = new WMOPortal();
            portal.read(super.data);
            this.portalList.add(portal);
        }

        checkHeader(HEADER_MOPR);
        chunkSize = super.data.getInt() / SIZE_MOPR;

        WMOPortalRef portalRef;
        for (int i = 0; i < chunkSize; i++) {
            portalRef = new WMOPortalRef();
            portalRef.read(super.data);
            this.portalRefList.add(portalRef);
        }

        checkHeader(HEADER_MOVV);
        chunkSize = super.data.getInt() / SIZE_MOVV;

        Point3D visibleBlockVertices;
        for (int i = 0; i < chunkSize; i++) {            
            visibleBlockVertices = new Point3D(super.data.getFloat(), super.data.getFloat(), super.data.getFloat());
            this.visibleBlockVerticesList.add(visibleBlockVertices);
        }

        checkHeader(HEADER_MOVB);
        chunkSize = super.data.getInt() / SIZE_MOVB;

        WMOBlock visibleBlock;
        for (int i = 0; i < chunkSize; i++) {
            visibleBlock = new WMOBlock();
            visibleBlock.read(super.data);
            this.visibleBlockList.add(visibleBlock);
        }

        checkHeader(HEADER_MOLT);
        chunkSize = super.data.getInt() / SIZE_MOLT;

        WMOLight light;
        for (int i = 0; i < chunkSize; i++) {
            light = new WMOLight();
            light.read(super.data);
            this.lightList.add(light);
        }

        checkHeader(HEADER_MODS);
        chunkSize = super.data.getInt() / SIZE_MODS;

        WMODoodadSet doodadSet;
        for (int i = 0; i < chunkSize; i++) {
            doodadSet = new WMODoodadSet();
            doodadSet.read(super.data);
            this.doodadSetList.add(doodadSet);
        }

        this.doodadNameMap = readStringChunkAsMap(super.data.position(), HEADER_MODN);

        checkHeader(HEADER_MODD);
        chunkSize = super.data.getInt() / SIZE_MODD;

        WMODoodadDef doodadDef;
        for (int i = 0; i < chunkSize; i++) {
            doodadDef = new WMODoodadDef();
            doodadDef.read(super.data);
            this.doodadDefList.add(doodadDef);
        }

        checkHeader(HEADER_MFOG);
        chunkSize = super.data.getInt() / SIZE_MFOG;

        WMOFog fog;
        for (int i = 0; i < chunkSize; i++) {
            fog = new WMOFog();
            fog.read(super.data);
            this.fogList.add(fog);
        }

        // MCVP is an optional header.
        if (data.hasRemaining()) {
            checkHeader(HEADER_MCVP);
            chunkSize = super.data.getInt() / SIZE_MCVP;

            C4Plane convexVolume;
            for (int i = 0; i < chunkSize; i++) {
                convexVolume = new C4Plane();
                convexVolume.setNormal(new Point3D(super.data.getFloat(), super.data.getFloat(), super.data.getFloat()));
                convexVolume.setDistance(super.data.getFloat());
                this.convexVolumePlanesList.add(convexVolume);
            }
        }

        // Init group information.
        WMOGroup wmoGroup;
        for (int i = 0; i < this.nGroups; i++) {
            String wmoGroupPath = FilenameUtils.removeExtension(this.filename) + "_" + formatter.format(i) + ".wmo";
            wmoGroup = new WMOGroup();
            wmoGroup.init(manager, wmoGroupPath);
            this.wmoGroupList.add(wmoGroup);
        }

        init = true;
    }

    private void clear() {
        this.textureNameList.clear();
        this.materials.clear();
        this.groupNameList.clear();
        this.groupInfoList.clear();
        this.portalVertexList.clear();
        this.portalList.clear();
        this.portalRefList.clear();
        this.visibleBlockVerticesList.clear();
        this.visibleBlockList.clear();
        this.lightList.clear();
        this.doodadSetList.clear();
        this.doodadDefList.clear();
        this.doodadNameMap.clear();
        this.fogList.clear();
        this.convexVolumePlanesList.clear();
        this.wmoGroupList.clear();
        clearMesh();        
    }
    
    public boolean doNotAttenuateVertices() {
        return hasFlag(FLAG_DO_NOT_ATTENUATE_VERTICES);
    }

    public boolean useUnifiedRenderPath() {
        return hasFlag(FLAG_USE_UNIFIED_RENDER_PATH);
    }

    public boolean useDBCLiquidID() {
        return hasFlag(FLAG_USE_LIQUID_FROM_DBC);
    }

    public boolean doNotFixVertexColorAlpha() {
        return hasFlag(FLAG_DO_NOT_FIX_VERTEX_COLOR_ALPHA);
    }

    private boolean hasFlag(int flag) {
        return FlagUtils.hasFlag(this.flags, flag);
    }

    public ByteBuffer getData() {
        return data;
    }

    public void setData(ByteBuffer data) {
        super.data = data;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public int getnGroups() {
        return nGroups;
    }

    public void setnGroups(int nGroups) {
        this.nGroups = nGroups;
    }

    public int getnPortals() {
        return nPortals;
    }

    public void setnPortals(int nPortals) {
        this.nPortals = nPortals;
    }

    public int getnLights() {
        return nLights;
    }

    public void setnLights(int nLights) {
        this.nLights = nLights;
    }

    public int getnUnknown() {
        return nModels;
    }

    public void setnUnknown(int nUnknown) {
        this.nModels = nUnknown;
    }

    public int getnXX() {
        return nDoodads;
    }

    public void setnXX(int nXX) {
        this.nDoodads = nXX;
    }

    public int getnXXX() {
        return nDoodaSets;
    }

    public void setnXXX(int nXXX) {
        this.nDoodaSets = nXXX;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public short getNumLod() {
        return numLod;
    }

    public void setNumLod(short numLod) {
        this.numLod = numLod;
    }

    public int getnMaterials() {
        return nMaterials;
    }

    public void setnMaterials(int nMaterials) {
        this.nMaterials = nMaterials;
    }

    public CArgb getAmbColor() {
        return ambColor;
    }

    public void setAmbColor(CArgb ambColor) {
        this.ambColor = ambColor;
    }

    public int getWmoAreaTableID() {
        return wmoAreaTableID;
    }

    public void setWmoAreaTableID(int wmoAreaTableID) {
        this.wmoAreaTableID = wmoAreaTableID;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public List<String> getTextureNameList() {
        return textureNameList;
    }

    public void setTextureNameList(List<String> textureNameList) {
        this.textureNameList = textureNameList;
    }

    public List<WMOMaterials> getMaterials() {
        return materials;
    }

    public void setMaterials(List<WMOMaterials> materials) {
        this.materials = materials;
    }

    public List<String> getGroupNameList() {
        return groupNameList;
    }

    public void setGroupNameList(List<String> groupNameList) {
        this.groupNameList = groupNameList;
    }

    public List<WMOGroupInfo> getGroupInfoList() {
        return groupInfoList;
    }

    public void setGroupInfoList(List<WMOGroupInfo> groupInfoList) {
        this.groupInfoList = groupInfoList;
    }

    public String getSkyBoxName() {
        return skyBoxName;
    }

    public void setSkyBoxName(String skyBoxName) {
        this.skyBoxName = skyBoxName;
    }

    public List<Point3D> getPortalVertexList() {
        return portalVertexList;
    }

    public void setPortalVertexList(List<Point3D> portalVertexList) {
        this.portalVertexList = portalVertexList;
    }

    public List<WMOPortal> getPortalList() {
        return portalList;
    }

    public void setPortalList(List<WMOPortal> portalList) {
        this.portalList = portalList;
    }

    public List<WMOPortalRef> getPortalRefList() {
        return portalRefList;
    }

    public void setPortalRefList(List<WMOPortalRef> portalRefList) {
        this.portalRefList = portalRefList;
    }

    public List<Point3D> getVisibleBlockVerticesList() {
        return visibleBlockVerticesList;
    }

    public void setVisibleBlockVerticesList(List<Point3D> visibleBlockVerticesList) {
        this.visibleBlockVerticesList = visibleBlockVerticesList;
    }

    public List<WMOBlock> getVisibleBlockList() {
        return visibleBlockList;
    }

    public void setVisibleBlockList(List<WMOBlock> visibleBlockList) {
        this.visibleBlockList = visibleBlockList;
    }

    public List<WMOLight> getLightList() {
        return lightList;
    }

    public void setLightList(List<WMOLight> lightList) {
        this.lightList = lightList;
    }

    public List<WMODoodadSet> getDoodadSetList() {
        return doodadSetList;
    }

    public void setDoodadSetList(List<WMODoodadSet> doodadSetList) {
        this.doodadSetList = doodadSetList;
    }

    public int getnModels() {
        return nModels;
    }

    public void setnModels(int nModels) {
        this.nModels = nModels;
    }

    public int getnDoodads() {
        return nDoodads;
    }

    public void setnDoodads(int nDoodads) {
        this.nDoodads = nDoodads;
    }

    public int getnDoodaSets() {
        return nDoodaSets;
    }

    public void setnDoodaSets(int nDoodaSets) {
        this.nDoodaSets = nDoodaSets;
    }

    public Map<Integer, String> getDoodadNameMap() {
        return doodadNameMap;
    }

    public void setDoodadNameMap(Map<Integer, String> doodadNameMap) {
        this.doodadNameMap = doodadNameMap;
    }

    public List<WMODoodadDef> getDoodadDefList() {
        return doodadDefList;
    }

    public void setDoodadDefList(List<WMODoodadDef> doodadDefList) {
        this.doodadDefList = doodadDefList;
    }

    public List<WMOFog> getFogList() {
        return fogList;
    }

    public void setFogList(List<WMOFog> fogList) {
        this.fogList = fogList;
    }

    public List<C4Plane> getConvexVolumePlanesList() {
        return convexVolumePlanesList;
    }

    public void setConvexVolumePlanesList(List<C4Plane> convexVolumePlanesList) {
        this.convexVolumePlanesList = convexVolumePlanesList;
    }

    public List<WMOGroup> getWmoGroupReadersList() {
        return wmoGroupList;
    }

    public void setWmoGroupReadersList(List<WMOGroup> wmoGroupReadersList) {
        this.wmoGroupList = wmoGroupReadersList;
    }

    public boolean isAddModels() {
        return addModels;
    }

    public void setAddModels(boolean addModels) {
        this.addModels = addModels;
    }    

    public boolean isRenderWMOBoundaries() {
        return renderWMOBoundaries;
    }

    public void setRenderWMOBoundaries(boolean renderWMOBoundaries) {
        this.renderWMOBoundaries = renderWMOBoundaries;
    }

    public boolean isRenderGroup() {
        return renderGroup;
    }

    public boolean hasLiquid() {
        for(int i = 0; i < nGroups; i++) {
            if(this.wmoGroupList.get(i).hasLiquid()) {
                return true;
            }
        }
        
        return false;
    }
    
    public void setRenderGroup(boolean renderGroup) {
        this.renderGroup = renderGroup;
    }

    public boolean isRenderNonLiquidGroup() {
        return renderNonLiquidGroup;
    }

    public void setRenderNonLiquidGroup(boolean renderNonLiquidGroup) {
        this.renderNonLiquidGroup = renderNonLiquidGroup;
    }

    public boolean isDisplayGroupNumber() {
        return displayGroupNumber;
    }

    public void setDisplayGroupNumber(boolean displayGroupNumber) {
        this.displayGroupNumber = displayGroupNumber;
    }                     
    
    /**
     * This method return the liquid tile map that can be added to any JavaFX
     * group later on.
     *
     * @param viewportWidth The viewport width size into which this liquid tile
     * map will need to be rendered. Used to translate the position of the Tile
     * Map to the visible area.
     * @param viewportHeight The viewport width size into which this liquid tile
     * map will need to be rendered. Used to translate the position of the Tile
     * Map to the visible area.
     * @param renderType Indicates which type of rendering is requested.
     * @return A Pane containing the liquid tile map.
     */
    private Pane renderLiquidTileMap(Render2DType renderType) {
        Pane pane = new AnchorPane();        
        
        Group liquid = new Group();
        Rectangle rect = new Rectangle();
        double heightRoot = this.boundingBox.getHeight();
        double widthRoot = this.boundingBox.getWidth();
        rect.setX(this.boundingBox.getMinX());
        rect.setY(-this.boundingBox.getMinY() - heightRoot);
        rect.setWidth(widthRoot);
        rect.setHeight(heightRoot);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        
        if (renderWMOBoundaries) {
            liquid.getChildren().add(rect);
        }

        for (WMOGroup group : this.wmoGroupList) {
            StackPane stackPane = new StackPane();
            Rectangle r = new Rectangle();
            double height = group.getGroup().getBoundingBox().getHeight();
            double width = group.getGroup().getBoundingBox().getWidth();

            r.setX(group.getGroup().getBoundingBox().getMinX());
            r.setY(-group.getGroup().getBoundingBox().getMinY() - height);
            r.setWidth(width);
            r.setHeight(height);
            r.setFill(Color.TRANSPARENT);

            if (group.hasLiquid()) {                
                for (int x = 0; x < group.getLiquid().getxTiles(); x++) {
                    for (int y = 0; y < group.getLiquid().getyTiles(); y++) {
                        Rectangle tile = new Rectangle(x * MapUnit.UNIT_SIZE + group.getLiquid().getBaseCoordinates().getX(), -(y * MapUnit.UNIT_SIZE + group.getLiquid().getBaseCoordinates().getY()), MapUnit.UNIT_SIZE, MapUnit.UNIT_SIZE);
                        tile.setFill(Color.TRANSPARENT);

                        boolean render = true;
                        if (group.getLiquid().hasNoLiquid(x, y)) {
                            render = false;
                        } else {
                            tile.setFill(getColorForLiquid(renderType, group, x, y));
                        }
                        if (render) {
                            liquid.getChildren().add(tile);
                        }
                    }
                }
            }

            String[] temp = FilenameUtils.getName(group.getFilename()).split("\\.")[0].split("_");
            Text label = new Text(temp[temp.length - 1]);

            if (group.hasLiquid()) {
                r.setStroke(Color.BLUE);
                label.setStroke(Color.RED);
            } else {
                r.setStroke(Color.BLACK);
            }
            stackPane.setLayoutX(r.getX());
            stackPane.setLayoutY(r.getY());
            if ((renderGroup && group.hasLiquid()) || (renderNonLiquidGroup && !group.hasLiquid())) {
                stackPane.getChildren().add(r);
                if (displayGroupNumber) {
                    stackPane.getChildren().add(label);
                    StackPane.setAlignment(label, Pos.TOP_LEFT);
                }
            }
            pane.getChildren().add(stackPane);            
        }        
        pane.getChildren().add(liquid);

        return pane;
    }

    private Color getColorForLiquid(Render2DType renderType, WMOGroup group, int x, int y) {
        switch (renderType) {
            case RENDER_TILEMAP_LIQUID_TYPE:
                if (group.getLiquid().isOverlap(x, y)) {
                    return Color.YELLOW;
                } else if (group.getLiquid().isWater(x, y)) {
                    return Color.BLUE;
                } else if (group.getLiquid().isOcean(x, y)) {
                    return Color.PURPLE;
                } else if (group.getLiquid().isMagma(x, y)) {
                    return Color.ORANGE;
                } else if (group.getLiquid().isSlime(x, y)) {
                    return Color.GREEN;
                }
                break;
            case RENDER_TILEMAP_LIQUID_FISHABLE:
                if (group.getLiquid().isFishable(x, y)) {
                    return Color.GREEN;
                } else {
                    return Color.RED;
                }
            case RENDER_TILEMAP_LIQUID_ANIMATED:
                if (group.getLiquid().isAnimated(x, y)) {
                    return Color.GREEN;
                } else {
                    return Color.RED;
                }
        }
        return Color.BLACK;
    }

    @Override
    public Pane render2D(Render2DType renderType) throws ModelRendererException {
        switch(renderType) {
            case RENDER_TILEMAP_LIQUID_TYPE:
            case RENDER_TILEMAP_LIQUID_FISHABLE:
            case RENDER_TILEMAP_LIQUID_ANIMATED:
                return renderLiquidTileMap(renderType);                                        
            case RENDER_TILEMAP_LIQUID_HEIGHTMAP:
            case RENDER_TILEMAP_TERRAIN_HEIGHTMAP:
            default:
                throw new UnsupportedOperationException("This render type is not yet supported");
        }        
    }

    @Override
    public PolygonMesh render3D(Render3DType type, Map<String, M2> cache) throws ModelRendererException, MPQException {
        switch(type) {            
            case LIQUID:
                return renderLiquid();
            case MODEL:
            case COLLISION_MODEL:
                return renderModel(type, cache);            
            default:
                throw new UnsupportedOperationException("The type method is not supported on this object.");
        }
    }
    
    /**
     * This method will provide a PolygonMesh containing the rendered Liquid. This mesh can be used to display with JavaFX.
     * @return A PolygonMesh type of object being the 3D representation of the liquid present in this WMO.
     */
    private PolygonMesh renderLiquid() throws ModelRendererException {
        if (this.init == false) {
            logger.error("This WMO has not been initialized !");
            throw new ModelRendererException("This WMO has not been initialized !");
        }
        
        clearLiquidMesh();
        
        int offsetVertices = 0;                
        
        for (int i = 0; i < this.nGroups; i++) {            
            WMOGroup wmoGroup = this.wmoGroupList.get(i);            
            
            if (wmoGroup.hasLiquid()) {
                // Wmo group file has liquid information.
                
                int[][] faces = new int[wmoGroup.getLiquidIndicesList().size()/4][8];                
                for (int face = 0, idx = 0; face < wmoGroup.getLiquidIndicesList().size(); face += 4, idx++) {                    
                    faces[idx][0] = wmoGroup.getLiquidIndicesList().get(face) + offsetVertices;                    
                    faces[idx][1] = wmoGroup.getLiquidIndicesList().get(face) + offsetVertices;                                        
                    faces[idx][2] = wmoGroup.getLiquidIndicesList().get(face + 2) + offsetVertices;
                    faces[idx][3] = wmoGroup.getLiquidIndicesList().get(face + 2) + offsetVertices;                    
                    faces[idx][4] = wmoGroup.getLiquidIndicesList().get(face + 3) + offsetVertices;                    
                    faces[idx][5] = wmoGroup.getLiquidIndicesList().get(face + 3) + offsetVertices;                
                    faces[idx][6] = wmoGroup.getLiquidIndicesList().get(face + 1) + offsetVertices;
                    faces[idx][7] = wmoGroup.getLiquidIndicesList().get(face + 1) + offsetVertices;                    
                    liquidMesh.getFaceSmoothingGroups().addAll(0);                    
                }                        
                liquidMesh.faces = ArrayUtils.addAll(liquidMesh.faces, faces);                                                
                
                int idx = 0;
                for (Point3D v : wmoGroup.getLiquidVerticesList()) {
                    liquidMesh.getPoints().addAll((float) v.getY(), (float) v.getZ(), (float) v.getX());                    
                    liquidMesh.getTexCoords().addAll((float) wmoGroup.getTextureVertexList().get(idx).getX(), (float) wmoGroup.getTextureVertexList().get(idx).getY());
                    idx++;
                    offsetVertices++;
                }

            }
        }
        
        return this.liquidMesh;
    }
    
    private PolygonMesh renderModel(Render3DType type, Map<String, M2> cache) throws ModelRendererException, MPQException {
        if (this.init == false) {
            logger.error("This WMO has not been initialized !");
            throw new ModelRendererException("This WMO has not been initialized !");
        }        
        
        if(cache == null) {
            logger.info("Cache entry is null, creating a temporary empty cache.");
            cache = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        }
        
        clearShapeMesh();

        int offsetVertices = 0;

        for (int i = 0; i < this.nGroups; i++) {

            WMOGroup wmoGroup = this.wmoGroupList.get(i);
            if (!wmoGroup.getVertexList().isEmpty()) {
                // Wmo group file has vertices.

                // Adding the faces counter clock-wise.
                int[][] faces = new int[wmoGroup.getIndexList().size()/3][6];                       
                for (int face = 0, idx = 0; face < wmoGroup.getIndexList().size(); face += 3, idx++) {                    
                    faces[idx][0] = wmoGroup.getIndexList().get(face) + offsetVertices;                    
                    faces[idx][1] = wmoGroup.getIndexList().get(face) + offsetVertices;                                                            
                    faces[idx][2] = wmoGroup.getIndexList().get(face + 1) + offsetVertices;                    
                    faces[idx][3] = wmoGroup.getIndexList().get(face + 1) + offsetVertices;                                    
                    faces[idx][4] = wmoGroup.getIndexList().get(face + 2) + offsetVertices;
                    faces[idx][5] = wmoGroup.getIndexList().get(face + 2) + offsetVertices;                    
                    shapeMesh.getFaceSmoothingGroups().addAll(0);                    
                }                        
                shapeMesh.faces = ArrayUtils.addAll(shapeMesh.faces, faces);                

                int idx = 0;
                for (Point3D v : wmoGroup.getVertexList()) {
                    shapeMesh.getPoints().addAll((float) v.getY(), (float) v.getZ(), (float) v.getX());
                    shapeMesh.getNormals().addAll((float) wmoGroup.getNormalList().get(idx).getY(), (float) wmoGroup.getNormalList().get(idx).getZ(), (float) wmoGroup.getNormalList().get(idx).getX());
                    shapeMesh.getTexCoords().addAll((float) wmoGroup.getTextureVertexList().get(idx).getX(), (float) wmoGroup.getTextureVertexList().get(idx).getY());
                    idx++;
                    offsetVertices++;
                }

            }
        }

        if (addModels) {
            M2 model;            
            // Now we add models.
            for (WMODoodadDef modelInstance : this.doodadDefList) {

                if (this.doodadNameMap.containsKey(modelInstance.getNameIndex())) {
                    // MDX model files are stored as M2 in the MPQ. God knows why.
                    String modelFile = FilenameUtils.removeExtension(this.doodadNameMap.get(modelInstance.getNameIndex())) + ".M2";
                    if (!manager.getMPQForFile(modelFile).hasFile(modelFile)) {
                        logger.warn("Oooops, m2Editor doesn't have the file: " + modelFile);
                        continue;
                    }

                    try {
                        // First, check if the M2 is in cache. Must be much faster than parsing it again and again.
                        if (cache.containsKey(modelFile)) {
                            model = cache.get(modelFile);
                            model.render3D(type, null);
                        } else {
                            model = new M2();                            
                            model.init(manager, modelFile);
                            model.render3D(type, null);
                            cache.put(modelFile, model);
                        }

                        if (model.getShapeMesh().faces == null) {
                            // Empty collision mesh. It can happen.
                            continue;
                        }
                        
                        // Now, we have the vertices of this M2, we need to scale, rotate & position.                                                                                
                        // First, we create a view to apply these transformations.
                        Affine affine = new Affine();

                        // We translate the object location.                        
                        Translate translate = new Translate(modelInstance.getPosition().getX(), modelInstance.getPosition().getY(), modelInstance.getPosition().getZ());                        
                        
                        // We convert the quaternion to a Rotate object with angle (in degrees) & pivot point.
                        Rotate rotate = getAngleAndAxis(modelInstance.getOrientation());

                        // We scale.
                        Scale scale = new Scale(modelInstance.getScale(), modelInstance.getScale(), modelInstance.getScale());

                        // We add all transformations to the view and we get back the transformation matrix.
                        affine.append(translate);                    
                        affine.append(rotate);                        
                        affine.append(scale);  

                        // We apply the transformation matrix to all points of the mesh.
                        PolygonMesh temp = new PolygonMesh();
                        for (int i = 0; i < model.getShapeMesh().getPoints().size(); i += 3) {
                            Point3D point = new Point3D(model.getShapeMesh().getPoints().get(i), model.getShapeMesh().getPoints().get(i + 1), model.getShapeMesh().getPoints().get(i + 2));
                            point = affine.transform(point);
                            temp.getPoints().addAll((float) point.getY(), (float) point.getZ(), (float) point.getX());
                        }

                        for (int i = 0; i < model.getShapeMesh().getNormals().size(); i += 3) {
                            Point3D normal = new Point3D(model.getShapeMesh().getNormals().get(i), model.getShapeMesh().getNormals().get(i + 1), model.getShapeMesh().getNormals().get(i + 2));
                            normal = affine.transform(normal);
                            temp.getNormals().addAll((float) normal.getY(), (float) normal.getZ(), (float) normal.getX());
                        }
                        
                        int offset = shapeMesh.getPoints().size() / 3;

                        // Then, we add the converted model mesh to the WMO mesh.
                        this.shapeMesh.getPoints().addAll(temp.getPoints());
                        this.shapeMesh.getNormals().addAll(temp.getNormals());
                        this.shapeMesh.getTexCoords().addAll(model.getShapeMesh().getTexCoords());
                        this.shapeMesh.getFaceSmoothingGroups().addAll(model.getShapeMesh().getFaceSmoothingGroups());

                        // And we recalculate the faces of the model mesh.
                        int[][] faces = new int[model.getShapeMesh().faces.length][6];
                        for (int i = 0; i < model.getShapeMesh().faces.length; i++) {
                            for (int j = 0; j < model.getShapeMesh().faces[i].length; j++) {
                                faces[i][j] = model.getShapeMesh().faces[i][j] + offset;
                            }
                        }                        
                        
                        shapeMesh.faces = ArrayUtils.addAll(shapeMesh.faces, faces);                          
                    } catch (IOException ex) {
                        logger.error("Error while adding a new model to this WMO: " + modelFile);
                    } catch (FileReaderException ex) {
                        logger.error("Error while adding a new model to this WMO: " + modelFile);
                    }
                } else {
                    logger.warn(filename + " - Oooops, offset for MDX is not found! " + modelInstance.getNameIndex());
                }
            }
        }
        
        return shapeMesh;
    }
}
