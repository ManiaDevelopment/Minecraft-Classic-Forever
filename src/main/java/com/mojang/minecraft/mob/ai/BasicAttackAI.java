package com.mojang.minecraft.mob.ai;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.item.Arrow;
import com.mojang.minecraft.mob.ai.BasicAI;
import com.mojang.minecraft.model.Vec3D;
import com.mojang.util.MathHelper;

public class BasicAttackAI extends BasicAI {

   public static final long serialVersionUID = 0L;
   public int damage = 6;


   protected void update() {
      super.update();
      if(this.mob.health > 0) {
         this.doAttack();
      }

   }

   protected void doAttack() {
      Entity var1 = this.level.getPlayer();
      float var2 = 16.0F;
      if(this.attackTarget != null && this.attackTarget.removed) {
         this.attackTarget = null;
      }

      float var3;
      float var4;
      float var5;
      if(var1 != null && this.attackTarget == null) {
         var3 = var1.x - this.mob.x;
         var4 = var1.y - this.mob.y;
         var5 = var1.z - this.mob.z;
         if(var3 * var3 + var4 * var4 + var5 * var5 < var2 * var2) {
            this.attackTarget = var1;
         }
      }

      if(this.attackTarget != null) {
         var3 = this.attackTarget.x - this.mob.x;
         var4 = this.attackTarget.y - this.mob.y;
         var5 = this.attackTarget.z - this.mob.z;
         float var6;
         if((var6 = var3 * var3 + var4 * var4 + var5 * var5) > var2 * var2 * 2.0F * 2.0F && this.random.nextInt(100) == 0) {
            this.attackTarget = null;
         }

         if(this.attackTarget != null) {
            var6 = MathHelper.sqrt(var6);
            this.mob.yRot = (float)(Math.atan2((double)var5, (double)var3) * 180.0D / 3.1415927410125732D) - 90.0F;
            this.mob.xRot = -((float)(Math.atan2((double)var4, (double)var6) * 180.0D / 3.1415927410125732D));
            if(MathHelper.sqrt(var3 * var3 + var4 * var4 + var5 * var5) < 2.0F && this.attackDelay == 0) {
               this.attack(this.attackTarget);
            }
         }

      }
   }

   public boolean attack(Entity var1) {
      if(this.level.clip(new Vec3D(this.mob.x, this.mob.y, this.mob.z), new Vec3D(var1.x, var1.y, var1.z)) != null) {
         return false;
      } else {
         this.mob.attackTime = 5;
         this.attackDelay = this.random.nextInt(20) + 10;
         int var2 = (int)((this.random.nextFloat() + this.random.nextFloat()) / 2.0F * (float)this.damage + 1.0F);
         var1.hurt(this.mob, var2);
         this.noActionTime = 0;
         return true;
      }
   }

   public void hurt(Entity var1, int var2) {
      super.hurt(var1, var2);
      if(var1 instanceof Arrow) {
         var1 = ((Arrow)var1).getOwner();
      }

      if(var1 != null && !var1.getClass().equals(this.mob.getClass())) {
         this.attackTarget = var1;
      }

   }
}
