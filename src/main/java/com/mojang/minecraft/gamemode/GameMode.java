package com.mojang.minecraft.gamemode;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.level.tile.Tile$SoundType;
import com.mojang.minecraft.player.Player;

public class GameMode
{
	public GameMode(Minecraft minecraft)
	{
		this.minecraft = minecraft;

		instantBreak = false;
	}

	public Minecraft minecraft;

	public boolean instantBreak;

	public void apply(Level level)
	{
		level.creativeMode = false;
		level.growTrees = true;
	}

	public void openInventory()
	{
	}

	public void hitBlock(int x, int y, int z)
	{
		this.breakBlock(x, y, z);
	}

	public boolean canPlace(int block)
	{
		return true;
	}

	public void breakBlock(int x, int y, int z)
	{
		Level level = minecraft.level;
		Block block = Block.blocks[level.getTile(x, y, z)];

		boolean success = level.netSetTile(x, y, z, 0);

		if(block != null && success)
		{
			if(minecraft.isOnline())
			{
				minecraft.networkManager.sendBlockChange(x, y, z, 0, minecraft.player.inventory.getSelected());
			}

			if(block.stepsound != Tile$SoundType.none)
			{
				level.playSound("step." + block.stepsound.name, (float)x, (float)y, (float)z, (block.stepsound.getVolume() + 1.0F) / 2.0F, block.stepsound.getPitch() * 0.8F);
			}

			block.spawnBreakParticles(level, x, y, z, minecraft.particleManager);
		}

	}

	public void hitBlock(int x, int y, int z, int side)
	{
	}

	public void resetHits()
	{
	}

	public void applyCracks(float time)
	{
	}

	public float getReachDistance()
	{
		return 5.0F;
	}

	public boolean useItem(Player player, int type)
	{
		return false;
	}

	public void preparePlayer(Player player)
	{
	}

	public void spawnMob()
	{
	}

	public void prepareLevel(Level level)
	{
	}

	public boolean isSurvival()
	{
		return true;
	}

	public void apply(Player player)
	{
	}
}
