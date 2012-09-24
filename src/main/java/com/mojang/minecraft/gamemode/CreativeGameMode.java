package com.mojang.minecraft.gamemode;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.SessionData;
import com.mojang.minecraft.gui.BlockSelectScreen;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.player.Player;

public class CreativeGameMode extends GameMode
{
	public CreativeGameMode(Minecraft minecraft)
	{
		super(minecraft);

		instantBreak = true;
	}

	@Override
	public void apply(Level level)
	{
		super.apply(level);

		level.removeAllNonCreativeModeEntities();

		level.creativeMode = true;
		level.growTrees = false;
	}

	@Override
	public void openInventory()
	{
		BlockSelectScreen blockSelectScreen = new BlockSelectScreen();

		minecraft.setCurrentScreen(blockSelectScreen);
	}

	@Override
	public boolean isSurvival()
	{
		return false;
	}

	@Override
	public void apply(Player player)
	{
		for(int slot = 0; slot < 9; slot++)
		{
			player.inventory.count[slot] = 1;

			if(player.inventory.slots[slot] <= 0)
			{
				player.inventory.slots[slot] = ((Block)SessionData.allowedBlocks.get(slot)).id;
			}
		}

	}
}
