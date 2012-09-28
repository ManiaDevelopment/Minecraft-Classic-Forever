package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.level.tile.FlowerBlock;
import java.util.Random;

public final class MushroomBlock extends FlowerBlock {

   protected MushroomBlock(int var1, int var2) {
      super(var1, var2);
      float var3 = 0.2F;
      this.setBounds(0.5F - var3, 0.0F, 0.5F - var3, var3 + 0.5F, var3 * 2.0F, var3 + 0.5F);
   }

   public final void update(Level level, int x, int y, int z, Random rand) {
      int var6 = level.getTile(x, y - 1, z);
      if(level.isLit(x, y, z) || var6 != Block.STONE.id && var6 != Block.GRAVEL.id && var6 != Block.COBBLESTONE.id) {
         level.setTile(x, y, z, 0);
      }

   }
}
