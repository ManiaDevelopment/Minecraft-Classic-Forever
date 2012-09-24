package com.mojang.minecraft.level.generator.noise;

import java.util.Random;

public class PerlinNoise extends Noise
{
	public PerlinNoise()
	{
		this(new Random());
	}

	public PerlinNoise(Random random)
	{
		noise = new int[512];

		for(int count = 0; count < 256; noise[count] = count++)
		{
		}

		for(int count = 0; count < 256; count++)
		{
			int unknown0 = random.nextInt(256 - count) + count;
			int unknown1 = noise[count];

			noise[count] = noise[unknown0];
			noise[unknown0] = unknown1;
			noise[count + 256] = noise[count];
		}

	}

	@Override
	public double compute(double x, double z)
	{
		double unknown0 = 0.0D;
		double unknown1 = z;
		double unknown2 = x;

		int unknown3 = (int)Math.floor(x) & 255;
		int unknown4 = (int)Math.floor(z) & 255;
		int unknown5 = (int)Math.floor(0.0D) & 255;

		unknown2 -= Math.floor(unknown2);
		unknown1 -= Math.floor(unknown1);
		unknown0 = 0.0D - Math.floor(0.0D);

		double unknown6 = a(unknown2);
		double unknown7 = a(unknown1);
		double unknown8 = a(unknown0);

		int unknown9 = noise[unknown3] + unknown4;
		int unknown10 = noise[unknown9] + unknown5;

		unknown9 = noise[unknown9 + 1] + unknown5;
		unknown3 = noise[unknown3 + 1] + unknown4;
		unknown4 = noise[unknown3] + unknown5;
		unknown3 = noise[unknown3 + 1] + unknown5;

		// TODO: Maybe organize better.
		return lerp(
				unknown8,
				lerp(
						unknown7,
						lerp(
								unknown6,
								grad(
										noise[unknown10],
										unknown2,
										unknown1,
										unknown0),
								grad(
										noise[unknown4],
										unknown2 - 1.0D,
										unknown1,
										unknown0)),
						lerp(
								unknown6,
								grad(
										noise[unknown9],
										unknown2,
										unknown1 - 1.0D,
										unknown0),
								grad(
										noise[unknown3],
										unknown2 - 1.0D,
										unknown1 - 1.0D,
										unknown0))),
				lerp(
						unknown7,
						lerp(
								unknown6,
								grad(
										noise[unknown10 + 1],
										unknown2,
										unknown1,
										unknown0 - 1.0D),
								grad(
										noise[unknown4 + 1],
										unknown2 - 1.0D,
										unknown1,
										unknown0 - 1.0D)),
						lerp(
								unknown6,
								grad(
										noise[unknown9 + 1],
										unknown2,
										unknown1 - 1.0D,
										unknown0 - 1.0D),
								grad(
										noise[unknown3 + 1],
										unknown2 - 1.0D,
										unknown1 - 1.0D,
										unknown0 - 1.0D))));
	}

	private int[] noise;

	private static double a(double unknown0)
	{
		return unknown0 * unknown0 * unknown0 * (unknown0 * (unknown0 * 6.0D - 15.0D) + 10.0D);
	}

	private static double lerp(double unknown0, double unknown1, double unknown2)
	{
		return unknown1 + unknown0 * (unknown2 - unknown1);
	}

	private static double grad(int unknown0, double unknown1, double unknown2, double unknown3)
	{
		double unknown4 = (unknown0 &= 15) < 8 ? unknown1 : unknown2;
		double unknown5 = unknown0 < 4 ? unknown2 : (unknown0 != 12 && unknown0 != 14 ? unknown3 : unknown1);

		return ((unknown0 & 1) == 0 ? unknown4 : -unknown4) + ((unknown0 & 2) == 0 ? unknown5 : -unknown5);
	}
}
