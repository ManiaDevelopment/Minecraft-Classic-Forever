package com.mojang.minecraft.model;

import com.mojang.minecraft.model.Model;
import com.mojang.minecraft.model.ModelPart;
import com.mojang.util.MathHelper;

public class AnimalModel extends Model {

   public ModelPart head = new ModelPart(0, 0);
   public ModelPart body;
   public ModelPart leg1;
   public ModelPart leg2;
   public ModelPart leg3;
   public ModelPart leg4;


   public AnimalModel(int var1, float var2) {
      this.head.setBounds(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
      this.head.setPosition(0.0F, (float)(18 - var1), -6.0F);
      this.body = new ModelPart(28, 8);
      this.body.setBounds(-5.0F, -10.0F, -7.0F, 10, 16, 8, 0.0F);
      this.body.setPosition(0.0F, (float)(17 - var1), 2.0F);
      this.leg1 = new ModelPart(0, 16);
      this.leg1.setBounds(-2.0F, 0.0F, -2.0F, 4, var1, 4, 0.0F);
      this.leg1.setPosition(-3.0F, (float)(24 - var1), 7.0F);
      this.leg2 = new ModelPart(0, 16);
      this.leg2.setBounds(-2.0F, 0.0F, -2.0F, 4, var1, 4, 0.0F);
      this.leg2.setPosition(3.0F, (float)(24 - var1), 7.0F);
      this.leg3 = new ModelPart(0, 16);
      this.leg3.setBounds(-2.0F, 0.0F, -2.0F, 4, var1, 4, 0.0F);
      this.leg3.setPosition(-3.0F, (float)(24 - var1), -5.0F);
      this.leg4 = new ModelPart(0, 16);
      this.leg4.setBounds(-2.0F, 0.0F, -2.0F, 4, var1, 4, 0.0F);
      this.leg4.setPosition(3.0F, (float)(24 - var1), -5.0F);
   }

   public final void render(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.yaw = var4 / 57.295776F;
      this.head.pitch = var5 / 57.295776F;
      this.body.pitch = 1.5707964F;
      this.leg1.pitch = MathHelper.cos(var1 * 0.6662F) * 1.4F * var2;
      this.leg2.pitch = MathHelper.cos(var1 * 0.6662F + 3.1415927F) * 1.4F * var2;
      this.leg3.pitch = MathHelper.cos(var1 * 0.6662F + 3.1415927F) * 1.4F * var2;
      this.leg4.pitch = MathHelper.cos(var1 * 0.6662F) * 1.4F * var2;
      this.head.render(var6);
      this.body.render(var6);
      this.leg1.render(var6);
      this.leg2.render(var6);
      this.leg3.render(var6);
      this.leg4.render(var6);
   }
}
