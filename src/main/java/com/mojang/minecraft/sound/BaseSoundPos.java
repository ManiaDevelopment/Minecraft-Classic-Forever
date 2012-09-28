package com.mojang.minecraft.sound;

import com.mojang.minecraft.Entity;
import com.mojang.util.MathHelper;

public abstract class BaseSoundPos implements SoundPos
{
	public BaseSoundPos(Entity listener)
	{
		this.listener = listener;
	}

	private Entity listener;

	public float getRotationDiff(float x, float y)
	{
		x -= listener.x;
		y -= listener.z;

		float var3 = MathHelper.sqrt(x * x + y * y);

		x /= var3;
		y /= var3;

		if((var3 /= 2.0F) > 1.0F)
		{
			var3 = 1.0F;
		}

		float var4 = MathHelper.cos(-listener.yRot * 0.017453292F + 3.1415927F);

		return (MathHelper.sin(-listener.yRot * 0.017453292F + 3.1415927F) * y - var4 * x) * var3;
	}

	public float getDistanceSq(float x, float y, float z)
	{
		x -= listener.x;
		y -= listener.y;
		float var4 = z - listener.z;

		var4 = MathHelper.sqrt(x * x + y * y + var4 * var4);

		if((var4 = 1.0F - var4 / 32.0F) < 0.0F)
		{
			var4 = 0.0F;
		}

		return var4;
	}
}
