package com.mojang.minecraft.particle;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.particle.Particle;
import com.mojang.minecraft.render.ShapeRenderer;

public class WaterDropParticle extends Particle {

   private static final long serialVersionUID = 1L;


   public WaterDropParticle(Level var1, float var2, float var3, float var4) {
      super(var1, var2, var3, var4, 0.0F, 0.0F, 0.0F);
      this.xd *= 0.3F;
      this.yd = (float)Math.random() * 0.2F + 0.1F;
      this.zd *= 0.3F;
      this.rCol = 1.0F;
      this.gCol = 1.0F;
      this.bCol = 1.0F;
      this.tex = 16;
      this.setSize(0.01F, 0.01F);
      this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
   }

   public void render(ShapeRenderer var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super.render(var1, var2, var3, var4, var5, var6, var7);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.yd = (float)((double)this.yd - 0.06D);
      this.move(this.xd, this.yd, this.zd);
      this.xd *= 0.98F;
      this.yd *= 0.98F;
      this.zd *= 0.98F;
      if(this.lifetime-- <= 0) {
         this.remove();
      }

      if(this.onGround) {
         if(Math.random() < 0.5D) {
            this.remove();
         }

         this.xd *= 0.7F;
         this.zd *= 0.7F;
      }

   }
}
