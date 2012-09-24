package com.mojang.minecraft.gui;

import com.mojang.minecraft.GameSettings;
import com.mojang.minecraft.render.ShapeRenderer;
import com.mojang.minecraft.render.TextureManager;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public final class FontRenderer {

   private int[] widthmap = new int[256];
   private int fontTexture = 0;
   private GameSettings settings;


   public FontRenderer(GameSettings var1, String var2, TextureManager var3) {
      this.settings = var1;

      BufferedImage var14;
      try {
         var14 = ImageIO.read(TextureManager.class.getResourceAsStream(var2));
      } catch (IOException var13) {
         throw new RuntimeException(var13);
      }

      int var4 = var14.getWidth();
      int var5 = var14.getHeight();
      int[] var6 = new int[var4 * var5];
      var14.getRGB(0, 0, var4, var5, var6, 0, var4);

      for(int var15 = 0; var15 < 128; ++var15) {
         var5 = var15 % 16;
         int var7 = var15 / 16;
         int var8 = 0;

         for(boolean var9 = false; var8 < 8 && !var9; ++var8) {
            int var10 = (var5 << 3) + var8;
            var9 = true;

            for(int var11 = 0; var11 < 8 && var9; ++var11) {
               int var12 = ((var7 << 3) + var11) * var4;
               if((var6[var10 + var12] & 255) > 128) {
                  var9 = false;
               }
            }
         }

         if(var15 == 32) {
            var8 = 4;
         }

         this.widthmap[var15] = var8;
      }

      this.fontTexture = var3.load(var2);
   }

   public final void render(String var1, int var2, int var3, int var4) {
      this.render(var1, var2 + 1, var3 + 1, var4, true);
      this.renderNoShadow(var1, var2, var3, var4);
   }

   public final void renderNoShadow(String var1, int var2, int var3, int var4) {
      this.render(var1, var2, var3, var4, false);
   }

   private void render(String var1, int var2, int var3, int var4, boolean var5) {
      if(var1 != null) {
         char[] var12 = var1.toCharArray();
         if(var5) {
            var4 = (var4 & 16579836) >> 2;
         }

         GL11.glBindTexture(3553, this.fontTexture);
         ShapeRenderer var6 = ShapeRenderer.instance;
         ShapeRenderer.instance.begin();
         var6.color(var4);
         int var7 = 0;

         for(int var8 = 0; var8 < var12.length; ++var8) {
            int var9;
            if(var12[var8] == 38 && var12.length > var8 + 1) {
               if((var4 = "0123456789abcdef".indexOf(var12[var8 + 1])) < 0) {
                  var4 = 15;
               }

               var9 = (var4 & 8) << 3;
               int var10 = (var4 & 1) * 191 + var9;
               int var11 = ((var4 & 2) >> 1) * 191 + var9;
               var4 = ((var4 & 4) >> 2) * 191 + var9;
               if(this.settings.anaglyph) {
                  var9 = (var4 * 30 + var11 * 59 + var10 * 11) / 100;
                  var11 = (var4 * 30 + var11 * 70) / 100;
                  var10 = (var4 * 30 + var10 * 70) / 100;
                  var4 = var9;
                  var11 = var11;
                  var10 = var10;
               }

               var4 = var4 << 16 | var11 << 8 | var10;
               var8 += 2;
               if(var5) {
                  var4 = (var4 & 16579836) >> 2;
               }

               var6.color(var4);
            }

            var4 = var12[var8] % 16 << 3;
            var9 = var12[var8] / 16 << 3;
            float var13 = 7.99F;
            var6.vertexUV((float)(var2 + var7), (float)var3 + var13, 0.0F, (float)var4 / 128.0F, ((float)var9 + var13) / 128.0F);
            var6.vertexUV((float)(var2 + var7) + var13, (float)var3 + var13, 0.0F, ((float)var4 + var13) / 128.0F, ((float)var9 + var13) / 128.0F);
            var6.vertexUV((float)(var2 + var7) + var13, (float)var3, 0.0F, ((float)var4 + var13) / 128.0F, (float)var9 / 128.0F);
            var6.vertexUV((float)(var2 + var7), (float)var3, 0.0F, (float)var4 / 128.0F, (float)var9 / 128.0F);
            var7 += this.widthmap[var12[var8]];
         }

         var6.end();
      }
   }

   public final int getWidth(String var1) {
      if(var1 == null) {
         return 0;
      } else {
         char[] var4 = var1.toCharArray();
         int var2 = 0;

         for(int var3 = 0; var3 < var4.length; ++var3) {
            if(var4[var3] == 38) {
               ++var3;
            } else {
               var2 += this.widthmap[var4[var3]];
            }
         }

         return var2;
      }
   }

   public static String stripColor(String var0) {
      char[] var3 = var0.toCharArray();
      String var1 = "";

      for(int var2 = 0; var2 < var3.length; ++var2) {
         if(var3[var2] == 38) {
            ++var2;
         } else {
            var1 = var1 + var3[var2];
         }
      }

      return var1;
   }
}
