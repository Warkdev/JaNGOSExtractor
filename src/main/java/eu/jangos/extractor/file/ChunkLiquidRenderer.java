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

import static eu.jangos.extractor.file.ChunkLiquidRenderer.LIQUID_FLAG_LENGTH;
import eu.jangos.extractor.rendering.Render2DType;
import eu.jangos.utils.FlagUtils;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * ChunkLiquid is a parent class for liquid chunks in ADT/WMO.
 * @author Warkdev
 */
public abstract class ChunkLiquidRenderer extends ModelRenderer {   

    public static final int LIQUID_DATA_LENGTH = 9;
    public static final int LIQUID_FLAG_LENGTH = 8;

    private static final int MASK_LIQUID = 0x03;
    private static final int FLAG_IS_WATER = 0x00;
    private static final int FLAG_IS_OCEAN = 0x01;
    private static final int FLAG_IS_MAGMA = 0x02;
    private static final int FLAG_IS_SLIME = 0x03;
    private static final int FLAG_IS_ANIMATED = 0x04;
    private static final int FLAG_E = 0x08;
    private static final int MASK_NO_LIQUID = 0x0F;
    private static final int FLAG_C = 0x10;
    private static final int FLAG_D = 0x20;
    private static final int FLAG_FISHABLE = 0x40;
    private static final int FLAG_DARK = 0x80;
    
    // Color settings.
    public static Color COLOR_NONE = Color.BLACK;
    
    public static Color COLOR_DARK = Color.YELLOW;
    
    public static Color COLOR_RIVER = Color.BLUE;
    public static Color COLOR_OCEAN = Color.PURPLE;
    public static Color COLOR_MAGMA = Color.ORANGE;
    public static Color COLOR_SLIME = Color.GREEN;
    
    public static Color COLOR_NOT = Color.RED;
    public static Color COLOR_ANIMATED = Color.GREEN;
    public static Color COLOR_FISHABLE = Color.GREEN;  
    
    protected List<Short> flags;    
    
    protected Pane renderLiquidTileMap(Render2DType renderType) {
        Pane pane = new Pane();        
        Canvas canvas = new Canvas(LIQUID_FLAG_LENGTH, LIQUID_FLAG_LENGTH);                
        PixelWriter pixel = canvas.getGraphicsContext2D().getPixelWriter();        
        pane.getChildren().add(canvas);
        
        for (int x = 0; x < LIQUID_FLAG_LENGTH; x++) {
            for (int y = 0; y < LIQUID_FLAG_LENGTH; y++) {
                pixel.setColor(x, y, getColorForLiquid(renderType, x, y));
            }
        }
        
        return pane;
    }
    
    public Color getColorForLiquid(Render2DType renderType, int x, int y) {        
        if(hasNoLiquid(x, y)) {
            return COLOR_NONE;
        }
        
        switch (renderType) {
            case RENDER_TILEMAP_LIQUID_TYPE:
                if (isDark(x, y)) {
                    return COLOR_DARK;
                } else if (isRiver(x, y)) {
                    return COLOR_RIVER;
                } else if (isOcean(x, y)) {
                    return COLOR_OCEAN;
                } else if (isMagma(x, y)) {
                    return COLOR_MAGMA;
                } else if (isSlime(x, y)) {
                    return COLOR_SLIME;
                }
                break;
            case RENDER_TILEMAP_LIQUID_FISHABLE:
                if (isFishable(x, y)) {
                    return COLOR_FISHABLE;
                } else {
                    return COLOR_NOT;
                }
            case RENDER_TILEMAP_LIQUID_ANIMATED:
                if (isAnimated(x, y)) {
                    return COLOR_ANIMATED;
                } else {
                    return COLOR_NOT;
                }            
        }      
        
        return COLOR_NONE;
    }
    
    public boolean hasNoLiquid(int row, int col) {
        return hasFlag(row, col, MASK_NO_LIQUID);
    }

    public boolean isRiver(int row, int col) {
        return !hasNoLiquid(row, col) && (this.flags.get(getFlagPosition(row, col)) & MASK_LIQUID) == FLAG_IS_WATER;
    }

