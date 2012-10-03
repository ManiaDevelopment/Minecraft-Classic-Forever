package com.mojang.minecraft.model;

import com.mojang.minecraft.model.AnimalModel;
import com.mojang.minecraft.model.ModelPart;

public final class SheepFurModel extends AnimalModel {

   public SheepFurModel() {
      super(12, 0.0F);
      this.head = new ModelPart(0, 0);
      this.head.setBounds(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
      this.head.setPosition(0.0F, 6.0F, -8.0F);
      this.body = new ModelPart(28, 8);
      this.body.setBounds(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
      this.body.setPosition(0.0F, 5.0F, 2.0F);
      float var1 = 0.5F;
      this.leg1 = new ModelPart(0, 16);
      this.leg1.setBounds(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.leg1.setPosition(-3.0F, 12.0F, 7.0F);
      this.leg2 = new ModelPart(0, 16);
      this.leg2.setBounds(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.leg2.setPosition(3.0F, 12.0F, 7.0F);
      this.leg3 = new ModelPart(0, 16);
      this.leg3.setBounds(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.leg3.setPosition(-3.0F, 12.0F, -5.0F);
      this.leg4 = new ModelPart(0, 16);
      this.leg4.setBounds(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.leg4.setPosition(3.0F, 12.0F, -5.0F);
   }
}
