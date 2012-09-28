package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;

public final class SlabBlock extends Block {

   private boolean doubleSlab;


   public SlabBlock(int var1, boolean var2) {
      super(var1, 6);
      this.doubleSlab = var2;
      if(!var2) {
         this.setBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

   }

   protected final int getTextureId(int texture) {
      return texture <= 1?6:5;
   }

   public final boolean isSolid() {
      return this.doubleSlab;
   }

   public final void onNeighborChange(Level var1, int var2, int var3, int var4, int var5) {
      if(this == Block.SLAB) {
         ;
      }
   }

   public final void onAdded(Level level, int x, int y, int z) {
      if(this != Block.SLAB) {
         super.onAdded(level, x, y, z);
      }

      if(level.getTile(x, y - 1, z) == SLAB.id) {
         level.setTile(x, y, z, 0);
         level.setTile(x, y - 1, z, Block.DOUBLE_SLAB.id);
      }

   }

   public final int getDrop() {
      return Block.SLAB.id;
   }

   public final boolean isCube() {
      return this.doubleSlab;
   }

   public final boolean canRenderSide(Level level, int x, int y, int z, int side) {
      if(this != Block.SLAB) {
         super.canRenderSide(level, x, y, z, side);
      }

      return side == 1?true:(!super.canRenderSide(level, x, y, z, side)?false:(side == 0?true: level.getTile(x, y, z) != this.id));
   }
}
