package com.mojang.minecraft.model;

import com.mojang.minecraft.model.TexturedQuad;
import com.mojang.minecraft.model.Vec3D;
import com.mojang.minecraft.model.Vertex;
import org.lwjgl.opengl.GL11;

public final class ModelPart {

   public Vertex[] vertices;
   public TexturedQuad[] quads;
   private int u;
   private int v;
   public float x;
   public float y;
   public float z;
   public float pitch;
   public float yaw;
   public float roll;
   public boolean hasList = false;
   public int list = 0;
   public boolean mirror = false;
   public boolean render = true;
   private boolean unused = false;


   public ModelPart(int var1, int var2) {
      this.u = var1;
      this.v = var2;
   }

   public final void setBounds(float var1, float var2, float var3, int var4, int var5, int var6, float var7) {
      this.vertices = new Vertex[8];
      this.quads = new TexturedQuad[6];
      float var8 = var1 + (float)var4;
      float var9 = var2 + (float)var5;
      float var10 = var3 + (float)var6;
      var1 -= var7;
      var2 -= var7;
      var3 -= var7;
      var8 += var7;
      var9 += var7;
      var10 += var7;
      if(this.mirror) {
         var7 = var8;
         var8 = var1;
         var1 = var7;
      }

      Vertex var20 = new Vertex(var1, var2, var3, 0.0F, 0.0F);
      Vertex var11 = new Vertex(var8, var2, var3, 0.0F, 8.0F);
      Vertex var12 = new Vertex(var8, var9, var3, 8.0F, 8.0F);
      Vertex var18 = new Vertex(var1, var9, var3, 8.0F, 0.0F);
      Vertex var13 = new Vertex(var1, var2, var10, 0.0F, 0.0F);
      Vertex var15 = new Vertex(var8, var2, var10, 0.0F, 8.0F);
      Vertex var21 = new Vertex(var8, var9, var10, 8.0F, 8.0F);
      Vertex var14 = new Vertex(var1, var9, var10, 8.0F, 0.0F);
      this.vertices[0] = var20;
      this.vertices[1] = var11;
      this.vertices[2] = var12;
      this.vertices[3] = var18;
      this.vertices[4] = var13;
      this.vertices[5] = var15;
      this.vertices[6] = var21;
      this.vertices[7] = var14;
      this.quads[0] = new TexturedQuad(new Vertex[]{var15, var11, var12, var21}, this.u + var6 + var4, this.v + var6, this.u + var6 + var4 + var6, this.v + var6 + var5);
      this.quads[1] = new TexturedQuad(new Vertex[]{var20, var13, var14, var18}, this.u, this.v + var6, this.u + var6, this.v + var6 + var5);
      this.quads[2] = new TexturedQuad(new Vertex[]{var15, var13, var20, var11}, this.u + var6, this.v, this.u + var6 + var4, this.v + var6);
      this.quads[3] = new TexturedQuad(new Vertex[]{var12, var18, var14, var21}, this.u + var6 + var4, this.v, this.u + var6 + var4 + var4, this.v + var6);
      this.quads[4] = new TexturedQuad(new Vertex[]{var11, var20, var18, var12}, this.u + var6, this.v + var6, this.u + var6 + var4, this.v + var6 + var5);
      this.quads[5] = new TexturedQuad(new Vertex[]{var13, var15, var21, var14}, this.u + var6 + var4 + var6, this.v + var6, this.u + var6 + var4 + var6 + var4, this.v + var6 + var5);
      if(this.mirror) {
         for(int var16 = 0; var16 < this.quads.length; ++var16) {
            TexturedQuad var17;
            Vertex[] var19 = new Vertex[(var17 = this.quads[var16]).vertices.length];

            for(var4 = 0; var4 < var17.vertices.length; ++var4) {
               var19[var4] = var17.vertices[var17.vertices.length - var4 - 1];
            }

            var17.vertices = var19;
         }
      }

   }

   public final void setPosition(float var1, float var2, float var3) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
   }

   public final void render(float var1) {
      if(this.render) {
         if(!this.hasList) {
            this.generateList(var1);
         }

         if(this.pitch == 0.0F && this.yaw == 0.0F && this.roll == 0.0F) {
            if(this.x == 0.0F && this.y == 0.0F && this.z == 0.0F) {
               GL11.glCallList(this.list);
            } else {
               GL11.glTranslatef(this.x * var1, this.y * var1, this.z * var1);
               GL11.glCallList(this.list);
               GL11.glTranslatef(-this.x * var1, -this.y * var1, -this.z * var1);
            }
         } else {
            GL11.glPushMatrix();
            GL11.glTranslatef(this.x * var1, this.y * var1, this.z * var1);
            if(this.roll != 0.0F) {
               GL11.glRotatef(this.roll * 57.295776F, 0.0F, 0.0F, 1.0F);
            }

            if(this.yaw != 0.0F) {
               GL11.glRotatef(this.yaw * 57.295776F, 0.0F, 1.0F, 0.0F);
            }

            if(this.pitch != 0.0F) {
               GL11.glRotatef(this.pitch * 57.295776F, 1.0F, 0.0F, 0.0F);
            }

            GL11.glCallList(this.list);
            GL11.glPopMatrix();
         }
      }
   }

   public void generateList(float var1) {
      this.list = GL11.glGenLists(1);
      GL11.glNewList(this.list, 4864);
      GL11.glBegin(7);

      for(int var2 = 0; var2 < this.quads.length; ++var2) {
         TexturedQuad var10000 = this.quads[var2];
         float var3 = var1;
         TexturedQuad var4 = var10000;
         Vec3D var5 = var10000.vertices[1].vector.subtract(var4.vertices[0].vector).normalize();
         Vec3D var6 = var4.vertices[1].vector.subtract(var4.vertices[2].vector).normalize();
         GL11.glNormal3f((var5 = (new Vec3D(var5.y * var6.z - var5.z * var6.y, var5.z * var6.x - var5.x * var6.z, var5.x * var6.y - var5.y * var6.x)).normalize()).x, var5.y, var5.z);

         for(int var7 = 0; var7 < 4; ++var7) {
            Vertex var8;
            GL11.glTexCoord2f((var8 = var4.vertices[var7]).u, var8.v);
            GL11.glVertex3f(var8.vector.x * var3, var8.vector.y * var3, var8.vector.z * var3);
         }
      }

      GL11.glEnd();
      GL11.glEndList();
      this.hasList = true;
   }
}
