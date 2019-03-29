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
package eu.jangos.extractor.rendering;

import eu.jangos.extractor.rendering.symbolic.SymbolicPolygonMesh;
import eu.jangos.extractor.rendering.symbolic.SymbolicSubdivisionBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Catmull Clark subdivision surface polygon mesh
 */
public class SubdivisionMesh extends PolygonMesh {
    private final PolygonMesh originalMesh;
    private int subdivisionLevel;
    private BoundaryMode boundaryMode;
    private MapBorderMode mapBorderMode;
    private final List<SymbolicPolygonMesh> symbolicMeshes;

    private boolean pointValuesDirty;
    private boolean meshDirty;
    private boolean subdivisionLevelDirty;

    /**
     * Describes whether the edges and points at the boundary are treated as creases
     */
    public enum BoundaryMode {
        /**
         * Only edges at the boundary are treated as creases
         */
        CREASE_EDGES,
        /**
         * Edges and points at the boundary are treated as creases
         */
        CREASE_ALL
    }

    /**
     * Describes how the new texture coordinate for the control point is defined
     */
    public enum MapBorderMode {
        /**
         * Jeeps the same uvs for all control points
         */
        NOT_SMOOTH,
        /**
         * Smooths uvs of points at corners
         */
        SMOOTH_INTERNAL,
        /**
         * Smooths uvs of points at boundaries and original control points (and creases [in the future when creases are defined])
         */
        SMOOTH_ALL
    }

    public SubdivisionMesh(PolygonMesh originalMesh, int subdivisionLevel, BoundaryMode boundaryMode, MapBorderMode mapBorderMode) {
        this.originalMesh = originalMesh;
        setSubdivisionLevelForced(subdivisionLevel);
        setBoundaryModeForced(boundaryMode);
        setMapBorderModeForced(mapBorderMode);

        symbolicMeshes = new ArrayList<>(4); // the polymesh is usually subdivided up to 3 times

        originalMesh.getPoints().addListener((observableArray, sizeChanged, from, to) -> {
            if (sizeChanged) {
                meshDirty = true;
            } else {
                pointValuesDirty = true;
            }
        });
        originalMesh.getTexCoords().addListener((observableArray, sizeChanged, from, to) -> meshDirty = true);
    }

    /**
     * Updates the variables of the underlying polygon mesh.
     * It only updates the fields that need to be updated.
     */
    public void update() {
        if (meshDirty) {
            symbolicMeshes.clear();
            symbolicMeshes.add(new SymbolicPolygonMesh(originalMesh));
            pointValuesDirty = true;
            subdivisionLevelDirty = true;
        }

        while (subdivisionLevel >= symbolicMeshes.size()) {
            symbolicMeshes.add(SymbolicSubdivisionBuilder.subdivide(symbolicMeshes.get(symbolicMeshes.size()-1), boundaryMode, mapBorderMode));
            pointValuesDirty = true;
            subdivisionLevelDirty = true;
        }

        if (pointValuesDirty) {
            for (int i = 0; i <= subdivisionLevel; i++) {
                SymbolicPolygonMesh symbolicMesh = symbolicMeshes.get(i);
                symbolicMesh.points.update();
            }
        }

        if (pointValuesDirty || subdivisionLevelDirty) {
            getPoints().setAll(symbolicMeshes.get(subdivisionLevel).points.data);
        }

        if (subdivisionLevelDirty) {
            faces = symbolicMeshes.get(subdivisionLevel).faces;
            numEdgesInFaces = -1;
            getFaceSmoothingGroups().setAll(symbolicMeshes.get(subdivisionLevel).faceSmoothingGroups);
            getTexCoords().setAll(symbolicMeshes.get(subdivisionLevel).texCoords);
        }

        meshDirty = false;
        pointValuesDirty = false;
        subdivisionLevelDirty = false;
    }

    private void setSubdivisionLevelForced(int subdivisionLevel) {
        this.subdivisionLevel = subdivisionLevel;
        subdivisionLevelDirty = true;
    }

    private void setBoundaryModeForced(SubdivisionMesh.BoundaryMode boundaryMode) {
        this.boundaryMode = boundaryMode;
        meshDirty = true;
    }

    private void setMapBorderModeForced(SubdivisionMesh.MapBorderMode mapBorderMode) {
        this.mapBorderMode = mapBorderMode;
        meshDirty = true;
    }

    public PolygonMesh getOriginalMesh() {
        return originalMesh;
    }

    public int getSubdivisionLevel() {
        return subdivisionLevel;
    }

    public void setSubdivisionLevel(int subdivisionLevel) {
        if (subdivisionLevel != this.subdivisionLevel) {
            setSubdivisionLevelForced(subdivisionLevel);
        }
    }

    public SubdivisionMesh.BoundaryMode getBoundaryMode() {
        return boundaryMode;
    }

    public void setBoundaryMode(SubdivisionMesh.BoundaryMode boundaryMode) {
        if (boundaryMode != this.boundaryMode) {
            setBoundaryModeForced(boundaryMode);
        }
    }

    public SubdivisionMesh.MapBorderMode getMapBorderMode() {
        return mapBorderMode;
    }

    public void setMapBorderMode(SubdivisionMesh.MapBorderMode mapBorderMode) {
        if (mapBorderMode != this.mapBorderMode) {
            setMapBorderModeForced(mapBorderMode);
        }
    }
}
