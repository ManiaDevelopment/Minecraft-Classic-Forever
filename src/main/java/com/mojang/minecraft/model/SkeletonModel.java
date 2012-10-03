package com.mojang.minecraft.model;

import com.mojang.minecraft.model.ModelPart;
import com.mojang.minecraft.model.ZombieModel;

public final class SkeletonModel extends ZombieModel {

   public SkeletonModel() {
      this.rightArm = new ModelPart(40, 16);
      this.rightArm.setBounds(-1.0F, -2.0F, -1.0F, 2, 12, 2, 0.0F);
      this.rightArm.setPosition(-5.0F, 2.0F, 0.0F);
      this.leftArm = new ModelPart(40, 16);
      this.leftArm.mirror = true;
      this.leftArm.setBounds(-1.0F, -2.0F, -1.0F, 2, 12, 2, 0.0F);
      this.leftArm.setPosition(5.0F, 2.0F, 0.0F);
      this.rightLeg = new ModelPart(0, 16);
      this.rightLeg.setBounds(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
      this.rightLeg.setPosition(-2.0F, 12.0F, 0.0F);
      this.leftLeg = new ModelPart(0, 16);
      this.leftLeg.mirror = true;
      this.leftLeg.setBounds(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
      this.leftLeg.setPosition(2.0F, 12.0F, 0.0F);
   }
}
