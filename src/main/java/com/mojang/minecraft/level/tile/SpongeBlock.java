package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;

public final class SpongeBlock extends Block {

   protected SpongeBlock(int var1) {
      super(19);
      this.textureId = 48;
   }

   public final void onAdded(Level level, int x, int y, int z) {
      for(int var7 = x - 2; var7 <= x + 2; ++var7) {
         for(int var5 = y - 2; var5 <= y + 2; ++var5) {
            for(int var6 = z - 2; var6 <= z + 2; ++var6) {
               if(level.isWater(var7, var5, var6)) {
                  level.setTileNoNeighborChange(var7, var5, var6, 0);
               }
            }
         }
      }

   }

   public final void onRemoved(Level var1, int var2, int var3, int var4) {
      for(int var7 = var2 - 2; var7 <= var2 + 2; ++var7) {
         for(int var5 = var3 - 2; var5 <= var3 + 2; ++var5) {
            for(int var6 = var4 - 2; var6 <= var4 + 2; ++var6) {
               var1.updateNeighborsAt(var7, var5, var6, var1.getTile(var7, var5, var6));
            }
         }
      }

   }
}
