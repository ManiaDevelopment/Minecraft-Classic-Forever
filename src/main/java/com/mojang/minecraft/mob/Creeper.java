package com.mojang.minecraft.mob;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.mob.Creeper$1;
import com.mojang.minecraft.mob.Mob;
import com.mojang.util.MathHelper;

public class Creeper extends Mob {

   public static final long serialVersionUID = 0L;


   public Creeper(Level var1, float var2, float var3, float var4) {
      super(var1);
      this.heightOffset = 1.62F;
      this.modelName = "creeper";
      this.textureName = "/mob/creeper.png";
      this.ai = new Creeper$1(this);
      this.ai.defaultLookAngle = 45;
      this.deathScore = 200;
      this.setPos(var2, var3, var4);
   }

   public float getBrightness(float var1) {
      float var2 = (float)(20 - this.health) / 20.0F;
      return ((MathHelper.sin((float)this.tickCount + var1) * 0.5F + 0.5F) * var2 * 0.5F + 0.25F + var2 * 0.25F) * super.getBrightness(var1);
   }
}
