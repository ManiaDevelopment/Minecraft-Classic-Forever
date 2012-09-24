package com.mojang.util;


public final class MathHelper {

   private static float[] SIN_TABLE = new float[65536];


   public static final float sin(float var0) {
      return SIN_TABLE[(int)(var0 * 10430.378F) & '\uffff'];
   }

   public static final float cos(float var0) {
      return SIN_TABLE[(int)(var0 * 10430.378F + 16384.0F) & '\uffff'];
   }

   public static final float sqrt(float var0) {
      return (float)Math.sqrt((double)var0);
   }

   static {
      for(int var0 = 0; var0 < 65536; ++var0) {
         SIN_TABLE[var0] = (float)Math.sin((double)var0 * 3.141592653589793D * 2.0D / 65536.0D);
      }

   }
}
