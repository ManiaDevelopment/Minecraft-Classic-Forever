package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;

public class LeavesBaseBlock extends Block {

   private boolean showNeighborSides = true;


   protected LeavesBaseBlock(int var1, int var2, boolean var3) {
      super(var1, var2);
   }

   public final boolean isSolid() {
      return false;
   }

   public final boolean canRenderSide(Level var1, int var2, int var3, int var4, int var5) {
      int var6 = var1.getTile(var2, var3, var4);
      return !this.showNeighborSides && var6 == this.id?false:super.canRenderSide(var1, var2, var3, var4, var5);
   }

   public final boolean isOpaque() {
      return false;
   }
}
