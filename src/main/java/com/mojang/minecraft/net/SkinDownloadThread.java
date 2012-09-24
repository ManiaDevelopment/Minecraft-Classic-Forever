package com.mojang.minecraft.net;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

public class SkinDownloadThread extends Thread
{
	public SkinDownloadThread(NetworkPlayer networkPlayer)
	{
		super();

		this.player = networkPlayer;
	}

	@Override
	public void run()
	{
		HttpURLConnection connection = null;

		try {
			connection = (HttpURLConnection)new URL("http://www.minecraft.net/skin/" + player.name + ".png").openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(false);

			connection.connect();

			if(connection.getResponseCode() == 404)
			{
				return;
			}

			player.newTexture = ImageIO.read(connection.getInputStream());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null)
			{
				connection.disconnect();
			}
		}
	}

	private NetworkPlayer player;
}
