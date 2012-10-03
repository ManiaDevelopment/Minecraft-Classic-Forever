package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;
import java.util.Random;

public final class GrassBlock extends Block {

   protected GrassBlock(int var1) {
      super(2);
      this.textureId = 3;
      this.setPhysics(true);
   }

   protected final int getTextureId(int texture) {
      return texture == 1?0:(texture == 0?2:3);
   }

   public final void update(Level level, int x, int y, int z, Random rand) {
      if(rand.nextInt(4) == 0) {
         if(!level.isLit(x, y, z)) {
            level.setTile(x, y, z, Block.DIRT.id);
         } else {
            for(int var9 = 0; var9 < 4; ++var9) {
               int var6 = x + rand.nextInt(3) - 1;
               int var7 = y + rand.nextInt(5) - 3;
               int var8 = z + rand.nextInt(3) - 1;
               if(level.getTile(var6, var7, var8) == Block.DIRT.id && level.isLit(var6, var7, var8)) {
                  level.setTile(var6, var7, var8, Block.GRASS.id);
               }
            }

         }
      }
   }

   public final int getDrop() {
      return Block.DIRT.getDrop();
   }
}
