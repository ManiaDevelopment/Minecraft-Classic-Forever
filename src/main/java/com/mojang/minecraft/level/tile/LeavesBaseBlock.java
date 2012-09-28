package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;

public class LeavesBaseBlock extends Block {

   private boolean showNeighborSides = true;


   protected LeavesBaseBlock(int var1, int var2, boolean var3) {
      super(var1, var2);
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
