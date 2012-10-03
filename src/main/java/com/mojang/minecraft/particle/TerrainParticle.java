package com.mojang.minecraft.particle;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.particle.Particle;
import com.mojang.minecraft.render.ShapeRenderer;

public class TerrainParticle extends Particle {

   private static final long serialVersionUID = 1L;


   public TerrainParticle(Level var1, float var2, float var3, float var4, float var5, float var6, float var7, Block var8) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.tex = var8.textureId;
      this.gravity = var8.particleGravity;
      this.rCol = this.gCol = this.bCol = 0.6F;
   }

   public int getParticleTexture() {
      return 1;
   }

   public void render(ShapeRenderer var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8;
      float var9 = (var8 = ((float)(this.tex % 16) + this.uo / 4.0F) / 16.0F) + 0.015609375F;
      float var10;
      float var11 = (var10 = ((float)(this.tex / 16) + this.vo / 4.0F) / 16.0F) + 0.015609375F;
      float var12 = 0.1F * this.size;
      float var13 = this.xo + (this.x - this.xo) * var2;
      float var14 = this.yo + (this.y - this.yo) * var2;
      float var15 = this.zo + (this.z - this.zo) * var2;
      var2 = this.getBrightness(var2);
      var1.color(var2 * this.rCol, var2 * this.gCol, var2 * this.bCol);
      var1.vertexUV(var13 - var3 * var12 - var6 * var12, var14 - var4 * var12, var15 - var5 * var12 - var7 * var12, var8, var11);
      var1.vertexUV(var13 - var3 * var12 + var6 * var12, var14 + var4 * var12, var15 - var5 * var12 + var7 * var12, var8, var10);
      var1.vertexUV(var13 + var3 * var12 + var6 * var12, var14 + var4 * var12, var15 + var5 * var12 + var7 * var12, var9, var10);
      var1.vertexUV(var13 + var3 * var12 - var6 * var12, var14 - var4 * var12, var15 + var5 * var12 - var7 * var12, var9, var11);
   }
}
