package com.mojang.minecraft;

public enum Minecraft$OS
{
	linux("linux", 0),
	solaris("solaris", 1),
	windows("windows", 2),
	macos("macos", 3),
	unknown("unknown", 4);

	private static final Minecraft$OS[] values = new Minecraft$OS[] {linux, solaris, windows, macos, unknown};

	private Minecraft$OS(String name, int id)
	{
	}
}
