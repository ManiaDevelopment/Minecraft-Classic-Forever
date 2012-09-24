package com.mojang.minecraft.phys;

import com.mojang.minecraft.MovingObjectPosition;
import com.mojang.minecraft.model.Vec3D;
import java.io.Serializable;

public class AABB implements Serializable {

   public static final long serialVersionUID = 0L;
   private float epsilon = 0.0F;
   public float x0;
   public float y0;
   public float z0;
   public float x1;
   public float y1;
   public float z1;


   public AABB(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.x0 = var1;
      this.y0 = var2;
      this.z0 = var3;
      this.x1 = var4;
      this.y1 = var5;
      this.z1 = var6;
   }

   public AABB expand(float var1, float var2, float var3) {
      float var4 = this.x0;
      float var5 = this.y0;
      float var6 = this.z0;
      float var7 = this.x1;
      float var8 = this.y1;
      float var9 = this.z1;
      if(var1 < 0.0F) {
         var4 += var1;
      }

      if(var1 > 0.0F) {
         var7 += var1;
      }

      if(var2 < 0.0F) {
         var5 += var2;
      }

      if(var2 > 0.0F) {
         var8 += var2;
      }

      if(var3 < 0.0F) {
         var6 += var3;
      }

      if(var3 > 0.0F) {
         var9 += var3;
      }

      return new AABB(var4, var5, var6, var7, var8, var9);
   }

   public AABB grow(float var1, float var2, float var3) {
      float var4 = this.x0 - var1;
      float var5 = this.y0 - var2;
      float var6 = this.z0 - var3;
      var1 += this.x1;
      var2 += this.y1;
      float var7 = this.z1 + var3;
      return new AABB(var4, var5, var6, var1, var2, var7);
   }

   public AABB cloneMove(float var1, float var2, float var3) {
      return new AABB(this.x0 + var3, this.y0 + var2, this.z0 + var3, this.x1 + var1, this.y1 + var2, this.z1 + var3);
   }

   public float clipXCollide(AABB var1, float var2) {
      if(var1.y1 > this.y0 && var1.y0 < this.y1) {
         if(var1.z1 > this.z0 && var1.z0 < this.z1) {
            float var3;
            if(var2 > 0.0F && var1.x1 <= this.x0 && (var3 = this.x0 - var1.x1 - this.epsilon) < var2) {
               var2 = var3;
            }

            if(var2 < 0.0F && var1.x0 >= this.x1 && (var3 = this.x1 - var1.x0 + this.epsilon) > var2) {
               var2 = var3;
            }

            return var2;
         } else {
            return var2;
         }
      } else {
         return var2;
      }
   }

   public float clipYCollide(AABB var1, float var2) {
      if(var1.x1 > this.x0 && var1.x0 < this.x1) {
         if(var1.z1 > this.z0 && var1.z0 < this.z1) {
            float var3;
            if(var2 > 0.0F && var1.y1 <= this.y0 && (var3 = this.y0 - var1.y1 - this.epsilon) < var2) {
               var2 = var3;
            }

            if(var2 < 0.0F && var1.y0 >= this.y1 && (var3 = this.y1 - var1.y0 + this.epsilon) > var2) {
               var2 = var3;
            }

            return var2;
         } else {
            return var2;
         }
      } else {
         return var2;
      }
   }

   public float clipZCollide(AABB var1, float var2) {
      if(var1.x1 > this.x0 && var1.x0 < this.x1) {
         if(var1.y1 > this.y0 && var1.y0 < this.y1) {
            float var3;
            if(var2 > 0.0F && var1.z1 <= this.z0 && (var3 = this.z0 - var1.z1 - this.epsilon) < var2) {
               var2 = var3;
            }

            if(var2 < 0.0F && var1.z0 >= this.z1 && (var3 = this.z1 - var1.z0 + this.epsilon) > var2) {
               var2 = var3;
            }

            return var2;
         } else {
            return var2;
         }
      } else {
         return var2;
      }
   }

   public boolean intersects(AABB var1) {
      return var1.x1 > this.x0 && var1.x0 < this.x1?(var1.y1 > this.y0 && var1.y0 < this.y1?var1.z1 > this.z0 && var1.z0 < this.z1:false):false;
   }

   public boolean intersectsInner(AABB var1) {
      return var1.x1 >= this.x0 && var1.x0 <= this.x1?(var1.y1 >= this.y0 && var1.y0 <= this.y1?var1.z1 >= this.z0 && var1.z0 <= this.z1:false):false;
   }

