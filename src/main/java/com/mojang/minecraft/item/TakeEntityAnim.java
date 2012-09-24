package com.mojang.minecraft.item;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.render.TextureManager;

public class TakeEntityAnim extends Entity
{
	public TakeEntityAnim(Level level1, Entity item, Entity player)
	{
		super(level1);

		this.item = item;
		this.player = player;

		setSize(1.0F, 1.0F);

		xorg = item.x;
		yorg = item.y;
		zorg = item.z;
	}

	@Override
	public void tick()
	{
		time++;

		if(time >= 3)
		{
			remove();
		}

		// TODO: Is this right?
		float distance = (distance = (float)time / 3.0F) * distance;

		xo = item.xo = item.x;
		yo = item.yo = item.y;
		zo = item.zo = item.z;

		x = item.x = xorg + (player.x - xorg) * distance;
		y = item.y = yorg + (player.y - 1.0F - yorg) * distance;
		z = item.z = zorg + (player.z - zorg) * distance;

		setPos(x, y, z);
	}

	@Override
	public void render(TextureManager textureManager, float unknown0)
	{
		item.render(textureManager, unknown0);
	}

	private static final long serialVersionUID = 1L;

	private int time = 0;

	private Entity item;
	private Entity player;

	private float xorg;
	private float yorg;
	private float zorg;
}
