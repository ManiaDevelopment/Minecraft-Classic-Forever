package com.mojang.minecraft;

public class ChatLine
{
	public ChatLine(String message)
	{
		this.message = message;
		this.time = 0;
	}

	public String message;
	public int time;
}
