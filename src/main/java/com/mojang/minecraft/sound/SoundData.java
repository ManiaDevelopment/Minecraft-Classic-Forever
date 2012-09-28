package com.mojang.minecraft.sound;

public class SoundData
{
	public SoundData(short[] data, float length)
	{
		this.data = data;
		this.length = length;
	}

	public short[] data;
	public float length;
}
