package com.mojang.minecraft.model;

import com.mojang.minecraft.model.Vec3D;

public final class Vertex {

   public Vec3D vector;
   public float u;
   public float v;


   public Vertex(float var1, float var2, float var3, float var4, float var5) {
      this(new Vec3D(var1, var2, var3), var4, var5);
   }

   public final Vertex create(float var1, float var2) {
      return new Vertex(this, var1, var2);
   }

   private Vertex(Vertex var1, float var2, float var3) {
      this.vector = var1.vector;
      this.u = var2;
      this.v = var3;
   }

   private Vertex(Vec3D var1, float var2, float var3) {
      this.vector = var1;
      this.u = var2;
      this.v = var3;
   }
}
