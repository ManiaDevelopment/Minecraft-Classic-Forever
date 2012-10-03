package com.mojang.minecraft.mob;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.item.Item;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.mob.QuadrupedMob;
import com.mojang.minecraft.mob.Sheep$1;
import com.mojang.minecraft.model.AnimalModel;
import com.mojang.minecraft.player.Player;
import com.mojang.minecraft.render.TextureManager;
import org.lwjgl.opengl.GL11;

public class Sheep extends QuadrupedMob {

   public static final long serialVersionUID = 0L;
   public boolean hasFur = true;
   public boolean grazing = false;
   public int grazingTime = 0;
   public float graze;
   public float grazeO;


   public Sheep(Level var1, float var2, float var3, float var4) {
      super(var1, var2, var3, var4);
      this.setSize(1.4F, 1.72F);
      this.setPos(var2, var3, var4);
      this.heightOffset = 1.72F;
      this.modelName = "sheep";
      this.textureName = "/mob/sheep.png";
      this.ai = new Sheep$1(this);
   }

   public void aiStep() {
      super.aiStep();
      this.grazeO = this.graze;
      if(this.grazing) {
         this.graze += 0.2F;
      } else {
         this.graze -= 0.2F;
      }

      if(this.graze < 0.0F) {
         this.graze = 0.0F;
      }

      if(this.graze > 1.0F) {
         this.graze = 1.0F;
      }

   }

   public void die(Entity var1) {
      if(var1 != null) {
         var1.awardKillScore(this, 10);
      }

      int var2 = (int)(Math.random() + Math.random() + 1.0D);

      for(int var3 = 0; var3 < var2; ++var3) {
         this.level.addEntity(new Item(this.level, this.x, this.y, this.z, Block.BROWN_MUSHROOM.id));
      }

      super.die(var1);
   }

   public void hurt(Entity var1, int var2) {
      if(this.hasFur && var1 instanceof Player) {
         this.hasFur = false;
         int var3 = (int)(Math.random() * 3.0D + 1.0D);

         for(var2 = 0; var2 < var3; ++var2) {
            this.level.addEntity(new Item(this.level, this.x, this.y, this.z, Block.WHITE_WOOL.id));
         }

      } else {
         super.hurt(var1, var2);
      }
   }

   public void renderModel(TextureManager var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      AnimalModel var8;
      float var9 = (var8 = (AnimalModel)modelCache.getModel(this.modelName)).head.y;
      float var10 = var8.head.z;
      var8.head.y += (this.grazeO + (this.graze - this.grazeO) * var3) * 8.0F;
      var8.head.z -= this.grazeO + (this.graze - this.grazeO) * var3;
      super.renderModel(var1, var2, var3, var4, var5, var6, var7);
      if(this.hasFur) {
         GL11.glBindTexture(3553, var1.load("/mob/sheep_fur.png"));
         GL11.glDisable(2884);
         AnimalModel var11;
         (var11 = (AnimalModel)modelCache.getModel("sheep.fur")).head.yaw = var8.head.yaw;
         var11.head.pitch = var8.head.pitch;
         var11.head.y = var8.head.y;
         var11.head.x = var8.head.x;
         var11.body.yaw = var8.body.yaw;
         var11.body.pitch = var8.body.pitch;
         var11.leg1.pitch = var8.leg1.pitch;
         var11.leg2.pitch = var8.leg2.pitch;
         var11.leg3.pitch = var8.leg3.pitch;
         var11.leg4.pitch = var8.leg4.pitch;
         var11.head.render(var7);
         var11.body.render(var7);
         var11.leg1.render(var7);
         var11.leg2.render(var7);
         var11.leg3.render(var7);
         var11.leg4.render(var7);
      }

      var8.head.y = var9;
      var8.head.z = var10;
   }
}
