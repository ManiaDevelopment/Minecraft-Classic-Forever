package com.mojang.minecraft.mob;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.mob.Creeper;
import com.mojang.minecraft.mob.ai.BasicAttackAI;
import com.mojang.minecraft.particle.TerrainParticle;
import com.mojang.util.MathHelper;

final class Creeper$1 extends BasicAttackAI {

   public static final long serialVersionUID = 0L;
   // $FF: synthetic field
   final Creeper creeper;


   Creeper$1(Creeper var1) {
      this.creeper = var1;
   }

   public final boolean attack(Entity var1) {
      if(!super.attack(var1)) {
         return false;
      } else {
         this.mob.hurt(var1, 6);
         return true;
      }
   }

   public final void beforeRemove() {
      float var1 = 4.0F;
      this.level.explode(this.mob, this.mob.x, this.mob.y, this.mob.z, var1);

      for(int var2 = 0; var2 < 500; ++var2) {
         float var3 = (float)this.random.nextGaussian() * var1 / 4.0F;
         float var4 = (float)this.random.nextGaussian() * var1 / 4.0F;
         float var5 = (float)this.random.nextGaussian() * var1 / 4.0F;
         float var6 = MathHelper.sqrt(var3 * var3 + var4 * var4 + var5 * var5);
         float var7 = var3 / var6 / var6;
         float var8 = var4 / var6 / var6;
         var6 = var5 / var6 / var6;
         this.level.particleEngine.spawnParticle(new TerrainParticle(this.level, this.mob.x + var3, this.mob.y + var4, this.mob.z + var5, var7, var8, var6, Block.LEAVES));
      }

   }
}
