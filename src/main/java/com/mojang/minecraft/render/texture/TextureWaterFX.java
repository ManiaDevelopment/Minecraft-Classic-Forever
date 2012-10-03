package com.mojang.minecraft.render.texture;

import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.render.texture.TextureFX;

public final class TextureWaterFX extends TextureFX {

   private float[] red = new float[256];
   private float[] blue = new float[256];
   private float[] green = new float[256];
   private float[] alpha = new float[256];
   private int updates = 0;


   public TextureWaterFX() {
      super(Block.WATER.textureId);
   }

   public final void animate() {
      ++this.updates;

      int var1;
      int var2;
      float var3;
      int var4;
      int var5;
      int var6;
      for(var1 = 0; var1 < 16; ++var1) {
         for(var2 = 0; var2 < 16; ++var2) {
            var3 = 0.0F;

            for(var4 = var1 - 1; var4 <= var1 + 1; ++var4) {
               var5 = var4 & 15;
               var6 = var2 & 15;
               var3 += this.red[var5 + (var6 << 4)];
            }

            this.blue[var1 + (var2 << 4)] = var3 / 3.3F + this.green[var1 + (var2 << 4)] * 0.8F;
         }
      }

      for(var1 = 0; var1 < 16; ++var1) {
         for(var2 = 0; var2 < 16; ++var2) {
            this.green[var1 + (var2 << 4)] += this.alpha[var1 + (var2 << 4)] * 0.05F;
            if(this.green[var1 + (var2 << 4)] < 0.0F) {
               this.green[var1 + (var2 << 4)] = 0.0F;
            }

            this.alpha[var1 + (var2 << 4)] -= 0.1F;
            if(Math.random() < 0.05D) {
               this.alpha[var1 + (var2 << 4)] = 0.5F;
            }
         }
      }

      float[] var8 = this.blue;
      this.blue = this.red;
      this.red = var8;

      for(var2 = 0; var2 < 256; ++var2) {
         if((var3 = this.red[var2]) > 1.0F) {
            var3 = 1.0F;
         }

         if(var3 < 0.0F) {
            var3 = 0.0F;
         }

         float var9 = var3 * var3;
         var5 = (int)(32.0F + var9 * 32.0F);
         var6 = (int)(50.0F + var9 * 64.0F);
         var1 = 255;
         int var10 = (int)(146.0F + var9 * 50.0F);
         if(this.anaglyph) {
            var1 = (var5 * 30 + var6 * 59 + 2805) / 100;
            var4 = (var5 * 30 + var6 * 70) / 100;
            int var7 = (var5 * 30 + 17850) / 100;
            var5 = var1;
            var6 = var4;
            var1 = var7;
         }

         this.textureData[var2 << 2] = (byte)var5;
         this.textureData[(var2 << 2) + 1] = (byte)var6;
         this.textureData[(var2 << 2) + 2] = (byte)var1;
         this.textureData[(var2 << 2) + 3] = (byte)var10;
      }

   }
}
