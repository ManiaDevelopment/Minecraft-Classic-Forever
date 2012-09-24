package com.mojang.minecraft.net;

public class PositionUpdate
{
	public PositionUpdate(float x, float y, float z, float yaw, float pitch)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;

		rotation = true;
		position = true;
	}

	public PositionUpdate(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;

		position = true;
		rotation = false;
	}

	public PositionUpdate(float yaw, float pitch)
	{
		this.yaw = yaw;
		this.pitch = pitch;

		rotation = true;
		position = false;
	}

	public float x;
	public float y;
	public float z;

	public float yaw;
	public float pitch;

	public boolean rotation = false;
	public boolean position = false;
}
