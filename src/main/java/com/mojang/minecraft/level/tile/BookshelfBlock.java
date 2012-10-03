package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.tile.Block;

public final class BookshelfBlock extends Block {

   public BookshelfBlock(int var1, int var2) {
      super(47, 35);
   }

   protected final int getTextureId(int texture) {
      return texture <= 1?4:this.textureId;
   }

   public final int getDropCount() {
      return 0;
   }
}
