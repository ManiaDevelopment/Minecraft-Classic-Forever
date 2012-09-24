package com.mojang.minecraft.render.texture;

public class TextureFX
{
	public TextureFX(int textureID)
	{
		this.textureId = textureID;
	}

	public byte[] textureData = new byte[1024];
	public int textureId;
	public boolean anaglyph = false;

	public void animate()
	{
	}
}
