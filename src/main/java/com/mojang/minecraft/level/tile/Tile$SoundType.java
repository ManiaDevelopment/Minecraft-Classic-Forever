package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.tile.Block;

public enum Tile$SoundType {

   none("none", 0, "-", 0.0F, 0.0F),
   grass("grass", 1, "grass", 0.6F, 1.0F),
   cloth("cloth", 2, "grass", 0.7F, 1.2F),
   gravel("gravel", 3, "gravel", 1.0F, 1.0F),
   stone("stone", 4, "stone", 1.0F, 1.0F),
   metal("metal", 5, "stone", 1.0F, 2.0F),
   wood("wood", 6, "wood", 1.0F, 1.0F);
   public final String name;
   private final float volume;
   private final float pitch;
   // $FF: synthetic field
   private static final Tile$SoundType[] values = new Tile$SoundType[]{none, grass, cloth, gravel, stone, metal, wood};


   private Tile$SoundType(String var1, int var2, String var3, float var4, float var5) {
      this.name = var3;
      this.volume = var4;
      this.pitch = var5;
   }

   public final float getVolume() {
      return this.volume / (Block.random.nextFloat() * 0.4F + 1.0F) * 0.5F;
   }

   public final float getPitch() {
      return this.pitch / (Block.random.nextFloat() * 0.2F + 0.9F);
   }

}
