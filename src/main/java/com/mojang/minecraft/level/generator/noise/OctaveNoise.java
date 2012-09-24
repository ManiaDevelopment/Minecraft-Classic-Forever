package com.mojang.minecraft.level.generator.noise;

import java.util.Random;

public class OctaveNoise extends Noise
{
	public OctaveNoise(Random random, int octaves)
	{
		this.octaves = octaves;
		perlin = new PerlinNoise[octaves];

		for(int count = 0; count < octaves; count++)
		{
			perlin[count] = new PerlinNoise(random);
		}

	}

	@Override
	public double compute(double x, double z)
	{
		double result = 0.0D;
		double unknown0 = 1.0D;

		for(int count = 0; count < octaves; count++)
		{
			result += perlin[count].compute(x / unknown0, z / unknown0) * unknown0;

			unknown0 *= 2.0D;
		}

		return result;
	}

	private PerlinNoise[] perlin;
	private int octaves;
}
