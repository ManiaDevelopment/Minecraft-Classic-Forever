package com.mojang.minecraft.particle;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.particle.Particle;
import com.mojang.minecraft.render.ShapeRenderer;

public class SmokeParticle extends Particle {

   private static final long serialVersionUID = 1L;


   public SmokeParticle(Level var1, float var2, float var3, float var4) {
      super(var1, var2, var3, var4, 0.0F, 0.0F, 0.0F);
      this.xd *= 0.1F;
      this.yd *= 0.1F;
      this.zd *= 0.1F;
      this.rCol = this.gCol = this.bCol = (float)(Math.random() * 0.30000001192092896D);
      this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
      this.noPhysics = true;
   }

   public void render(ShapeRenderer var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super.render(var1, var2, var3, var4, var5, var6, var7);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if(this.age++ >= this.lifetime) {
         this.remove();
      }

      this.tex = 7 - (this.age << 3) / this.lifetime;
      this.yd = (float)((double)this.yd + 0.004D);
      this.move(this.xd, this.yd, this.zd);
      this.xd *= 0.96F;
      this.yd *= 0.96F;
      this.zd *= 0.96F;
      if(this.onGround) {
         this.xd *= 0.7F;
         this.zd *= 0.7F;
      }

   }
}
