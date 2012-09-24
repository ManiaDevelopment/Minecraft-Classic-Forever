package com.mojang.minecraft.gamemode;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.MobSpawner;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.mob.Mob;
import com.mojang.minecraft.player.Player;

public final class SurvivalGameMode extends GameMode
{
	public SurvivalGameMode(Minecraft minecraft)
	{
		super(minecraft);
	}

	private int hitX;
	private int hitY;
	private int hitZ;
	private int hits;
	private int hardness;
	private int hitDelay;
	private MobSpawner spawner;

	@Override
	public void apply(Level level)
	{
		super.apply(level);

		spawner = new MobSpawner(level);
	}

	@Override
	public void hitBlock(int x, int y, int z, int side)
	{
		if(hitDelay > 0)
		{
			hitDelay--;
		} else if(x == hitX && y == hitY && z == hitZ) {
			int type = minecraft.level.getTile(x, y, z);

			if(type != 0)
			{
				Block block = Block.blocks[type];

				hardness = block.getHardness();

				block.spawnBlockParticles(minecraft.level, x, y, z, side, minecraft.particleManager);

				hits++;

				if(hits == hardness + 1)
				{
					breakBlock(x, y, z);

					hits = 0;
					hitDelay = 5;
				}

			}
		} else {
			// TODO: I think if I don't set hits to 0 you can continue breaking from where you left off.

			hits = 0;
			hitX = x;
			hitY = y;
			hitZ = z;
		}
	}

	@Override
	public boolean canPlace(int block)
	{
		return minecraft.player.inventory.removeResource(block);
	}

	@Override
	public void breakBlock(int x, int y, int z)
	{
		int block = minecraft.level.getTile(x, y, z);
		Block.blocks[block].onBreak(minecraft.level, x, y, z);

		super.breakBlock(x, y, z);
	}

	@Override
	public void hitBlock(int x, int y, int z)
	{
		int block = this.minecraft.level.getTile(x, y, z);

		if(block > 0 && Block.blocks[block].getHardness() == 0)
		{
			breakBlock(x, y, z);
		}
	}

	@Override
	public void resetHits()
	{
		this.hits = 0;
		this.hitDelay = 0;
	}

	@Override
	public void applyCracks(float time)
	{
		if(hits <= 0)
		{
			minecraft.levelRenderer.cracks = 0.0F;
		} else {
			minecraft.levelRenderer.cracks = ((float)hits + time - 1.0F) / (float)hardness;
		}
	}

	@Override
	public float getReachDistance()
	{
		return 4.0F;
	}

	@Override
	public boolean useItem(Player player, int type)
	{
		Block block = Block.blocks[type];
		if(block == Block.RED_MUSHROOM && minecraft.player.inventory.removeResource(type))
		{
			player.hurt(null, 3);

			return true;
		} else if(block == Block.BROWN_MUSHROOM && minecraft.player.inventory.removeResource(type)) {
			player.heal(5);

			return true;
		} else {
			return false;
		}
	}

	@Override
	public void preparePlayer(Player player)
	{
		player.inventory.slots[8] = Block.TNT.id;
		player.inventory.count[8] = 10;
	}

	@Override
	public void spawnMob()
	{
		int area = spawner.level.width * spawner.level.height * spawner.level.depth / 64 / 64 / 64;

		if(spawner.level.random.nextInt(100) < area && spawner.level.countInstanceOf(Mob.class) < area * 20)
		{
			spawner.spawn(area, spawner.level.player, null);
		}

	}

	@Override
	public void prepareLevel(Level level)
	{
		spawner = new MobSpawner(level);

		minecraft.progressBar.setText("Spawning..");

		int area = level.width * level.height * level.depth / 800;

		spawner.spawn(area, null, minecraft.progressBar);
	}
}
