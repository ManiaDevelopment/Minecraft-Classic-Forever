package com.mojang.minecraft.level;


public final class NextTickListEntry {

   public int x;
   public int y;
   public int z;
   public int block;
   public int ticks;


   public NextTickListEntry(int var1, int var2, int var3, int var4) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
      this.block = var4;
   }
}
