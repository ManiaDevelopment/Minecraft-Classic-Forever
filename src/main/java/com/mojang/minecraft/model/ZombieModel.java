package com.mojang.minecraft.model;

import com.mojang.minecraft.model.HumanoidModel;
import com.mojang.util.MathHelper;

public class ZombieModel extends HumanoidModel {

   public final void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
      super.setRotationAngles(var1, var2, var3, var4, var5, var6);
      var1 = MathHelper.sin(this.attackOffset * 3.1415927F);
      var2 = MathHelper.sin((1.0F - (1.0F - this.attackOffset) * (1.0F - this.attackOffset)) * 3.1415927F);
      this.rightArm.roll = 0.0F;
      this.leftArm.roll = 0.0F;
      this.rightArm.yaw = -(0.1F - var1 * 0.6F);
      this.leftArm.yaw = 0.1F - var1 * 0.6F;
      this.rightArm.pitch = -1.5707964F;
      this.leftArm.pitch = -1.5707964F;
      this.rightArm.pitch -= var1 * 1.2F - var2 * 0.4F;
      this.leftArm.pitch -= var1 * 1.2F - var2 * 0.4F;
      this.rightArm.roll += MathHelper.cos(var3 * 0.09F) * 0.05F + 0.05F;
      this.leftArm.roll -= MathHelper.cos(var3 * 0.09F) * 0.05F + 0.05F;
      this.rightArm.pitch += MathHelper.sin(var3 * 0.067F) * 0.05F;
      this.leftArm.pitch -= MathHelper.sin(var3 * 0.067F) * 0.05F;
   }
}
