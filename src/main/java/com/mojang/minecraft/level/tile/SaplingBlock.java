package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.level.tile.FlowerBlock;
import java.util.Random;

public final class SaplingBlock extends FlowerBlock {

   protected SaplingBlock(int var1, int var2) {
      super(6, 15);
      float var3 = 0.4F;
      this.setBounds(0.5F - var3, 0.0F, 0.5F - var3, var3 + 0.5F, var3 * 2.0F, var3 + 0.5F);
   }

   public final void update(Level var1, int var2, int var3, int var4, Random var5) {
      int var6 = var1.getTile(var2, var3 - 1, var4);
      if(var1.isLit(var2, var3, var4) && (var6 == Block.DIRT.id || var6 == Block.GRASS.id)) {
         if(var5.nextInt(5) == 0) {
            var1.setTileNoUpdate(var2, var3, var4, 0);
            if(!var1.maybeGrowTree(var2, var3, var4)) {
               var1.setTileNoUpdate(var2, var3, var4, this.id);
            }
         }

      } else {
         var1.setTile(var2, var3, var4, 0);
      }
   }
}
