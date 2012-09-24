package com.mojang.minecraft.render;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.level.tile.Block;

public class HeldBlock
{
	public HeldBlock(Minecraft minecraft)
	{
		this.minecraft = minecraft;
	}

	public Minecraft minecraft;

	public Block block = null;

	public float pos = 0.0F;
	public float lastPos = 0.0F;

	public int offset = 0;

	public boolean moving = false;
}
