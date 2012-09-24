package com.mojang.minecraft.model;

import com.mojang.minecraft.model.AnimalModel;
import com.mojang.minecraft.model.ModelPart;

public final class SheepModel extends AnimalModel {

   public SheepModel() {
      super(12, 0.0F);
      this.head = new ModelPart(0, 0);
      this.head.setBounds(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
      this.head.setPosition(0.0F, 6.0F, -8.0F);
      this.body = new ModelPart(28, 8);
      this.body.setBounds(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
      this.body.setPosition(0.0F, 5.0F, 2.0F);
   }
}
