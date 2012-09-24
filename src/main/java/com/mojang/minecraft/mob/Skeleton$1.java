package com.mojang.minecraft.mob;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.mob.Mob;
import com.mojang.minecraft.mob.Skeleton;
import com.mojang.minecraft.mob.ai.BasicAttackAI;

final class Skeleton$1 extends BasicAttackAI {

   public static final long serialVersionUID = 0L;
   // $FF: synthetic field
   final Skeleton parent;


   Skeleton$1(Skeleton var1) {
      this.parent = var1;
   }

   public final void tick(Level var1, Mob var2) {
      super.tick(var1, var2);
      if(var2.health > 0 && this.random.nextInt(30) == 0 && this.attackTarget != null) {
         this.parent.shootArrow(var1);
      }

   }

   public final void beforeRemove() {
      Skeleton.shootRandomArrow(this.parent);
   }
}
