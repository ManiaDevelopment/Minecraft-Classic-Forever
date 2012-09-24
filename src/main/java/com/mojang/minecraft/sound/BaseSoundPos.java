package com.mojang.minecraft.sound;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.sound.SoundPos;
import com.mojang.util.MathHelper;

public abstract class BaseSoundPos implements SoundPos {

   private Entity listener;


   public BaseSoundPos(Entity var1) {
      this.listener = var1;
   }

   public final float getRotationDiff(float var1, float var2) {
      var1 -= this.listener.x;
      var2 -= this.listener.z;
      float var3 = MathHelper.sqrt(var1 * var1 + var2 * var2);
      var1 /= var3;
      var2 /= var3;
      if((var3 /= 2.0F) > 1.0F) {
         var3 = 1.0F;
      }

      float var4 = MathHelper.cos(-this.listener.yRot * 0.017453292F + 3.1415927F);
      return (MathHelper.sin(-this.listener.yRot * 0.017453292F + 3.1415927F) * var2 - var4 * var1) * var3;
   }

   public final float getDistanceSq(float var1, float var2, float var3) {
      var1 -= this.listener.x;
      var2 -= this.listener.y;
      float var4 = var3 - this.listener.z;
      var4 = MathHelper.sqrt(var1 * var1 + var2 * var2 + var4 * var4);
      if((var4 = 1.0F - var4 / 32.0F) < 0.0F) {
         var4 = 0.0F;
      }

      return var4;
   }
}
