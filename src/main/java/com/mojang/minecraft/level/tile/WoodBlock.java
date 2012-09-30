package com.mojang.minecraft.level.tile;

public final class WoodBlock extends Block {

   protected WoodBlock(int var1) {
      super(17);
      this.textureId = 20;
   }

   public final int getDropCount() {
      return random.nextInt(3) + 3;
   }

   public final int getDrop() {
      return WOOD.id;
   }

   protected final int getTextureId(int texture) {
      return texture == 1?21:(texture == 0?21:20);
   }
}
