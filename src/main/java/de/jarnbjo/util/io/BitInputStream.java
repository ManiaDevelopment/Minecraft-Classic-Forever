package de.jarnbjo.util.io;

import de.jarnbjo.util.io.HuffmanNode;

public final class BitInputStream {

   private byte[] source;
   private byte currentByte;
   private int endian;
   private int byteIndex;
   private int bitIndex;


   public BitInputStream(byte[] var1) {
      this(var1, 0);
   }

   private BitInputStream(byte[] var1, int var2) {
      this.byteIndex = 0;
      this.bitIndex = 0;
      this.endian = 0;
      this.source = var1;
      this.currentByte = var1[0];
      this.bitIndex = 0;
   }

   public final boolean getBit() {
      if(this.endian == 0) {
         if(this.bitIndex > 7) {
            this.bitIndex = 0;
            this.currentByte = this.source[++this.byteIndex];
         }

         return (this.currentByte & 1 << this.bitIndex++) != 0;
      } else {
         if(this.bitIndex < 0) {
            this.bitIndex = 7;
            this.currentByte = this.source[++this.byteIndex];
         }

         return (this.currentByte & 1 << this.bitIndex--) != 0;
      }
   }

   public final int getInt(int var1) {
      if(var1 > 32) {
         throw new IllegalArgumentException("Argument \"bits\" must be <= 32");
      } else {
         int var2 = 0;
         int var3;
         if(this.endian == 0) {
            for(var3 = 0; var3 < var1; ++var3) {
               if(this.getBit()) {
                  var2 |= 1 << var3;
               }
            }
         } else {
            if(this.bitIndex < 0) {
               this.bitIndex = 7;
               this.currentByte = this.source[++this.byteIndex];
            }

            if(var1 <= this.bitIndex + 1) {
               var3 = this.currentByte & 255;
               var2 = 1 + this.bitIndex - var1;
               int var4 = (1 << var1) - 1 << var2;
               var2 = (var3 & var4) >> var2;
               this.bitIndex -= var1;
            } else {
               var2 = (this.currentByte & 255 & (1 << this.bitIndex + 1) - 1) << var1 - this.bitIndex - 1;
               var1 -= this.bitIndex + 1;

               for(this.currentByte = this.source[++this.byteIndex]; var1 >= 8; this.currentByte = this.source[++this.byteIndex]) {
                  var1 -= 8;
                  var2 |= (this.source[this.byteIndex] & 255) << var1;
               }

               if(var1 > 0) {
                  var3 = this.source[this.byteIndex] & 255;
                  var2 |= var3 >> 8 - var1 & (1 << var1) - 1;
                  this.bitIndex = 7 - var1;
               } else {
                  this.currentByte = this.source[--this.byteIndex];
                  this.bitIndex = -1;
               }
            }
         }

         return var2;
      }
   }

   public final int getInt(HuffmanNode var1) {
      for(; var1.value == null; var1 = (this.currentByte & 1 << this.bitIndex++) != 0?var1.o1:var1.o0) {
         if(this.bitIndex > 7) {
            this.bitIndex = 0;
            this.currentByte = this.source[++this.byteIndex];
         }
      }

      return var1.value.intValue();
   }

   public final long getLong(int var1) {
      if(var1 > 64) {
         throw new IllegalArgumentException("Argument \"bits\" must be <= 64");
      } else {
         long var2 = 0L;
         int var4;
         if(this.endian == 0) {
            for(var4 = 0; var4 < var1; ++var4) {
               if(this.getBit()) {
                  var2 |= 1L << var4;
               }
            }
         } else {
            for(var4 = var1 - 1; var4 >= 0; --var4) {
               if(this.getBit()) {
                  var2 |= 1L << var4;
               }
            }
         }

         return var2;
      }
   }
}
