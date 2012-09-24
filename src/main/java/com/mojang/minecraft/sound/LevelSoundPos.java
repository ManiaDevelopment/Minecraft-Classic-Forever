package com.mojang.minecraft.sound;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.sound.BaseSoundPos;

public final class LevelSoundPos extends BaseSoundPos {

   private float x;
   private float y;
   private float z;


   public LevelSoundPos(float var1, float var2, float var3, Entity var4) {
      super(var4);
      this.x = var1;
      this.y = var2;
      this.z = var3;
   }

   public final float getRotationDiff() {
      return super.getRotationDiff(this.x, this.z);
   }

   public final float getDistanceSq() {
      return super.getDistanceSq(this.x, this.y, this.z);
   }
}
