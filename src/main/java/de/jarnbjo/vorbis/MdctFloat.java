package de.jarnbjo.vorbis;

import com.mojang.util.MathHelper;

final class MdctFloat {

   private int n;
   private int log2n;
   private float[] trig;
   private int[] bitrev;
   private float dtmp1;
   private float dtmp2;
   private float dtmp3;
   private float dtmp4;
   private float[] _x = new float[1024];
   private float[] _w = new float[1024];


   protected MdctFloat(int var1) {
      this.bitrev = new int[var1 / 4];
      this.trig = new float[var1 + var1 / 4];
      this.log2n = (int)Math.rint(Math.log((double)var1) / Math.log(2.0D));
      this.n = var1;
      int var2;
      int var3 = (var2 = 0 + var1 / 2) + 1;
      int var4;
      int var5 = (var4 = var2 + var1 / 2) + 1;

      int var6;
      for(var6 = 0; var6 < var1 / 4; ++var6) {
         this.trig[0 + (var6 << 1)] = MathHelper.cos(3.1415927F / (float)var1 * (float)(var6 * 4));
         this.trig[1 + (var6 << 1)] = -MathHelper.sin(3.1415927F / (float)var1 * (float)(var6 * 4));
         this.trig[var2 + (var6 << 1)] = MathHelper.cos(3.1415927F / (float)(var1 * 2) * (float)(var6 * 2 + 1));
         this.trig[var3 + (var6 << 1)] = MathHelper.sin(3.1415927F / (float)(var1 * 2) * (float)(var6 * 2 + 1));
      }

      for(var6 = 0; var6 < var1 / 8; ++var6) {
         this.trig[var4 + (var6 << 1)] = MathHelper.cos(3.1415927F / (float)var1 * (float)(var6 * 4 + 2));
         this.trig[var5 + (var6 << 1)] = -MathHelper.sin(3.1415927F / (float)var1 * (float)(var6 * 4 + 2));
      }

      var6 = (1 << this.log2n - 1) - 1;
      var2 = 1 << this.log2n - 2;

      for(var3 = 0; var3 < var1 / 8; ++var3) {
         var4 = 0;

         for(var5 = 0; var2 >>> var5 != 0; ++var5) {
            if((var2 >>> var5 & var3) != 0) {
               var4 |= 1 << var5;
            }
         }

         this.bitrev[var3 << 1] = ~var4 & var6;
         this.bitrev[(var3 << 1) + 1] = var4;
      }

   }

