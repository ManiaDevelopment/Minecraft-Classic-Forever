package com.mojang.minecraft.mob;

import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.mob.Sheep;
import com.mojang.minecraft.mob.ai.BasicAI;
import com.mojang.util.MathHelper;

final class Sheep$1 extends BasicAI {

   private static final long serialVersionUID = 1L;
   // $FF: synthetic field
   final Sheep sheep;


   Sheep$1(Sheep var1) {
      this.sheep = var1;
   }

   protected final void update() {
      float var1 = MathHelper.sin(this.sheep.yRot * 3.1415927F / 180.0F);
      float var2 = MathHelper.cos(this.sheep.yRot * 3.1415927F / 180.0F);
      var1 = -0.7F * var1;
      var2 = 0.7F * var2;
      int var4 = (int)(this.mob.x + var1);
      int var3 = (int)(this.mob.y - 2.0F);
      int var5 = (int)(this.mob.z + var2);
      if(this.sheep.grazing) {
         if(this.level.getTile(var4, var3, var5) != Block.GRASS.id) {
            this.sheep.grazing = false;
         } else {
            if(++this.sheep.grazingTime == 60) {
               this.level.setTile(var4, var3, var5, Block.DIRT.id);
               if(this.random.nextInt(5) == 0) {
                  this.sheep.hasFur = true;
               }
            }

            this.xxa = 0.0F;
            this.yya = 0.0F;
            this.mob.xRot = (float)(40 + this.sheep.grazingTime / 2 % 2 * 10);
         }
      } else {
         if(this.level.getTile(var4, var3, var5) == Block.GRASS.id) {
            this.sheep.grazing = true;
            this.sheep.grazingTime = 0;
         }

         super.update();
      }
   }
}
