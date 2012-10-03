package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.tile.Block;

public final class WoodBlock extends Block {

   protected WoodBlock(int var1) {
      super(17);
      this.textureId = 20;
   }

   public final int getDropCount() {
      return random.nextInt(3) + 3;
   }

   public final int getDrop() {
      return Block.WOOD.id;
   }

   protected final int getTextureId(int texture) {
      return texture == 1?21:(texture == 0?21:20);
   }
}