   protected final synchronized void imdct(float[] var1, float[] var2, int[] var3) {
      var1 = var1;
      if(this._x.length < this.n / 2) {
         this._x = new float[this.n / 2];
      }

      if(this._w.length < this.n / 2) {
         this._w = new float[this.n / 2];
      }

      float[] var4 = this._x;
      float[] var5 = this._w;
      int var6 = this.n >> 1;
      int var7 = this.n >> 2;
      int var8 = this.n >> 3;
      int var9 = -1;
      int var10 = 0;
      int var11 = var6;

      int var12;
      for(var12 = 0; var12 < var8; ++var12) {
         var9 += 2;
         this.dtmp1 = var1[var9];
         var9 += 2;
         this.dtmp2 = var1[var9];
         --var11;
         this.dtmp3 = this.trig[var11];
         --var11;
         this.dtmp4 = this.trig[var11];
         var4[var10++] = -this.dtmp2 * this.dtmp3 - this.dtmp1 * this.dtmp4;
         var4[var10++] = this.dtmp1 * this.dtmp3 - this.dtmp2 * this.dtmp4;
      }

      var9 = var6;

      for(var12 = 0; var12 < var8; ++var12) {
         var9 -= 2;
         this.dtmp1 = var1[var9];
         var9 -= 2;
         this.dtmp2 = var1[var9];
         --var11;
         this.dtmp3 = this.trig[var11];
         --var11;
         this.dtmp4 = this.trig[var11];
         var4[var10++] = this.dtmp2 * this.dtmp3 + this.dtmp1 * this.dtmp4;
         var4[var10++] = this.dtmp2 * this.dtmp4 - this.dtmp1 * this.dtmp3;
      }

      var11 = var8;
      var10 = var7;
      var9 = var6;
      var8 = this.n;
      var5 = var5;
      var4 = var4;
      MdctFloat var25 = this;
      var12 = var7;
      int var13 = 0;
      int var14 = var7;
      int var15 = var6;

      int var16;
      for(var16 = 0; var16 < var10; ++var16) {
         float var17 = var4[var12] - var4[var13];
         var5[var14 + var16] = var4[var12++] + var4[var13++];
         float var18 = var4[var12] - var4[var13];
         var15 -= 4;
         var5[var16++] = var17 * var25.trig[var15] + var18 * var25.trig[var15 + 1];
         var5[var16] = var18 * var25.trig[var15] - var17 * var25.trig[var15 + 1];
         var5[var14 + var16] = var4[var12++] + var4[var13++];
      }

      int var19;
      float var21;
      float var20;
      float var23;
      float var24;
      int var34;
      int var35;
      for(var16 = 0; var16 < var25.log2n - 3; ++var16) {
         var34 = var8 >>> var16 + 2;
         var35 = 1 << var16 + 3;
         var10 = var9 - 2;
         var15 = 0;

         for(var13 = 0; var13 < var34 >>> 2; ++var13) {
            var19 = var10;
            var14 = var10 - (var34 >> 1);
            var20 = var25.trig[var15];
            var21 = var25.trig[var15 + 1];
            var10 -= 2;
            ++var34;

            for(int var22 = 0; var22 < 2 << var16; ++var22) {
               var25.dtmp1 = var5[var19];
               var25.dtmp2 = var5[var14];
               var23 = var25.dtmp1 - var25.dtmp2;
               var4[var19] = var25.dtmp1 + var25.dtmp2;
               ++var19;
               var25.dtmp1 = var5[var19];
               ++var14;
               var25.dtmp2 = var5[var14];
               var24 = var25.dtmp1 - var25.dtmp2;
               var4[var19] = var25.dtmp1 + var25.dtmp2;
               var4[var14] = var24 * var20 - var23 * var21;
               var4[var14 - 1] = var23 * var20 + var24 * var21;
               var19 -= var34;
               var14 -= var34;
            }

            --var34;
            var15 += var35;
         }

         float[] var33 = var5;
         var5 = var4;
         var4 = var33;
      }

      var16 = var8;
      var34 = 0;
      var35 = 0;
      var10 = var9 - 1;

      float var29;
      float var32;
      for(var12 = 0; var12 < var11; ++var12) {
         var13 = var25.bitrev[var34++];
         var19 = var25.bitrev[var34++];
         var20 = var5[var13] - var5[var19 + 1];
         var24 = var5[var13 - 1] + var5[var19];
         var21 = var5[var13] + var5[var19 + 1];
         var23 = var5[var13 - 1] - var5[var19];
         float var36 = var20 * var25.trig[var16];
         var29 = var24 * var25.trig[var16++];
         float var30 = var20 * var25.trig[var16];
         var32 = var24 * var25.trig[var16++];
         var4[var35++] = (var21 + var30 + var29) * 16383.0F;
         var4[var10--] = (-var23 + var32 - var36) * 16383.0F;
         var4[var35++] = (var23 + var32 - var36) * 16383.0F;
         var4[var10--] = (var21 - var30 - var29) * 16383.0F;
      }

      float[] var31 = var4;
      var10 = 0;
      var11 = var6;
      var12 = var7;
      int var26 = var7 - 1;
      int var27;
      int var28 = (var27 = var7 + var6) - 1;

      for(var6 = 0; var6 < var7; ++var6) {
         this.dtmp1 = var31[var10++];
         this.dtmp2 = var31[var10++];
         this.dtmp3 = this.trig[var11++];
         this.dtmp4 = this.trig[var11++];
         var29 = this.dtmp1 * this.dtmp4 - this.dtmp2 * this.dtmp3;
         var32 = -(this.dtmp1 * this.dtmp3 + this.dtmp2 * this.dtmp4);
         var3[var12] = (int)(-var29 * var2[var12]);
         var3[var26] = (int)(var29 * var2[var26]);
         var3[var27] = (int)(var32 * var2[var27]);
         var3[var28] = (int)(var32 * var2[var28]);
         ++var12;
         --var26;
         ++var27;
         --var28;
      }

   }
}
