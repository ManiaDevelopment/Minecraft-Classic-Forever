package com.mojang.minecraft.level;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.level.BlockMap$Slot;
import com.mojang.minecraft.level.SyntheticClass;
import com.mojang.minecraft.model.Vec3D;
import com.mojang.minecraft.phys.AABB;
import com.mojang.minecraft.render.Frustrum;
import com.mojang.minecraft.render.TextureManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BlockMap implements Serializable {

   public static final long serialVersionUID = 0L;
   private int width;
   private int depth;
   private int height;
   private BlockMap$Slot slot = new BlockMap$Slot(this, (SyntheticClass)null);
   private BlockMap$Slot slot2 = new BlockMap$Slot(this, (SyntheticClass)null);
   public List[] entityGrid;
   public List all = new ArrayList();
   private List tmp = new ArrayList();


   public BlockMap(int var1, int var2, int var3) {
      this.width = var1 / 16;
      this.depth = var2 / 16;
      this.height = var3 / 16;
      if(this.width == 0) {
         this.width = 1;
      }

      if(this.depth == 0) {
         this.depth = 1;
      }

      if(this.height == 0) {
         this.height = 1;
      }

      this.entityGrid = new ArrayList[this.width * this.depth * this.height];

      for(var1 = 0; var1 < this.width; ++var1) {
         for(var2 = 0; var2 < this.depth; ++var2) {
            for(var3 = 0; var3 < this.height; ++var3) {
               this.entityGrid[(var3 * this.depth + var2) * this.width + var1] = new ArrayList();
            }
         }
      }

   }

   public void insert(Entity var1) {
      this.all.add(var1);
      this.slot.init(var1.x, var1.y, var1.z).add(var1);
      var1.xOld = var1.x;
      var1.yOld = var1.y;
      var1.zOld = var1.z;
      var1.blockMap = this;
   }

   public void remove(Entity var1) {
      this.slot.init(var1.xOld, var1.yOld, var1.zOld).remove(var1);
      this.all.remove(var1);
   }

   public void moved(Entity var1) {
      BlockMap$Slot var2 = this.slot.init(var1.xOld, var1.yOld, var1.zOld);
      BlockMap$Slot var3 = this.slot2.init(var1.x, var1.y, var1.z);
      if(!var2.equals(var3)) {
         var2.remove(var1);
         var3.add(var1);
         var1.xOld = var1.x;
         var1.yOld = var1.y;
         var1.zOld = var1.z;
      }
   }

   public List getEntities(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.tmp.clear();
      return this.getEntities(var1, var2, var3, var4, var5, var6, var7, this.tmp);
   }

   public List getEntities(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7, List var8) {
      BlockMap$Slot var9 = this.slot.init(var2, var3, var4);
      BlockMap$Slot var10 = this.slot2.init(var5, var6, var7);

      for(int var11 = BlockMap$Slot.getXSlot(var9) - 1; var11 <= BlockMap$Slot.getXSlot(var10) + 1; ++var11) {
         for(int var12 = BlockMap$Slot.getYSlot(var9) - 1; var12 <= BlockMap$Slot.getYSlot(var10) + 1; ++var12) {
            for(int var13 = BlockMap$Slot.getZSlot(var9) - 1; var13 <= BlockMap$Slot.getZSlot(var10) + 1; ++var13) {
               if(var11 >= 0 && var12 >= 0 && var13 >= 0 && var11 < this.width && var12 < this.depth && var13 < this.height) {
                  List var14 = this.entityGrid[(var13 * this.depth + var12) * this.width + var11];

                  for(int var15 = 0; var15 < var14.size(); ++var15) {
                     Entity var16;
                     if((var16 = (Entity)var14.get(var15)) != var1 && var16.intersects(var2, var3, var4, var5, var6, var7)) {
                        var8.add(var16);
                     }
                  }
               }
            }
         }
      }

      return var8;
   }

   public void removeAllNonCreativeModeEntities() {
      for(int var1 = 0; var1 < this.width; ++var1) {
         for(int var2 = 0; var2 < this.depth; ++var2) {
            for(int var3 = 0; var3 < this.height; ++var3) {
               List var4 = this.entityGrid[(var3 * this.depth + var2) * this.width + var1];

               for(int var5 = 0; var5 < var4.size(); ++var5) {
                  if(!((Entity)var4.get(var5)).isCreativeModeAllowed()) {
                     var4.remove(var5--);
                  }
               }
            }
         }
      }

   }

   public void clear() {
      for(int var1 = 0; var1 < this.width; ++var1) {
         for(int var2 = 0; var2 < this.depth; ++var2) {
            for(int var3 = 0; var3 < this.height; ++var3) {
               this.entityGrid[(var3 * this.depth + var2) * this.width + var1].clear();
            }
         }
      }

   }

   public List getEntities(Entity var1, AABB var2) {
      this.tmp.clear();
      return this.getEntities(var1, var2.x0, var2.y0, var2.z0, var2.x1, var2.y1, var2.z1, this.tmp);
   }

   public List getEntities(Entity var1, AABB var2, List var3) {
      return this.getEntities(var1, var2.x0, var2.y0, var2.z0, var2.x1, var2.y1, var2.z1, var3);
   }

   public void tickAll() {
      for(int var1 = 0; var1 < this.all.size(); ++var1) {
         Entity var2;
         (var2 = (Entity)this.all.get(var1)).tick();
         if(var2.removed) {
            this.all.remove(var1--);
            this.slot.init(var2.xOld, var2.yOld, var2.zOld).remove(var2);
         } else {
            int var3 = (int)(var2.xOld / 16.0F);
            int var4 = (int)(var2.yOld / 16.0F);
            int var5 = (int)(var2.zOld / 16.0F);
            int var6 = (int)(var2.x / 16.0F);
            int var7 = (int)(var2.y / 16.0F);
            int var8 = (int)(var2.z / 16.0F);
            if(var3 != var6 || var4 != var7 || var5 != var8) {
               this.moved(var2);
            }
         }
      }

   }

   public void render(Vec3D var1, Frustrum var2, TextureManager var3, float var4) {
      for(int var5 = 0; var5 < this.width; ++var5) {
         float var6 = (float)((var5 << 4) - 2);
         float var7 = (float)((var5 + 1 << 4) + 2);

         for(int var8 = 0; var8 < this.depth; ++var8) {
            float var9 = (float)((var8 << 4) - 2);
            float var10 = (float)((var8 + 1 << 4) + 2);

            for(int var11 = 0; var11 < this.height; ++var11) {
               List var12;
               if((var12 = this.entityGrid[(var11 * this.depth + var8) * this.width + var5]).size() != 0) {
                  float var13 = (float)((var11 << 4) - 2);
                  float var14 = (float)((var11 + 1 << 4) + 2);
                  if(var2.isBoxInFrustrum(var6, var9, var13, var7, var10, var14)) {
                     float var16 = var14;
                     float var17 = var10;
                     float var15 = var7;
                     var14 = var13;
                     var13 = var9;
                     float var18 = var6;
                     Frustrum var19 = var2;
                     int var20 = 0;

                     boolean var10000;
                     while(true) {
                        if(var20 >= 6) {
                           var10000 = true;
                           break;
                        }

                        if(var19.frustrum[var20][0] * var18 + var19.frustrum[var20][1] * var13 + var19.frustrum[var20][2] * var14 + var19.frustrum[var20][3] <= 0.0F) {
                           var10000 = false;
                           break;
                        }

                        if(var19.frustrum[var20][0] * var15 + var19.frustrum[var20][1] * var13 + var19.frustrum[var20][2] * var14 + var19.frustrum[var20][3] <= 0.0F) {
                           var10000 = false;
                           break;
                        }

                        if(var19.frustrum[var20][0] * var18 + var19.frustrum[var20][1] * var17 + var19.frustrum[var20][2] * var14 + var19.frustrum[var20][3] <= 0.0F) {
                           var10000 = false;
                           break;
                        }

                        if(var19.frustrum[var20][0] * var15 + var19.frustrum[var20][1] * var17 + var19.frustrum[var20][2] * var14 + var19.frustrum[var20][3] <= 0.0F) {
                           var10000 = false;
                           break;
                        }

                        if(var19.frustrum[var20][0] * var18 + var19.frustrum[var20][1] * var13 + var19.frustrum[var20][2] * var16 + var19.frustrum[var20][3] <= 0.0F) {
                           var10000 = false;
                           break;
                        }

                        if(var19.frustrum[var20][0] * var15 + var19.frustrum[var20][1] * var13 + var19.frustrum[var20][2] * var16 + var19.frustrum[var20][3] <= 0.0F) {
                           var10000 = false;
                           break;
                        }

                        if(var19.frustrum[var20][0] * var18 + var19.frustrum[var20][1] * var17 + var19.frustrum[var20][2] * var16 + var19.frustrum[var20][3] <= 0.0F) {
                           var10000 = false;
                           break;
                        }

                        if(var19.frustrum[var20][0] * var15 + var19.frustrum[var20][1] * var17 + var19.frustrum[var20][2] * var16 + var19.frustrum[var20][3] <= 0.0F) {
                           var10000 = false;
                           break;
                        }

                        ++var20;
                     }

                     boolean var21 = var10000;

                     for(int var23 = 0; var23 < var12.size(); ++var23) {
                        Entity var22;
                        if((var22 = (Entity)var12.get(var23)).shouldRender(var1)) {
                           if(!var21) {
                              AABB var24 = var22.bb;
                              if(!var2.isBoxInFrustrum(var24.x0, var24.y0, var24.z0, var24.x1, var24.y1, var24.z1)) {
                                 continue;
                              }
                           }

                           var22.render(var3, var4);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   // $FF: synthetic method
   static int getWidth(BlockMap var0) {
      return var0.width;
   }

   // $FF: synthetic method
   static int getDepth(BlockMap var0) {
      return var0.depth;
   }

   // $FF: synthetic method
   static int getHeight(BlockMap var0) {
      return var0.height;
   }
}
