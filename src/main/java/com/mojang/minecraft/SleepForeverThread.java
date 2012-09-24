package com.mojang.minecraft;

public class SleepForeverThread extends Thread
{
	public SleepForeverThread(Minecraft minecraft)
	{
		setDaemon(true);

		start();
	}

	@Override
	public void run()
	{
		while(true)
		{
			try {
				while(true)
				{
					Thread.sleep(2147483647L);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
