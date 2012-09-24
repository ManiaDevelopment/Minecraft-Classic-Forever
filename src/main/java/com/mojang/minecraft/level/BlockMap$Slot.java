package com.mojang.minecraft.level;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.level.BlockMap;
import com.mojang.minecraft.level.SyntheticClass;
import java.io.Serializable;

class BlockMap$Slot implements Serializable {

   public static final long serialVersionUID = 0L;
   private int xSlot;
   private int ySlot;
   private int zSlot;
   // $FF: synthetic field
   final BlockMap blockMap;


   private BlockMap$Slot(BlockMap var1) {
      this.blockMap = var1;
   }

   public BlockMap$Slot init(float var1, float var2, float var3) {
      this.xSlot = (int)(var1 / 16.0F);
      this.ySlot = (int)(var2 / 16.0F);
      this.zSlot = (int)(var3 / 16.0F);
      if(this.xSlot < 0) {
         this.xSlot = 0;
      }

      if(this.ySlot < 0) {
         this.ySlot = 0;
      }

      if(this.zSlot < 0) {
         this.zSlot = 0;
      }

      if(this.xSlot >= BlockMap.getWidth(this.blockMap)) {
         this.xSlot = BlockMap.getWidth(this.blockMap) - 1;
      }

      if(this.ySlot >= BlockMap.getDepth(this.blockMap)) {
         this.ySlot = BlockMap.getDepth(this.blockMap) - 1;
      }

      if(this.zSlot >= BlockMap.getHeight(this.blockMap)) {
         this.zSlot = BlockMap.getHeight(this.blockMap) - 1;
      }

      return this;
   }

   public void add(Entity var1) {
      if(this.xSlot >= 0 && this.ySlot >= 0 && this.zSlot >= 0) {
         this.blockMap.entityGrid[(this.zSlot * BlockMap.getDepth(this.blockMap) + this.ySlot) * BlockMap.getWidth(this.blockMap) + this.xSlot].add(var1);
      }

   }

   public void remove(Entity var1) {
      if(this.xSlot >= 0 && this.ySlot >= 0 && this.zSlot >= 0) {
         this.blockMap.entityGrid[(this.zSlot * BlockMap.getDepth(this.blockMap) + this.ySlot) * BlockMap.getWidth(this.blockMap) + this.xSlot].remove(var1);
      }

   }

   // $FF: synthetic method
   BlockMap$Slot(BlockMap var1, SyntheticClass var2) {
      this(var1);
   }

   // $FF: synthetic method
   static int getXSlot(BlockMap$Slot var0) {
      return var0.xSlot;
   }

   // $FF: synthetic method
   static int getYSlot(BlockMap$Slot var0) {
      return var0.ySlot;
   }

   // $FF: synthetic method
   static int getZSlot(BlockMap$Slot var0) {
      return var0.zSlot;
   }
}
