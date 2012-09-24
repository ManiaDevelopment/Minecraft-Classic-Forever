package com.mojang.minecraft;

import com.mojang.minecraft.level.tile.Block;
import java.util.ArrayList;
import java.util.List;

public final class SessionData {

   public static List allowedBlocks;
   public String username;
   public String sessionId;
   public String mppass;
   public boolean haspaid;


   public SessionData(String var1, String var2) {
      this.username = var1;
      this.sessionId = var2;
   }

   static {
      (allowedBlocks = new ArrayList()).add(Block.STONE);
      allowedBlocks.add(Block.COBBLESTONE);
      allowedBlocks.add(Block.BRICK);
      allowedBlocks.add(Block.DIRT);
      allowedBlocks.add(Block.WOOD);
      allowedBlocks.add(Block.LOG);
      allowedBlocks.add(Block.LEAVES);
      allowedBlocks.add(Block.GLASS);
      allowedBlocks.add(Block.SLAB);
      allowedBlocks.add(Block.MOSSY_COBBLESTONE);
      allowedBlocks.add(Block.SAPLING);
      allowedBlocks.add(Block.DANDELION);
      allowedBlocks.add(Block.ROSE);
      allowedBlocks.add(Block.BROWN_MUSHROOM);
      allowedBlocks.add(Block.RED_MUSHROOM);
      allowedBlocks.add(Block.SAND);
      allowedBlocks.add(Block.GRAVEL);
      allowedBlocks.add(Block.SPONGE);
      allowedBlocks.add(Block.RED_WOOL);
      allowedBlocks.add(Block.ORANGE_WOOL);
      allowedBlocks.add(Block.YELLOW_WOOL);
      allowedBlocks.add(Block.LIME_WOOL);
      allowedBlocks.add(Block.GREEN_WOOL);
      allowedBlocks.add(Block.AQUA_GREEN_WOOL);
      allowedBlocks.add(Block.CYAN_WOOL);
      allowedBlocks.add(Block.BLUE_WOOL);
      allowedBlocks.add(Block.PURPLE_WOOL);
      allowedBlocks.add(Block.INDIGO_WOOL);
      allowedBlocks.add(Block.VIOLET_WOOL);
      allowedBlocks.add(Block.MAGENTA_WOOL);
      allowedBlocks.add(Block.PINK_WOOL);
      allowedBlocks.add(Block.BLACK_WOOL);
      allowedBlocks.add(Block.GRAY_WOOL);
      allowedBlocks.add(Block.WHITE_WOOL);
      allowedBlocks.add(Block.COAL_ORE);
      allowedBlocks.add(Block.IRON_ORE);
      allowedBlocks.add(Block.GOLD_ORE);
      allowedBlocks.add(Block.IRON_BLOCK);
      allowedBlocks.add(Block.GOLD_BLOCK);
      allowedBlocks.add(Block.BOOKSHELF);
      allowedBlocks.add(Block.TNT);
      allowedBlocks.add(Block.OBSIDIAN);
   }
}
