package com.mojang.minecraft.mob;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.mob.QuadrupedMob;
import com.mojang.minecraft.mob.ai.JumpAttackAI;

public class Spider extends QuadrupedMob {

   public static final long serialVersionUID = 0L;


   public Spider(Level var1, float var2, float var3, float var4) {
      super(var1, var2, var3, var4);
      this.heightOffset = 0.72F;
      this.modelName = "spider";
      this.textureName = "/mob/spider.png";
      this.setSize(1.4F, 0.9F);
      this.setPos(var2, var3, var4);
      this.deathScore = 105;
      this.bobStrength = 0.0F;
      this.ai = new JumpAttackAI();
   }
}
