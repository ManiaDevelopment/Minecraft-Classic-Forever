package com.mojang.minecraft.player;

import com.mojang.minecraft.mob.ai.BasicAI;
import com.mojang.minecraft.player.Player;

// PlayerAI
public class Player$1 extends BasicAI
{
	public Player$1(Player player)
	{
		this.player = player;
	}

	@Override
	protected void update()
	{
		this.jumping = player.input.yya;

		this.xxa = player.input.xxa;
		this.yya = player.input.jumping;
	}

	public static final long serialVersionUID = 0L;

	private Player player;
}
