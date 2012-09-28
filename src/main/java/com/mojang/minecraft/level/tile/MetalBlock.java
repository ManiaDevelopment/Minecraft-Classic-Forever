package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.tile.Block;

public final class MetalBlock extends Block {

   public MetalBlock(int var1, int var2) {
      super(var1);
      this.textureId = var2;
   }

   protected final int getTextureId(int texture) {
      return texture == 1?this.textureId - 16:(texture == 0?this.textureId + 16:this.textureId);
   }
}
