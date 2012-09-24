package com.mojang.minecraft.model;

import com.mojang.minecraft.model.Model;
import com.mojang.minecraft.model.ModelPart;
import com.mojang.util.MathHelper;

public class HumanoidModel extends Model {

   public ModelPart head;
   public ModelPart headwear;
   public ModelPart body;
   public ModelPart rightArm;
   public ModelPart leftArm;
   public ModelPart rightLeg;
   public ModelPart leftLeg;


   public HumanoidModel() {
      this(0.0F);
   }

   public HumanoidModel(float var1) {
      this.head = new ModelPart(0, 0);
      this.head.setBounds(-4.0F, -8.0F, -4.0F, 8, 8, 8, var1);
      this.headwear = new ModelPart(32, 0);
      this.headwear.setBounds(-4.0F, -8.0F, -4.0F, 8, 8, 8, var1 + 0.5F);
      this.body = new ModelPart(16, 16);
      this.body.setBounds(-4.0F, 0.0F, -2.0F, 8, 12, 4, var1);
      this.rightArm = new ModelPart(40, 16);
      this.rightArm.setBounds(-3.0F, -2.0F, -2.0F, 4, 12, 4, var1);
      this.rightArm.setPosition(-5.0F, 2.0F, 0.0F);
      this.leftArm = new ModelPart(40, 16);
      this.leftArm.mirror = true;
      this.leftArm.setBounds(-1.0F, -2.0F, -2.0F, 4, 12, 4, var1);
      this.leftArm.setPosition(5.0F, 2.0F, 0.0F);
      this.rightLeg = new ModelPart(0, 16);
      this.rightLeg.setBounds(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1);
      this.rightLeg.setPosition(-2.0F, 12.0F, 0.0F);
      this.leftLeg = new ModelPart(0, 16);
      this.leftLeg.mirror = true;
      this.leftLeg.setBounds(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1);
      this.leftLeg.setPosition(2.0F, 12.0F, 0.0F);
   }

   public final void render(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.setRotationAngles(var1, var2, var3, var4, var5, var6);
      this.head.render(var6);
      this.body.render(var6);
      this.rightArm.render(var6);
      this.leftArm.render(var6);
      this.rightLeg.render(var6);
      this.leftLeg.render(var6);
   }

   public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.yaw = var4 / 57.295776F;
      this.head.pitch = var5 / 57.295776F;
      this.rightArm.pitch = MathHelper.cos(var1 * 0.6662F + 3.1415927F) * 2.0F * var2;
      this.rightArm.roll = (MathHelper.cos(var1 * 0.2312F) + 1.0F) * var2;
      this.leftArm.pitch = MathHelper.cos(var1 * 0.6662F) * 2.0F * var2;
      this.leftArm.roll = (MathHelper.cos(var1 * 0.2812F) - 1.0F) * var2;
      this.rightLeg.pitch = MathHelper.cos(var1 * 0.6662F) * 1.4F * var2;
      this.leftLeg.pitch = MathHelper.cos(var1 * 0.6662F + 3.1415927F) * 1.4F * var2;
      this.rightArm.roll += MathHelper.cos(var3 * 0.09F) * 0.05F + 0.05F;
      this.leftArm.roll -= MathHelper.cos(var3 * 0.09F) * 0.05F + 0.05F;
      this.rightArm.pitch += MathHelper.sin(var3 * 0.067F) * 0.05F;
      this.leftArm.pitch -= MathHelper.sin(var3 * 0.067F) * 0.05F;
   }
}