    public boolean isOcean(int row, int col) {
        return !hasNoLiquid(row, col) && (this.flags.get(getFlagPosition(row, col)) & MASK_LIQUID) == FLAG_IS_OCEAN;
    }

    public boolean isMagma(int row, int col) {
        return !hasNoLiquid(row, col) && (this.flags.get(getFlagPosition(row, col)) & MASK_LIQUID) == FLAG_IS_MAGMA;
    }

    public boolean isSlime(int row, int col) {
        return !hasNoLiquid(row, col) && (this.flags.get(getFlagPosition(row, col)) & MASK_LIQUID) == FLAG_IS_SLIME;
    }

    public boolean isFishable(int row, int col) {
        return hasFlag(row, col, FLAG_FISHABLE);
    }

    public boolean isFlagC(int row, int col) {
        return hasFlag(row, col, FLAG_C);
    }

    public boolean isFlagD(int row, int col) {
        return hasFlag(row, col, FLAG_D);
    }

    public boolean isFlagE(int row, int col) {
        return hasFlag(row, col, FLAG_E);
    }

    public boolean isAnimated(int row, int col) {
        return hasFlag(row, col, FLAG_IS_ANIMATED);
    }

    public boolean isDark(int row, int col) {
        return hasFlag(row, col, FLAG_DARK);
    }

    private boolean hasFlag(int row, int col, int flag) {
        return FlagUtils.hasFlag(this.flags.get(getFlagPosition(row, col)), flag);
    }
    
    private int getFlagPosition(int row, int col) {
        return row * LIQUID_FLAG_LENGTH + col;
    }

    private int getDataPosition(int row, int col) {
        return row * LIQUID_DATA_LENGTH + col;
    }
    
    public static Color getCOLOR_NONE() {
        return COLOR_NONE;
    }

    public static void setCOLOR_NONE(Color COLOR_NONE) {
        ChunkLiquidRenderer.COLOR_NONE = COLOR_NONE;
    }

    public static Color getCOLOR_DARK() {
        return COLOR_DARK;
    }

    public static void setCOLOR_OVERLAP(Color COLOR_DARK) {
        ChunkLiquidRenderer.COLOR_DARK = COLOR_DARK;
    }

    public static Color getCOLOR_RIVER() {
        return COLOR_RIVER;
    }

    public static void setCOLOR_RIVER(Color COLOR_RIVER) {
        ChunkLiquidRenderer.COLOR_RIVER = COLOR_RIVER;
    }

    public static Color getCOLOR_OCEAN() {
        return COLOR_OCEAN;
    }

    public static void setCOLOR_OCEAN(Color COLOR_OCEAN) {
        ChunkLiquidRenderer.COLOR_OCEAN = COLOR_OCEAN;
    }

    public static Color getCOLOR_MAGMA() {
        return COLOR_MAGMA;
    }

    public static void setCOLOR_MAGMA(Color COLOR_MAGMA) {
        ChunkLiquidRenderer.COLOR_MAGMA = COLOR_MAGMA;
    }

    public static Color getCOLOR_SLIME() {
        return COLOR_SLIME;
    }

    public static void setCOLOR_SLIME(Color COLOR_SLIME) {
        ChunkLiquidRenderer.COLOR_SLIME = COLOR_SLIME;
    }

    public static Color getCOLOR_NOT() {
        return COLOR_NOT;
    }

    public static void setCOLOR_NOT(Color COLOR_NOT) {
        ChunkLiquidRenderer.COLOR_NOT = COLOR_NOT;
    }

    public static Color getCOLOR_ANIMATED() {
        return COLOR_ANIMATED;
    }

    public static void setCOLOR_ANIMATED(Color COLOR_ANIMATED) {
        ChunkLiquidRenderer.COLOR_ANIMATED = COLOR_ANIMATED;
    }

    public static Color getCOLOR_FISHABLE() {
        return COLOR_FISHABLE;
    }

    public static void setCOLOR_FISHABLE(Color COLOR_FISHABLE) {
        ChunkLiquidRenderer.COLOR_FISHABLE = COLOR_FISHABLE;
    }
    
        
}
