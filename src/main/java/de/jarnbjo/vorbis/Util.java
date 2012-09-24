package de.jarnbjo.vorbis;

import de.jarnbjo.vorbis.Floor;

public final class Util {

   public static final int ilog(int var0) {
      int var1;
      for(var1 = 0; var0 > 0; ++var1) {
         var0 >>= 1;
      }

      return var1;
   }

   public static final float float32unpack(int var0) {
      float var1 = (float)(var0 & 2097151);
      float var2 = (float)((var0 & 2145386496) >> 21);
      if((var0 & Integer.MIN_VALUE) != 0) {
         var1 = -var1;
      }

      return var1 * (float)Math.pow(2.0D, (double)var2 - 788.0D);
   }

   public static final int lowNeighbour(int[] var0, int var1) {
      int var2 = -1;
      int var3 = 0;

      for(int var4 = 0; var4 < var0.length && var4 < var1; ++var4) {
         if(var0[var4] > var2 && var0[var4] < var0[var1]) {
            var2 = var0[var4];
            var3 = var4;
         }
      }

      return var3;
   }

   public static final int highNeighbour(int[] var0, int var1) {
      int var2 = Integer.MAX_VALUE;
      int var3 = 0;

      for(int var4 = 0; var4 < var0.length && var4 < var1; ++var4) {
         if(var0[var4] < var2 && var0[var4] > var0[var1]) {
            var2 = var0[var4];
            var3 = var4;
         }
      }

      return var3;
   }

   public static final void renderLine(int var0, int var1, int var2, int var3, float[] var4) {
      var3 -= var1;
      int var5 = var2 - var0;
      int var6 = var3 / var5;
      int var7 = var3 < 0?var6 - 1:var6 + 1;
      var1 = var1;
      int var9 = 0;
      var3 = (var3 < 0?-var3:var3) - (var6 > 0?var6 * var5:-var6 * var5);
      var4[var0] *= Floor.DB_STATIC_TABLE[var1];

      for(int var8 = var0 + 1; var8 < var2; ++var8) {
         if((var9 += var3) >= var5) {
            var9 -= var5;
            var4[var8] *= Floor.DB_STATIC_TABLE[var1 += var7];
         } else {
            var4[var8] *= Floor.DB_STATIC_TABLE[var1 += var6];
         }
      }

   }
}
