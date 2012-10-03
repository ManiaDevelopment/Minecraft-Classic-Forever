package com.mojang.minecraft.mob;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.mob.Mob;

public class QuadrupedMob extends Mob {

   public static final long serialVersionUID = 0L;


   public QuadrupedMob(Level var1, float var2, float var3, float var4) {
      super(var1);
      this.setSize(1.4F, 1.2F);
      this.setPos(var2, var3, var4);
      this.modelName = "pig";
   }
}
