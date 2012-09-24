package com.mojang.minecraft;

import com.mojang.minecraft.model.Vec3D;

public class MovingObjectPosition
{
	public MovingObjectPosition(int x, int y, int z, int side, Vec3D blockPos)
	{
		entityPos = 0;

		this.x = x;
		this.y = y;
		this.z = z;

		this.face = side;

		vec = new Vec3D(blockPos.x, blockPos.y, blockPos.z);
	}

	public MovingObjectPosition(Entity entity)
	{
		entityPos = 1;
		this.entity = entity;
	}

	public int entityPos;

	public int x;
	public int y;
	public int z;

	public int face;

	public Vec3D vec;

	public Entity entity;
}
