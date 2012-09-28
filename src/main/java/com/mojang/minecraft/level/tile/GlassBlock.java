package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;

public final class GlassBlock extends Block {

   private boolean showNeighborSides = false;


   protected GlassBlock(int var1, int var2, boolean var3) {
      super(20, 49);
   }

   public final boolean isSolid() {
      return false;
   }

   public final boolean canRenderSide(Level level, int x, int y, int z, int side) {
      int var6 = level.getTile(x, y, z);
      return !this.showNeighborSides && var6 == this.id?false:super.canRenderSide(level, x, y, z, side);
   }

   public final boolean isOpaque() {
      return false;
   }
}
