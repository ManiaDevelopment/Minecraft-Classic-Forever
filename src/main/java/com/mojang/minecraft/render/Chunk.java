package com.mojang.minecraft.render;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.player.Player;
import com.mojang.minecraft.render.Frustrum;
import com.mojang.minecraft.render.ShapeRenderer;
import com.mojang.util.MathHelper;
import org.lwjgl.opengl.GL11;

public final class Chunk {

   private Level level;
   private int baseListId = -1;
   private static ShapeRenderer renderer = ShapeRenderer.instance;
   public static int chunkUpdates = 0;
   private int x;
   private int y;
   private int z;
   private int width;
   private int height;
   private int depth;
   public boolean visible = false;
   private boolean[] dirty = new boolean[2];
   public boolean loaded;


   public Chunk(Level var1, int var2, int var3, int var4, int var5, int var6) {
      this.level = var1;
      this.x = var2;
      this.y = var3;
      this.z = var4;
      this.width = this.height = this.depth = 16;
      MathHelper.sqrt((float)(this.width * this.width + this.height * this.height + this.depth * this.depth));
      this.baseListId = var6;
      this.setAllDirty();
   }

   public final void update() {
      ++chunkUpdates;
      int var1 = this.x;
      int var2 = this.y;
      int var3 = this.z;
      int var4 = this.x + this.width;
      int var5 = this.y + this.height;
      int var6 = this.z + this.depth;

      int var7;
      for(var7 = 0; var7 < 2; ++var7) {
         this.dirty[var7] = true;
      }

      for(var7 = 0; var7 < 2; ++var7) {
         boolean var8 = false;
         boolean var9 = false;
         GL11.glNewList(this.baseListId + var7, 4864);
         renderer.begin();

         for(int var10 = var1; var10 < var4; ++var10) {
            for(int var11 = var2; var11 < var5; ++var11) {
               for(int var12 = var3; var12 < var6; ++var12) {
                  int var13;
                  if((var13 = this.level.getTile(var10, var11, var12)) > 0) {
                     Block var14;
                     if((var14 = Block.blocks[var13]).getRenderPass() != var7) {
                        var8 = true;
                     } else {
                        var9 |= var14.render(this.level, var10, var11, var12, renderer);
                     }
                  }
               }
            }
         }

         renderer.end();
         GL11.glEndList();
         if(var9) {
            this.dirty[var7] = false;
         }

         if(!var8) {
            break;
         }
      }

   }

   public final float distanceSquared(Player var1) {
      float var2 = var1.x - (float)this.x;
      float var3 = var1.y - (float)this.y;
      float var4 = var1.z - (float)this.z;
      return var2 * var2 + var3 * var3 + var4 * var4;
   }

   private void setAllDirty() {
      for(int var1 = 0; var1 < 2; ++var1) {
         this.dirty[var1] = true;
      }

   }

   public final void dispose() {
      this.setAllDirty();
      this.level = null;
   }

   public final int appendLists(int[] var1, int var2, int var3) {
      if(!this.visible) {
         return var2;
      } else {
         if(!this.dirty[var3]) {
            var1[var2++] = this.baseListId + var3;
         }

         return var2;
      }
   }

   public final void clip(Frustrum var1) {
      this.visible = var1.isBoxInFrustrum((float)this.x, (float)this.y, (float)this.z, (float)(this.x + this.width), (float)(this.y + this.height), (float)(this.z + this.depth));
   }

}
