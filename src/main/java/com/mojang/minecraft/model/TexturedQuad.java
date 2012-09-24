package com.mojang.minecraft.model;

import com.mojang.minecraft.model.Vertex;

public final class TexturedQuad {

   public Vertex[] vertices;


   private TexturedQuad(Vertex[] var1) {
      this.vertices = var1;
   }

   public TexturedQuad(Vertex[] var1, int var2, int var3, int var4, int var5) {
      this(var1);
      float var7 = 0.0015625F;
      float var6 = 0.003125F;
      var1[0] = var1[0].create((float)var4 / 64.0F - var7, (float)var3 / 32.0F + var6);
      var1[1] = var1[1].create((float)var2 / 64.0F + var7, (float)var3 / 32.0F + var6);
      var1[2] = var1[2].create((float)var2 / 64.0F + var7, (float)var5 / 32.0F - var6);
      var1[3] = var1[3].create((float)var4 / 64.0F - var7, (float)var5 / 32.0F - var6);
   }

   public TexturedQuad(Vertex[] var1, float var2, float var3, float var4, float var5) {
      this(var1);
      var1[0] = var1[0].create(var4, var3);
      var1[1] = var1[1].create(var2, var3);
      var1[2] = var1[2].create(var2, var5);
      var1[3] = var1[3].create(var4, var5);
   }
}
