package com.mojang.minecraft.player;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.mob.Mob;
import com.mojang.minecraft.model.HumanoidModel;
import com.mojang.minecraft.player.InputHandler;
import com.mojang.minecraft.player.Inventory;
import com.mojang.minecraft.player.Player$1;
import com.mojang.minecraft.render.TextureManager;
import com.mojang.util.MathHelper;
import java.awt.image.BufferedImage;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class Player extends Mob {

   public static final long serialVersionUID = 0L;
   public static final int MAX_HEALTH = 20;
   public static final int MAX_ARROWS = 99;
   public transient InputHandler input;
   public Inventory inventory = new Inventory();
   public byte userType = 0;
   public float oBob;
   public float bob;
   public int score = 0;
   public int arrows = 20;
   private static int newTextureId = -1;
   public static BufferedImage newTexture;


   public Player(Level var1) {
      super(var1);
      if(var1 != null) {
         var1.player = this;
         var1.removeEntity(this);
         var1.addEntity(this);
      }

      this.heightOffset = 1.62F;
      this.health = 20;
      this.modelName = "humanoid";
      this.rotOffs = 180.0F;
      this.ai = new Player$1(this);
   }

   public void resetPos() {
      this.heightOffset = 1.62F;
      this.setSize(0.6F, 1.8F);
      super.resetPos();
      if(this.level != null) {
         this.level.player = this;
      }

      this.health = 20;
      this.deathTime = 0;
   }

   public void aiStep() {
      this.inventory.tick();
      this.oBob = this.bob;
      this.input.updateMovement();
      super.aiStep();
      float var1 = MathHelper.sqrt(this.xd * this.xd + this.zd * this.zd);
      float var2 = (float)Math.atan((double)(-this.yd * 0.2F)) * 15.0F;
      if(var1 > 0.1F) {
         var1 = 0.1F;
      }

      if(!this.onGround || this.health <= 0) {
         var1 = 0.0F;
      }

      if(this.onGround || this.health <= 0) {
         var2 = 0.0F;
      }

      this.bob += (var1 - this.bob) * 0.4F;
      this.tilt += (var2 - this.tilt) * 0.8F;
      List var3;
      if(this.health > 0 && (var3 = this.level.findEntities(this, this.bb.grow(1.0F, 0.0F, 1.0F))) != null) {
         for(int var4 = 0; var4 < var3.size(); ++var4) {
            ((Entity)var3.get(var4)).playerTouch(this);
         }
      }

   }

   public void render(TextureManager var1, float var2) {}

   public void releaseAllKeys() {
      this.input.resetKeys();
   }

   public void setKey(int var1, boolean var2) {
      this.input.setKeyState(var1, var2);
   }

   public boolean addResource(int var1) {
      return this.inventory.addResource(var1);
   }

   public int getScore() {
      return this.score;
   }

   public HumanoidModel getModel() {
      return (HumanoidModel)modelCache.getModel(this.modelName);
   }

   public void die(Entity var1) {
      this.setSize(0.2F, 0.2F);
      this.setPos(this.x, this.y, this.z);
      this.yd = 0.1F;
      if(var1 != null) {
         this.xd = -MathHelper.cos((this.hurtDir + this.yRot) * 3.1415927F / 180.0F) * 0.1F;
         this.zd = -MathHelper.sin((this.hurtDir + this.yRot) * 3.1415927F / 180.0F) * 0.1F;
      } else {
         this.xd = this.zd = 0.0F;
      }

      this.heightOffset = 0.1F;
   }

   public void remove() {}

   public void awardKillScore(Entity var1, int var2) {
      this.score += var2;
   }

   public boolean isShootable() {
      return true;
   }

   public void bindTexture(TextureManager var1) {
      if(newTexture != null) {
         newTextureId = var1.load(newTexture);
         newTexture = null;
      }

      int var2;
      if(newTextureId < 0) {
         var2 = var1.load("/char.png");
         GL11.glBindTexture(3553, var2);
      } else {
         var2 = newTextureId;
         GL11.glBindTexture(3553, var2);
      }
   }

   public void hurt(Entity var1, int var2) {
      if(!this.level.creativeMode) {
         super.hurt(var1, var2);
      }

   }

   public boolean isCreativeModeAllowed() {
      return true;
   }

}
