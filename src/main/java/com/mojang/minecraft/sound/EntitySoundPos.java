package com.mojang.minecraft.sound;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.sound.BaseSoundPos;

public final class EntitySoundPos extends BaseSoundPos {

   private Entity source;


   public EntitySoundPos(Entity var1, Entity var2) {
      super(var2);
      this.source = var1;
   }

   public final float getRotationDiff() {
      return super.getRotationDiff(this.source.x, this.source.z);
   }

   public final float getDistanceSq() {
      return super.getDistanceSq(this.source.x, this.source.y, this.source.z);
   }
}
