package com.mojang.minecraft.level.tile;

public final class StoneBlock extends Block {

   public StoneBlock(int var1, int var2) {
      super(var1, var2);
   }

   public final int getDrop() {
      return COBBLESTONE.id;
   }
}
