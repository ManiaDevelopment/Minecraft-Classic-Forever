package com.mojang.minecraft.sound;

// TODO.
public final class SoundInfo extends AudioInfo {

   private SoundData data;
   private float seek = 0.0F;
   private float pitch;


   public SoundInfo(SoundData var1, float var2, float var3) {
      this.data = var1;
      this.pitch = var2 * 44100.0F / var1.length;
      this.volume = var3;
   }

   public final int update(short[] var1, int var2) {
      if(this.seek >= (float)this.data.data.length) {
         return 0;
      } else {
         for(int var3 = 0; var3 < var2; ++var3) {
            int var4 = (int)this.seek;
            short var5 = this.data.data[var4];
            short var6 = var4 < this.data.data.length - 1?this.data.data[var4 + 1]:0;
            var1[var3] = (short)((int)((float)var5 + (float)(var6 - var5) * (this.seek - (float)var4)));
            this.seek += this.pitch;
            if(this.seek >= (float)this.data.data.length) {
               return var3;
            }
         }

         return var2;
      }
   }
}
