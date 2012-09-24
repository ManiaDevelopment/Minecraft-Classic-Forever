package com.mojang.minecraft.level.generator.noise;

public final class CombinedNoise extends Noise
{
	public CombinedNoise(Noise noise1, Noise noise2)
	{
		this.noise1 = noise1;
		this.noise2 = noise2;
	}

	@Override
	public double compute(double x, double z)
	{
		return noise1.compute(x + noise2.compute(x, z), z);
	}

	private Noise noise1;
	private Noise noise2;
}
