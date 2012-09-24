package com.mojang.minecraft;

import com.mojang.minecraft.player.Player;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

public class SkinDownloadThread extends Thread
{
	public SkinDownloadThread(Minecraft minecraft)
	{
		super();

		this.minecraft = minecraft;
	}

	@Override
	public void run()
	{
		if(minecraft.session != null)
		{
			HttpURLConnection connection = null;

			try {
				connection = (HttpURLConnection)new URL("http://www.minecraft.net/skin/" + minecraft.session.username + ".png").openConnection();

				connection.setDoInput(true);
				connection.setDoOutput(false);

				connection.connect();

				if(connection.getResponseCode() != 404)
				{
					Player.newTexture = ImageIO.read(connection.getInputStream());

					return;
				}
			} catch (Exception var4) {
				var4.printStackTrace();
			} finally {
				if(connection != null)
				{
					connection.disconnect();
				}
			}

		}
	}

	private Minecraft minecraft;
}
