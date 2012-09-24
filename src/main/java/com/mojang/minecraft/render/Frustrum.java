package com.mojang.minecraft.render;


public class Frustrum {

   public float[][] frustrum = new float[16][16];
   public float[] projection = new float[16];
   public float[] modelview = new float[16];
   public float[] clipping = new float[16];


   public final boolean isBoxInFrustrum(float var1, float var2, float var3, float var4, float var5, float var6) {
      for(int var7 = 0; var7 < 6; ++var7) {
         if(this.frustrum[var7][0] * var1 + this.frustrum[var7][1] * var2 + this.frustrum[var7][2] * var3 + this.frustrum[var7][3] <= 0.0F && this.frustrum[var7][0] * var4 + this.frustrum[var7][1] * var2 + this.frustrum[var7][2] * var3 + this.frustrum[var7][3] <= 0.0F && this.frustrum[var7][0] * var1 + this.frustrum[var7][1] * var5 + this.frustrum[var7][2] * var3 + this.frustrum[var7][3] <= 0.0F && this.frustrum[var7][0] * var4 + this.frustrum[var7][1] * var5 + this.frustrum[var7][2] * var3 + this.frustrum[var7][3] <= 0.0F && this.frustrum[var7][0] * var1 + this.frustrum[var7][1] * var2 + this.frustrum[var7][2] * var6 + this.frustrum[var7][3] <= 0.0F && this.frustrum[var7][0] * var4 + this.frustrum[var7][1] * var2 + this.frustrum[var7][2] * var6 + this.frustrum[var7][3] <= 0.0F && this.frustrum[var7][0] * var1 + this.frustrum[var7][1] * var5 + this.frustrum[var7][2] * var6 + this.frustrum[var7][3] <= 0.0F && this.frustrum[var7][0] * var4 + this.frustrum[var7][1] * var5 + this.frustrum[var7][2] * var6 + this.frustrum[var7][3] <= 0.0F) {
            return false;
         }
      }

      return true;
   }
}
