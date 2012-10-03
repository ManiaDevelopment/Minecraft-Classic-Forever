package com.mojang.minecraft;

import com.mojang.minecraft.level.BlockMap;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.LiquidType;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.level.tile.Tile$SoundType;
import com.mojang.minecraft.model.Vec3D;
import com.mojang.minecraft.net.PositionUpdate;
import com.mojang.minecraft.phys.AABB;
import com.mojang.minecraft.render.TextureManager;
import com.mojang.util.MathHelper;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Entity implements Serializable {

   public static final long serialVersionUID = 0L;
   public Level level;
   public float xo;
   public float yo;
   public float zo;
   public float x;
   public float y;
   public float z;
   public float xd;
   public float yd;
   public float zd;
   public float yRot;
   public float xRot;
   public float yRotO;
   public float xRotO;
   public AABB bb;
   public boolean onGround = false;
   public boolean horizontalCollision = false;
   public boolean collision = false;
   public boolean slide = true;
   public boolean removed = false;
   public float heightOffset = 0.0F;
   public float bbWidth = 0.6F;
   public float bbHeight = 1.8F;
   public float walkDistO = 0.0F;
   public float walkDist = 0.0F;
   public boolean makeStepSound = true;
   public float fallDistance = 0.0F;
   private int nextStep = 1;
   public BlockMap blockMap;
   public float xOld;
   public float yOld;
   public float zOld;
   public int textureId = 0;
   public float ySlideOffset = 0.0F;
   public float footSize = 0.0F;
   public boolean noPhysics = false;
   public float pushthrough = 0.0F;
   public boolean hovered = false;


   public Entity(Level var1) {
      this.level = var1;
      this.setPos(0.0F, 0.0F, 0.0F);
   }

   public void resetPos() {
      if(this.level != null) {
         float var1 = (float)this.level.xSpawn + 0.5F;
         float var2 = (float)this.level.ySpawn;

         for(float var3 = (float)this.level.zSpawn + 0.5F; var2 > 0.0F; ++var2) {
            this.setPos(var1, var2, var3);
            if(this.level.getCubes(this.bb).size() == 0) {
               break;
            }
         }

         this.xd = this.yd = this.zd = 0.0F;
         this.yRot = this.level.rotSpawn;
         this.xRot = 0.0F;
      }
   }

   public void remove() {
      this.removed = true;
   }

   public void setSize(float var1, float var2) {
      this.bbWidth = var1;
      this.bbHeight = var2;
   }

   public void setPos(PositionUpdate var1) {
      if(var1.position) {
         this.setPos(var1.x, var1.y, var1.z);
      } else {
         this.setPos(this.x, this.y, this.z);
      }

      if(var1.rotation) {
         this.setRot(var1.yaw, var1.pitch);
      } else {
         this.setRot(this.yRot, this.xRot);
      }
   }

   protected void setRot(float var1, float var2) {
      this.yRot = var1;
      this.xRot = var2;
   }

   public void setPos(float var1, float var2, float var3) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
      float var4 = this.bbWidth / 2.0F;
      float var5 = this.bbHeight / 2.0F;
      this.bb = new AABB(var1 - var4, var2 - var5, var3 - var4, var1 + var4, var2 + var5, var3 + var4);
   }

   public void turn(float var1, float var2) {
      float var3 = this.xRot;
      float var4 = this.yRot;
      this.yRot = (float)((double)this.yRot + (double)var1 * 0.15D);
      this.xRot = (float)((double)this.xRot - (double)var2 * 0.15D);
      if(this.xRot < -90.0F) {
         this.xRot = -90.0F;
      }

      if(this.xRot > 90.0F) {
         this.xRot = 90.0F;
      }

      this.xRotO += this.xRot - var3;
      this.yRotO += this.yRot - var4;
   }

   public void interpolateTurn(float var1, float var2) {
      this.yRot = (float)((double)this.yRot + (double)var1 * 0.15D);
      this.xRot = (float)((double)this.xRot - (double)var2 * 0.15D);
      if(this.xRot < -90.0F) {
         this.xRot = -90.0F;
      }

      if(this.xRot > 90.0F) {
         this.xRot = 90.0F;
      }

   }

   public void tick() {
      this.walkDistO = this.walkDist;
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.xRotO = this.xRot;
      this.yRotO = this.yRot;
   }

   public boolean isFree(float var1, float var2, float var3, float var4) {
      AABB var5 = this.bb.grow(var4, var4, var4).cloneMove(var1, var2, var3);
      return this.level.getCubes(var5).size() > 0?false:!this.level.containsAnyLiquid(var5);
   }

   public boolean isFree(float var1, float var2, float var3) {
      AABB var4 = this.bb.cloneMove(var1, var2, var3);
      return this.level.getCubes(var4).size() > 0?false:!this.level.containsAnyLiquid(var4);
   }

   public void move(float var1, float var2, float var3) {
      if(this.noPhysics) {
         this.bb.move(var1, var2, var3);
         this.x = (this.bb.x0 + this.bb.x1) / 2.0F;
         this.y = this.bb.y0 + this.heightOffset - this.ySlideOffset;
         this.z = (this.bb.z0 + this.bb.z1) / 2.0F;
      } else {
         float var4 = this.x;
         float var5 = this.z;
         float var6 = var1;
         float var7 = var2;
         float var8 = var3;
         AABB var9 = this.bb.copy();
         ArrayList var10 = this.level.getCubes(this.bb.expand(var1, var2, var3));

         for(int var11 = 0; var11 < var10.size(); ++var11) {
            var2 = ((AABB)var10.get(var11)).clipYCollide(this.bb, var2);
         }

         this.bb.move(0.0F, var2, 0.0F);
         if(!this.slide && var7 != var2) {
            var3 = 0.0F;
            var2 = 0.0F;
            var1 = 0.0F;
         }

         boolean var16 = this.onGround || var7 != var2 && var7 < 0.0F;

         int var12;
         for(var12 = 0; var12 < var10.size(); ++var12) {
            var1 = ((AABB)var10.get(var12)).clipXCollide(this.bb, var1);
         }

         this.bb.move(var1, 0.0F, 0.0F);
         if(!this.slide && var6 != var1) {
            var3 = 0.0F;
            var2 = 0.0F;
            var1 = 0.0F;
         }

         for(var12 = 0; var12 < var10.size(); ++var12) {
            var3 = ((AABB)var10.get(var12)).clipZCollide(this.bb, var3);
         }

         this.bb.move(0.0F, 0.0F, var3);
         if(!this.slide && var8 != var3) {
            var3 = 0.0F;
            var2 = 0.0F;
            var1 = 0.0F;
         }

         float var17;
         float var18;
         if(this.footSize > 0.0F && var16 && this.ySlideOffset < 0.05F && (var6 != var1 || var8 != var3)) {
            var18 = var1;
            var17 = var2;
            float var13 = var3;
            var1 = var6;
            var2 = this.footSize;
            var3 = var8;
            AABB var14 = this.bb.copy();
            this.bb = var9.copy();
            var10 = this.level.getCubes(this.bb.expand(var6, var2, var8));

            int var15;
            for(var15 = 0; var15 < var10.size(); ++var15) {
               var2 = ((AABB)var10.get(var15)).clipYCollide(this.bb, var2);
            }

            this.bb.move(0.0F, var2, 0.0F);
            if(!this.slide && var7 != var2) {
               var3 = 0.0F;
               var2 = 0.0F;
               var1 = 0.0F;
            }

            for(var15 = 0; var15 < var10.size(); ++var15) {
               var1 = ((AABB)var10.get(var15)).clipXCollide(this.bb, var1);
            }

            this.bb.move(var1, 0.0F, 0.0F);
            if(!this.slide && var6 != var1) {
               var3 = 0.0F;
               var2 = 0.0F;
               var1 = 0.0F;
            }

            for(var15 = 0; var15 < var10.size(); ++var15) {
               var3 = ((AABB)var10.get(var15)).clipZCollide(this.bb, var3);
            }

            this.bb.move(0.0F, 0.0F, var3);
            if(!this.slide && var8 != var3) {
               var3 = 0.0F;
               var2 = 0.0F;
               var1 = 0.0F;
            }

            if(var18 * var18 + var13 * var13 >= var1 * var1 + var3 * var3) {
               var1 = var18;
               var2 = var17;
               var3 = var13;
               this.bb = var14.copy();
            } else {
               this.ySlideOffset = (float)((double)this.ySlideOffset + 0.5D);
            }
         }

         this.horizontalCollision = var6 != var1 || var8 != var3;
         this.onGround = var7 != var2 && var7 < 0.0F;
         this.collision = this.horizontalCollision || var7 != var2;
         if(this.onGround) {
            if(this.fallDistance > 0.0F) {
               this.causeFallDamage(this.fallDistance);
               this.fallDistance = 0.0F;
            }
         } else if(var2 < 0.0F) {
            this.fallDistance -= var2;
         }

         if(var6 != var1) {
            this.xd = 0.0F;
         }

         if(var7 != var2) {
            this.yd = 0.0F;
         }

         if(var8 != var3) {
            this.zd = 0.0F;
         }

         this.x = (this.bb.x0 + this.bb.x1) / 2.0F;
         this.y = this.bb.y0 + this.heightOffset - this.ySlideOffset;
         this.z = (this.bb.z0 + this.bb.z1) / 2.0F;
         var18 = this.x - var4;
         var17 = this.z - var5;
         this.walkDist = (float)((double)this.walkDist + (double)MathHelper.sqrt(var18 * var18 + var17 * var17) * 0.6D);
         if(this.makeStepSound) {
            int var19 = this.level.getTile((int)this.x, (int)(this.y - 0.2F - this.heightOffset), (int)this.z);
            if(this.walkDist > (float)this.nextStep && var19 > 0) {
               ++this.nextStep;
               Tile$SoundType var20;
               if((var20 = Block.blocks[var19].stepsound) != Tile$SoundType.none) {
                  this.playSound("step." + var20.name, var20.getVolume() * 0.75F, var20.getPitch());
               }
            }
         }

         this.ySlideOffset *= 0.4F;
      }
   }

   protected void causeFallDamage(float var1) {}

   public boolean isInWater() {
      return this.level.containsLiquid(this.bb.grow(0.0F, -0.4F, 0.0F), LiquidType.WATER);
   }

   public boolean isUnderWater() {
      int var1;
      return (var1 = this.level.getTile((int)this.x, (int)(this.y + 0.12F), (int)this.z)) != 0?Block.blocks[var1].getLiquidType().equals(LiquidType.WATER):false;
   }

   public boolean isInLava() {
      return this.level.containsLiquid(this.bb.grow(0.0F, -0.4F, 0.0F), LiquidType.LAVA);
   }

   public void moveRelative(float var1, float var2, float var3) {
      float var4;
      if((var4 = MathHelper.sqrt(var1 * var1 + var2 * var2)) >= 0.01F) {
         if(var4 < 1.0F) {
            var4 = 1.0F;
         }

         var4 = var3 / var4;
         var1 *= var4;
         var2 *= var4;
         var3 = MathHelper.sin(this.yRot * 3.1415927F / 180.0F);
         var4 = MathHelper.cos(this.yRot * 3.1415927F / 180.0F);
         this.xd += var1 * var4 - var2 * var3;
         this.zd += var2 * var4 + var1 * var3;
      }
   }

   public boolean isLit() {
      int var1 = (int)this.x;
      int var2 = (int)this.y;
      int var3 = (int)this.z;
      return this.level.isLit(var1, var2, var3);
   }

   public float getBrightness(float var1) {
      int var4 = (int)this.x;
      int var2 = (int)(this.y + this.heightOffset / 2.0F - 0.5F);
      int var3 = (int)this.z;
      return this.level.getBrightness(var4, var2, var3);
   }

   public void render(TextureManager var1, float var2) {}

   public void setLevel(Level var1) {
      this.level = var1;
   }

   public void playSound(String var1, float var2, float var3) {
      this.level.playSound(var1, this, var2, var3);
   }

   public void moveTo(float var1, float var2, float var3, float var4, float var5) {
      this.xo = this.x = var1;
      this.yo = this.y = var2;
      this.zo = this.z = var3;
      this.yRot = var4;
      this.xRot = var5;
      this.setPos(var1, var2, var3);
   }

   public float distanceTo(Entity var1) {
      float var2 = this.x - var1.x;
      float var3 = this.y - var1.y;
      float var4 = this.z - var1.z;
      return MathHelper.sqrt(var2 * var2 + var3 * var3 + var4 * var4);
   }

   public float distanceTo(float var1, float var2, float var3) {
      var1 = this.x - var1;
      var2 = this.y - var2;
      float var4 = this.z - var3;
      return MathHelper.sqrt(var1 * var1 + var2 * var2 + var4 * var4);
   }

   public float distanceToSqr(Entity var1) {
      float var2 = this.x - var1.x;
      float var3 = this.y - var1.y;
      float var4 = this.z - var1.z;
      return var2 * var2 + var3 * var3 + var4 * var4;
   }

   public void playerTouch(Entity var1) {}

   public void push(Entity var1) {
      float var2 = var1.x - this.x;
      float var3 = var1.z - this.z;
      float var4;
      if((var4 = var2 * var2 + var3 * var3) >= 0.01F) {
         var4 = MathHelper.sqrt(var4);
         var2 /= var4;
         var3 /= var4;
         var2 /= var4;
         var3 /= var4;
         var2 *= 0.05F;
         var3 *= 0.05F;
         var2 *= 1.0F - this.pushthrough;
         var3 *= 1.0F - this.pushthrough;
         this.push(-var2, 0.0F, -var3);
         var1.push(var2, 0.0F, var3);
      }

   }

   protected void push(float var1, float var2, float var3) {
      this.xd += var1;
      this.yd += var2;
      this.zd += var3;
   }

   public void hurt(Entity var1, int var2) {}

   public boolean intersects(float var1, float var2, float var3, float var4, float var5, float var6) {
      return this.bb.intersects(var1, var2, var3, var4, var5, var6);
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean isShootable() {
      return false;
   }

   public void awardKillScore(Entity var1, int var2) {}

   public boolean shouldRender(Vec3D var1) {
      float var2 = this.x - var1.x;
      float var3 = this.y - var1.y;
      float var4 = this.z - var1.z;
      var4 = var2 * var2 + var3 * var3 + var4 * var4;
      return this.shouldRenderAtSqrDistance(var4);
   }

   public boolean shouldRenderAtSqrDistance(float var1) {
      float var2 = this.bb.getSize() * 64.0F;
      return var1 < var2 * var2;
   }

   public int getTexture() {
      return this.textureId;
   }

   public boolean isCreativeModeAllowed() {
      return false;
   }

   public void renderHover(TextureManager var1, float var2) {}
}
