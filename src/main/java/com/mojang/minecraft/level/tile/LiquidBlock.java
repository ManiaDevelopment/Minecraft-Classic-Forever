package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.LiquidType;
import com.mojang.minecraft.render.ShapeRenderer;

import java.util.Random;

public class LiquidBlock extends Block {

   protected LiquidType type;
   protected int stillId;
   protected int movingId;


   protected LiquidBlock(int var1, LiquidType var2) {
      super(var1);
      this.type = var2;
      this.textureId = 14;
      if(var2 == LiquidType.LAVA) {
         this.textureId = 30;
      }

      Block.liquid[var1] = true;
      this.movingId = var1;
      this.stillId = var1 + 1;
      float var4 = 0.01F;
      float var3 = 0.1F;
      this.setBounds(var4 + 0.0F, 0.0F - var3 + var4, var4 + 0.0F, var4 + 1.0F, 1.0F - var3 + var4, var4 + 1.0F);
      this.setPhysics(true);
      if(var2 == LiquidType.LAVA) {
         this.setTickDelay(16);
      }

   }

   public final boolean isCube() {
      return false;
   }

   public final void onPlace(Level var1, int var2, int var3, int var4) {
      var1.addToTickNextTick(var2, var3, var4, this.movingId);
   }

   public void update(Level var1, int var2, int var3, int var4, Random var5) {
      boolean var7 = false;
      var4 = var4;
      var3 = var3;
      var2 = var2;
      var1 = var1;
      boolean var8 = false;

      boolean var6;
      do {
         --var3;
         if(var1.getTile(var2, var3, var4) != 0 || !this.canFlow(var1, var2, var3, var4)) {
            break;
         }

         if(var6 = var1.setTile(var2, var3, var4, this.movingId)) {
            var8 = true;
         }
      } while(var6 && this.type != LiquidType.LAVA);

      ++var3;
      if(this.type == LiquidType.WATER || !var8) {
         var8 = var8 | this.flow(var1, var2 - 1, var3, var4) | this.flow(var1, var2 + 1, var3, var4) | this.flow(var1, var2, var3, var4 - 1) | this.flow(var1, var2, var3, var4 + 1);
      }

      if(!var8) {
         var1.setTileNoUpdate(var2, var3, var4, this.stillId);
      } else {
         var1.addToTickNextTick(var2, var3, var4, this.movingId);
      }

   }

   private boolean canFlow(Level var1, int var2, int var3, int var4) {
      if(this.type == LiquidType.WATER) {
         for(int var7 = var2 - 2; var7 <= var2 + 2; ++var7) {
            for(int var5 = var3 - 2; var5 <= var3 + 2; ++var5) {
               for(int var6 = var4 - 2; var6 <= var4 + 2; ++var6) {
                  if(var1.getTile(var7, var5, var6) == Block.SPONGE.id) {
                     return false;
                  }
               }
            }
         }
      }

      return true;
   }

   private boolean flow(Level var1, int var2, int var3, int var4) {
      if(var1.getTile(var2, var3, var4) == 0) {
         if(!this.canFlow(var1, var2, var3, var4)) {
            return false;
         }

         if(var1.setTile(var2, var3, var4, this.movingId)) {
            var1.addToTickNextTick(var2, var3, var4, this.movingId);
         }
      }

      return false;
   }

   protected final float getBrightness(Level var1, int var2, int var3, int var4) {
      return this.type == LiquidType.LAVA?100.0F:var1.getBrightness(var2, var3, var4);
   }

   public final boolean canRenderSide(Level var1, int var2, int var3, int var4, int var5) {
      int var6;
      return var2 >= 0 && var3 >= 0 && var4 >= 0 && var2 < var1.width && var4 < var1.height?((var6 = var1.getTile(var2, var3, var4)) != this.movingId && var6 != this.stillId?(var5 == 1 && (var1.getTile(var2 - 1, var3, var4) == 0 || var1.getTile(var2 + 1, var3, var4) == 0 || var1.getTile(var2, var3, var4 - 1) == 0 || var1.getTile(var2, var3, var4 + 1) == 0)?true:super.canRenderSide(var1, var2, var3, var4, var5)):false):false;
   }

   public final void renderInside(ShapeRenderer var1, int var2, int var3, int var4, int var5) {
      super.renderInside(var1, var2, var3, var4, var5);
      super.renderSide(var1, var2, var3, var4, var5);
   }

   public final boolean isOpaque() {
      return true;
   }

   public final boolean isSolid() {
      return false;
   }

   public final LiquidType getLiquidType() {
      return this.type;
   }

   public void onNeighborChange(Level var1, int var2, int var3, int var4, int var5) {
      if(var5 != 0) {
         LiquidType var6 = Block.blocks[var5].getLiquidType();
         if(this.type == LiquidType.WATER && var6 == LiquidType.LAVA || var6 == LiquidType.WATER && this.type == LiquidType.LAVA) {
            var1.setTile(var2, var3, var4, Block.STONE.id);
            return;
         }
      }

      var1.addToTickNextTick(var2, var3, var4, var5);
   }

   public final int getTickDelay() {
      return this.type == LiquidType.LAVA?5:0;
   }

   public final void dropItems(Level var1, int var2, int var3, int var4, float var5) {}

   public final void onBreak(Level var1, int var2, int var3, int var4) {}

   public final int getDropCount() {
      return 0;
   }

   public final int getRenderPass() {
      return this.type == LiquidType.WATER?1:0;
   }
}
