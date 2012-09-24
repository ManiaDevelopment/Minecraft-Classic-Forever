package com.mojang.minecraft.mob;

import com.mojang.minecraft.item.Arrow;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.mob.Skeleton$1;
import com.mojang.minecraft.mob.Zombie;

public class Skeleton extends Zombie {

   public static final long serialVersionUID = 0L;


   public Skeleton(Level var1, float var2, float var3, float var4) {
      super(var1, var2, var3, var4);
      this.modelName = "skeleton";
      this.textureName = "/mob/skeleton.png";
      Skeleton$1 var5 = new Skeleton$1(this);
      this.deathScore = 120;
      var5.runSpeed = 0.3F;
      var5.damage = 8;
      this.ai = var5;
   }

   public void shootArrow(Level var1) {
      var1.addEntity(new Arrow(var1, this, this.x, this.y, this.z, this.yRot + 180.0F + (float)(Math.random() * 45.0D - 22.5D), this.xRot - (float)(Math.random() * 45.0D - 10.0D), 1.0F));
   }

   // $FF: synthetic method
   static void shootRandomArrow(Skeleton var0) {
      var0 = var0;
      int var1 = (int)((Math.random() + Math.random()) * 3.0D + 4.0D);

      for(int var2 = 0; var2 < var1; ++var2) {
         var0.level.addEntity(new Arrow(var0.level, var0.level.getPlayer(), var0.x, var0.y - 0.2F, var0.z, (float)Math.random() * 360.0F, -((float)Math.random()) * 60.0F, 0.4F));
      }

   }
}
