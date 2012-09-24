package com.mojang.minecraft.model;

import com.mojang.minecraft.model.Model;
import com.mojang.minecraft.model.ModelPart;
import com.mojang.util.MathHelper;

public final class CreeperModel extends Model {

   private ModelPart head = new ModelPart(0, 0);
   private ModelPart unused;
   private ModelPart body;
   private ModelPart leg1;
   private ModelPart leg2;
   private ModelPart leg3;
   private ModelPart leg4;


   public CreeperModel() {
      this.head.setBounds(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
      this.unused = new ModelPart(32, 0);
      this.unused.setBounds(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F + 0.5F);
      this.body = new ModelPart(16, 16);
      this.body.setBounds(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
      this.leg1 = new ModelPart(0, 16);
      this.leg1.setBounds(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
      this.leg1.setPosition(-2.0F, 12.0F, 4.0F);
      this.leg2 = new ModelPart(0, 16);
      this.leg2.setBounds(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
      this.leg2.setPosition(2.0F, 12.0F, 4.0F);
      this.leg3 = new ModelPart(0, 16);
      this.leg3.setBounds(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
      this.leg3.setPosition(-2.0F, 12.0F, -4.0F);
      this.leg4 = new ModelPart(0, 16);
      this.leg4.setBounds(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
      this.leg4.setPosition(2.0F, 12.0F, -4.0F);
   }

   public final void render(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.yaw = var4 / 57.295776F;
      this.head.pitch = var5 / 57.295776F;
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
