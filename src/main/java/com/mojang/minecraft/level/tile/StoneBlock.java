package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.tile.Block;

public final class StoneBlock extends Block {

   public StoneBlock(int var1, int var2) {
      super(var1, var2);
   }

   public final int getDrop() {
      return Block.COBBLESTONE.id;
   }
}
