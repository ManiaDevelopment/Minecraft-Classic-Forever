package com.mojang.minecraft.model;

import com.mojang.minecraft.model.Model;
import com.mojang.minecraft.model.ModelPart;
import com.mojang.util.MathHelper;

public final class SpiderModel extends Model {

   private ModelPart head = new ModelPart(32, 4);
   private ModelPart neck;
   private ModelPart body;
   private ModelPart leg1;
   private ModelPart leg2;
   private ModelPart leg3;
   private ModelPart leg4;
   private ModelPart leg5;
   private ModelPart leg6;
   private ModelPart leg7;
   private ModelPart leg8;


   public SpiderModel() {
      this.head.setBounds(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
      this.head.setPosition(0.0F, 0.0F, -3.0F);
      this.neck = new ModelPart(0, 0);
      this.neck.setBounds(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
      this.body = new ModelPart(0, 12);
      this.body.setBounds(-5.0F, -4.0F, -6.0F, 10, 8, 12, 0.0F);
      this.body.setPosition(0.0F, 0.0F, 9.0F);
      this.leg1 = new ModelPart(18, 0);
      this.leg1.setBounds(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.leg1.setPosition(-4.0F, 0.0F, 2.0F);
      this.leg2 = new ModelPart(18, 0);
      this.leg2.setBounds(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.leg2.setPosition(4.0F, 0.0F, 2.0F);
      this.leg3 = new ModelPart(18, 0);
      this.leg3.setBounds(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.leg3.setPosition(-4.0F, 0.0F, 1.0F);
      this.leg4 = new ModelPart(18, 0);
      this.leg4.setBounds(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.leg4.setPosition(4.0F, 0.0F, 1.0F);
      this.leg5 = new ModelPart(18, 0);
      this.leg5.setBounds(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.leg5.setPosition(-4.0F, 0.0F, 0.0F);
      this.leg6 = new ModelPart(18, 0);
      this.leg6.setBounds(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.leg6.setPosition(4.0F, 0.0F, 0.0F);
      this.leg7 = new ModelPart(18, 0);
      this.leg7.setBounds(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.leg7.setPosition(-4.0F, 0.0F, -1.0F);
      this.leg8 = new ModelPart(18, 0);
      this.leg8.setBounds(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.leg8.setPosition(4.0F, 0.0F, -1.0F);
   }

   public final void render(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.yaw = var4 / 57.295776F;
      this.head.pitch = var5 / 57.295776F;
      var4 = 0.7853982F;
      this.leg1.roll = -var4;
      this.leg2.roll = var4;
      this.leg3.roll = -var4 * 0.74F;
      this.leg4.roll = var4 * 0.74F;
      this.leg5.roll = -var4 * 0.74F;
      this.leg6.roll = var4 * 0.74F;
      this.leg7.roll = -var4;
      this.leg8.roll = var4;
      var4 = 0.3926991F;
      this.leg1.yaw = var4 * 2.0F;
      this.leg2.yaw = -var4 * 2.0F;
      this.leg3.yaw = var4;
      this.leg4.yaw = -var4;
      this.leg5.yaw = -var4;
      this.leg6.yaw = var4;
      this.leg7.yaw = -var4 * 2.0F;
      this.leg8.yaw = var4 * 2.0F;
      var4 = -(MathHelper.cos(var1 * 0.6662F * 2.0F) * 0.4F) * var2;
      var5 = -(MathHelper.cos(var1 * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * var2;
      float var7 = -(MathHelper.cos(var1 * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * var2;
      float var8 = -(MathHelper.cos(var1 * 0.6662F * 2.0F + 4.712389F) * 0.4F) * var2;
      float var9 = Math.abs(MathHelper.sin(var1 * 0.6662F) * 0.4F) * var2;
      float var10 = Math.abs(MathHelper.sin(var1 * 0.6662F + 3.1415927F) * 0.4F) * var2;
      float var11 = Math.abs(MathHelper.sin(var1 * 0.6662F + 1.5707964F) * 0.4F) * var2;
      var2 = Math.abs(MathHelper.sin(var1 * 0.6662F + 4.712389F) * 0.4F) * var2;
      this.leg1.yaw += var4;
      this.leg2.yaw -= var4;
      this.leg3.yaw += var5;
      this.leg4.yaw -= var5;
      this.leg5.yaw += var7;
      this.leg6.yaw -= var7;
      this.leg7.yaw += var8;
      this.leg8.yaw -= var8;
      this.leg1.roll += var9;
      this.leg2.roll -= var9;
      this.leg3.roll += var10;
      this.leg4.roll -= var10;
      this.leg5.roll += var11;
      this.leg6.roll -= var11;
      this.leg7.roll += var2;
      this.leg8.roll -= var2;
      this.head.render(var6);
      this.neck.render(var6);
      this.body.render(var6);
      this.leg1.render(var6);
      this.leg2.render(var6);
      this.leg3.render(var6);
      this.leg4.render(var6);
      this.leg5.render(var6);
      this.leg6.render(var6);
      this.leg7.render(var6);
      this.leg8.render(var6);
   }
}
