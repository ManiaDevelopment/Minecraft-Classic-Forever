package com.mojang.minecraft.render;

import com.mojang.minecraft.render.Frustrum;
import com.mojang.util.MathHelper;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class FrustrumImpl extends Frustrum {

   private static FrustrumImpl instance = new FrustrumImpl();
   private FloatBuffer projectionBuff = BufferUtils.createFloatBuffer(16);
   private FloatBuffer modelviewBuff = BufferUtils.createFloatBuffer(16);
   private FloatBuffer unused = BufferUtils.createFloatBuffer(16);


   public static Frustrum update() {
      FrustrumImpl var0 = instance;
      instance.projectionBuff.clear();
      var0.modelviewBuff.clear();
      var0.unused.clear();
      GL11.glGetFloat(2983, var0.projectionBuff);
      GL11.glGetFloat(2982, var0.modelviewBuff);
      var0.projectionBuff.flip().limit(16);
      var0.projectionBuff.get(var0.projection);
      var0.modelviewBuff.flip().limit(16);
      var0.modelviewBuff.get(var0.modelview);
      var0.clipping[0] = var0.modelview[0] * var0.projection[0] + var0.modelview[1] * var0.projection[4] + var0.modelview[2] * var0.projection[8] + var0.modelview[3] * var0.projection[12];
      var0.clipping[1] = var0.modelview[0] * var0.projection[1] + var0.modelview[1] * var0.projection[5] + var0.modelview[2] * var0.projection[9] + var0.modelview[3] * var0.projection[13];
      var0.clipping[2] = var0.modelview[0] * var0.projection[2] + var0.modelview[1] * var0.projection[6] + var0.modelview[2] * var0.projection[10] + var0.modelview[3] * var0.projection[14];
      var0.clipping[3] = var0.modelview[0] * var0.projection[3] + var0.modelview[1] * var0.projection[7] + var0.modelview[2] * var0.projection[11] + var0.modelview[3] * var0.projection[15];
      var0.clipping[4] = var0.modelview[4] * var0.projection[0] + var0.modelview[5] * var0.projection[4] + var0.modelview[6] * var0.projection[8] + var0.modelview[7] * var0.projection[12];
      var0.clipping[5] = var0.modelview[4] * var0.projection[1] + var0.modelview[5] * var0.projection[5] + var0.modelview[6] * var0.projection[9] + var0.modelview[7] * var0.projection[13];
      var0.clipping[6] = var0.modelview[4] * var0.projection[2] + var0.modelview[5] * var0.projection[6] + var0.modelview[6] * var0.projection[10] + var0.modelview[7] * var0.projection[14];
      var0.clipping[7] = var0.modelview[4] * var0.projection[3] + var0.modelview[5] * var0.projection[7] + var0.modelview[6] * var0.projection[11] + var0.modelview[7] * var0.projection[15];
      var0.clipping[8] = var0.modelview[8] * var0.projection[0] + var0.modelview[9] * var0.projection[4] + var0.modelview[10] * var0.projection[8] + var0.modelview[11] * var0.projection[12];
      var0.clipping[9] = var0.modelview[8] * var0.projection[1] + var0.modelview[9] * var0.projection[5] + var0.modelview[10] * var0.projection[9] + var0.modelview[11] * var0.projection[13];
      var0.clipping[10] = var0.modelview[8] * var0.projection[2] + var0.modelview[9] * var0.projection[6] + var0.modelview[10] * var0.projection[10] + var0.modelview[11] * var0.projection[14];
      var0.clipping[11] = var0.modelview[8] * var0.projection[3] + var0.modelview[9] * var0.projection[7] + var0.modelview[10] * var0.projection[11] + var0.modelview[11] * var0.projection[15];
      var0.clipping[12] = var0.modelview[12] * var0.projection[0] + var0.modelview[13] * var0.projection[4] + var0.modelview[14] * var0.projection[8] + var0.modelview[15] * var0.projection[12];
      var0.clipping[13] = var0.modelview[12] * var0.projection[1] + var0.modelview[13] * var0.projection[5] + var0.modelview[14] * var0.projection[9] + var0.modelview[15] * var0.projection[13];
      var0.clipping[14] = var0.modelview[12] * var0.projection[2] + var0.modelview[13] * var0.projection[6] + var0.modelview[14] * var0.projection[10] + var0.modelview[15] * var0.projection[14];
      var0.clipping[15] = var0.modelview[12] * var0.projection[3] + var0.modelview[13] * var0.projection[7] + var0.modelview[14] * var0.projection[11] + var0.modelview[15] * var0.projection[15];
      var0.frustrum[0][0] = var0.clipping[3] - var0.clipping[0];
      var0.frustrum[0][1] = var0.clipping[7] - var0.clipping[4];
      var0.frustrum[0][2] = var0.clipping[11] - var0.clipping[8];
      var0.frustrum[0][3] = var0.clipping[15] - var0.clipping[12];
      normalize(var0.frustrum, 0);
      var0.frustrum[1][0] = var0.clipping[3] + var0.clipping[0];
      var0.frustrum[1][1] = var0.clipping[7] + var0.clipping[4];
      var0.frustrum[1][2] = var0.clipping[11] + var0.clipping[8];
      var0.frustrum[1][3] = var0.clipping[15] + var0.clipping[12];
      normalize(var0.frustrum, 1);
      var0.frustrum[2][0] = var0.clipping[3] + var0.clipping[1];
      var0.frustrum[2][1] = var0.clipping[7] + var0.clipping[5];
      var0.frustrum[2][2] = var0.clipping[11] + var0.clipping[9];
      var0.frustrum[2][3] = var0.clipping[15] + var0.clipping[13];
      normalize(var0.frustrum, 2);
      var0.frustrum[3][0] = var0.clipping[3] - var0.clipping[1];
      var0.frustrum[3][1] = var0.clipping[7] - var0.clipping[5];
      var0.frustrum[3][2] = var0.clipping[11] - var0.clipping[9];
      var0.frustrum[3][3] = var0.clipping[15] - var0.clipping[13];
      normalize(var0.frustrum, 3);
      var0.frustrum[4][0] = var0.clipping[3] - var0.clipping[2];
      var0.frustrum[4][1] = var0.clipping[7] - var0.clipping[6];
      var0.frustrum[4][2] = var0.clipping[11] - var0.clipping[10];
      var0.frustrum[4][3] = var0.clipping[15] - var0.clipping[14];
      normalize(var0.frustrum, 4);
      var0.frustrum[5][0] = var0.clipping[3] + var0.clipping[2];
      var0.frustrum[5][1] = var0.clipping[7] + var0.clipping[6];
      var0.frustrum[5][2] = var0.clipping[11] + var0.clipping[10];
      var0.frustrum[5][3] = var0.clipping[15] + var0.clipping[14];
      normalize(var0.frustrum, 5);
      return instance;
   }

   private static void normalize(float[][] var0, int var1) {
      float var2 = MathHelper.sqrt(var0[var1][0] * var0[var1][0] + var0[var1][1] * var0[var1][1] + var0[var1][2] * var0[var1][2]);
      var0[var1][0] /= var2;
      var0[var1][1] /= var2;
      var0[var1][2] /= var2;
      var0[var1][3] /= var2;
   }

}
