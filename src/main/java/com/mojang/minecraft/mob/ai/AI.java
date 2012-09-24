package com.mojang.minecraft.mob.ai;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.mob.Mob;
import java.io.Serializable;

public abstract class AI implements Serializable {

   public static final long serialVersionUID = 0L;
   public int defaultLookAngle = 0;


   public void tick(Level var1, Mob var2) {}

   public void beforeRemove() {}

   public void hurt(Entity var1, int var2) {}
}