   public void move(float var1, float var2, float var3) {
      this.x0 += var1;
      this.y0 += var2;
      this.z0 += var3;
      this.x1 += var1;
      this.y1 += var2;
      this.z1 += var3;
   }

   public boolean intersects(float var1, float var2, float var3, float var4, float var5, float var6) {
      return var4 > this.x0 && var1 < this.x1?(var5 > this.y0 && var2 < this.y1?var6 > this.z0 && var3 < this.z1:false):false;
   }

   public boolean contains(Vec3D var1) {
      return var1.x > this.x0 && var1.x < this.x1?(var1.y > this.y0 && var1.y < this.y1?var1.z > this.z0 && var1.z < this.z1:false):false;
   }

   public float getSize() {
      float var1 = this.x1 - this.x0;
      float var2 = this.y1 - this.y0;
      float var3 = this.z1 - this.z0;
      return (var1 + var2 + var3) / 3.0F;
   }

   public AABB shrink(float var1, float var2, float var3) {
      float var4 = this.x0;
      float var5 = this.y0;
      float var6 = this.z0;
      float var7 = this.x1;
      float var8 = this.y1;
      float var9 = this.z1;
      if(var1 < 0.0F) {
         var4 -= var1;
      }

      if(var1 > 0.0F) {
         var7 -= var1;
      }

      if(var2 < 0.0F) {
         var5 -= var2;
      }

      if(var2 > 0.0F) {
         var8 -= var2;
      }

      if(var3 < 0.0F) {
         var6 -= var3;
      }

      if(var3 > 0.0F) {
         var9 -= var3;
      }

      return new AABB(var4, var5, var6, var7, var8, var9);
   }

   public AABB copy() {
      return new AABB(this.x0, this.y0, this.z0, this.x1, this.y1, this.z1);
   }

   public MovingObjectPosition clip(Vec3D var1, Vec3D var2) {
      Vec3D var3 = var1.getXIntersection(var2, this.x0);
      Vec3D var4 = var1.getXIntersection(var2, this.x1);
      Vec3D var5 = var1.getYIntersection(var2, this.y0);
      Vec3D var6 = var1.getYIntersection(var2, this.y1);
      Vec3D var7 = var1.getZIntersection(var2, this.z0);
      var2 = var1.getZIntersection(var2, this.z1);
      if(!this.xIntersects(var3)) {
         var3 = null;
      }

      if(!this.xIntersects(var4)) {
         var4 = null;
      }

      if(!this.yIntersects(var5)) {
         var5 = null;
      }

      if(!this.yIntersects(var6)) {
         var6 = null;
      }

      if(!this.zIntersects(var7)) {
         var7 = null;
      }

      if(!this.zIntersects(var2)) {
         var2 = null;
      }

      Vec3D var8 = null;
      if(var3 != null) {
         var8 = var3;
      }

      if(var4 != null && (var8 == null || var1.distanceSquared(var4) < var1.distanceSquared(var8))) {
         var8 = var4;
      }

      if(var5 != null && (var8 == null || var1.distanceSquared(var5) < var1.distanceSquared(var8))) {
         var8 = var5;
      }

      if(var6 != null && (var8 == null || var1.distanceSquared(var6) < var1.distanceSquared(var8))) {
         var8 = var6;
      }

      if(var7 != null && (var8 == null || var1.distanceSquared(var7) < var1.distanceSquared(var8))) {
         var8 = var7;
      }

      if(var2 != null && (var8 == null || var1.distanceSquared(var2) < var1.distanceSquared(var8))) {
         var8 = var2;
      }

      if(var8 == null) {
         return null;
      } else {
         byte var9 = -1;
         if(var8 == var3) {
            var9 = 4;
         }

         if(var8 == var4) {
            var9 = 5;
         }

         if(var8 == var5) {
            var9 = 0;
         }

         if(var8 == var6) {
            var9 = 1;
         }

         if(var8 == var7) {
            var9 = 2;
         }

         if(var8 == var2) {
            var9 = 3;
         }

         return new MovingObjectPosition(0, 0, 0, var9, var8);
      }
   }

   private boolean xIntersects(Vec3D var1) {
      return var1 == null?false:var1.y >= this.y0 && var1.y <= this.y1 && var1.z >= this.z0 && var1.z <= this.z1;
   }

   private boolean yIntersects(Vec3D var1) {
      return var1 == null?false:var1.x >= this.x0 && var1.x <= this.x1 && var1.z >= this.z0 && var1.z <= this.z1;
   }

   private boolean zIntersects(Vec3D var1) {
      return var1 == null?false:var1.x >= this.x0 && var1.x <= this.x1 && var1.y >= this.y0 && var1.y <= this.y1;
   }
}
