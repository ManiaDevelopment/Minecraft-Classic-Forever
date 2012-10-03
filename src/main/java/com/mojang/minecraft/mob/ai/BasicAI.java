package com.mojang.minecraft.mob.ai;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.mob.Mob;
import com.mojang.minecraft.mob.ai.AI;
import java.util.List;
import java.util.Random;

public class BasicAI extends AI {

   public static final long serialVersionUID = 0L;
   public Random random = new Random();
   public float xxa;
   public float yya;
   protected float yRotA;
   public Level level;
   public Mob mob;
   public boolean jumping = false;
   protected int attackDelay = 0;
   public float runSpeed = 0.7F;
   protected int noActionTime = 0;
   public Entity attackTarget = null;


   public void tick(Level var1, Mob var2) {
      ++this.noActionTime;
      Entity var3;
      if(this.noActionTime > 600 && this.random.nextInt(800) == 0 && (var3 = var1.getPlayer()) != null) {
         float var4 = var3.x - var2.x;
         float var5 = var3.y - var2.y;
         float var6 = var3.z - var2.z;
         if(var4 * var4 + var5 * var5 + var6 * var6 < 1024.0F) {
            this.noActionTime = 0;
         } else {
            var2.remove();
         }
      }

      this.level = var1;
      this.mob = var2;
      if(this.attackDelay > 0) {
         --this.attackDelay;
      }

      if(var2.health <= 0) {
         this.jumping = false;
         this.xxa = 0.0F;
         this.yya = 0.0F;
         this.yRotA = 0.0F;
      } else {
         this.update();
      }

      boolean var7 = var2.isInWater();
      boolean var9 = var2.isInLava();
      if(this.jumping) {
         if(var7) {
            var2.yd += 0.04F;
         } else if(var9) {
            var2.yd += 0.04F;
         } else if(var2.onGround) {
            this.jumpFromGround();
         }
      }

      this.xxa *= 0.98F;
      this.yya *= 0.98F;
      this.yRotA *= 0.9F;
      var2.travel(this.xxa, this.yya);
      List var11;
      if((var11 = var1.findEntities(var2, var2.bb.grow(0.2F, 0.0F, 0.2F))) != null && var11.size() > 0) {
         for(int var8 = 0; var8 < var11.size(); ++var8) {
            Entity var10;
            if((var10 = (Entity)var11.get(var8)).isPushable()) {
               var10.push(var2);
            }
         }
      }

   }

   protected void jumpFromGround() {
      this.mob.yd = 0.42F;
   }

   protected void update() {
      if(this.random.nextFloat() < 0.07F) {
         this.xxa = (this.random.nextFloat() - 0.5F) * this.runSpeed;
         this.yya = this.random.nextFloat() * this.runSpeed;
      }

      this.jumping = this.random.nextFloat() < 0.01F;
      if(this.random.nextFloat() < 0.04F) {
         this.yRotA = (this.random.nextFloat() - 0.5F) * 60.0F;
      }

      this.mob.yRot += this.yRotA;
      this.mob.xRot = (float)this.defaultLookAngle;
      if(this.attackTarget != null) {
         this.yya = this.runSpeed;
         this.jumping = this.random.nextFloat() < 0.04F;
      }

      boolean var1 = this.mob.isInWater();
      boolean var2 = this.mob.isInLava();
      if(var1 || var2) {
         this.jumping = this.random.nextFloat() < 0.8F;
      }

   }

   public void beforeRemove() {}

   public void hurt(Entity var1, int var2) {
      super.hurt(var1, var2);
      this.noActionTime = 0;
   }
}
