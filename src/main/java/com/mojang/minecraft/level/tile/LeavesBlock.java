package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.level.tile.LeavesBaseBlock;

public final class LeavesBlock extends LeavesBaseBlock {

   protected LeavesBlock(int var1, int var2) {
      super(18, 22, true);
   }

   public final int getDropCount() {
      return random.nextInt(10) == 0?1:0;
   }

   public final int getDrop() {
      return Block.SAPLING.id;
   }
}
