package com.mojang.minecraft;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ResourceDownloadThread extends Thread
{
	public ResourceDownloadThread(File minecraftFolder, Minecraft minecraft)
	{
		this.minecraft = minecraft;

		this.setName("Resource download thread");
		this.setDaemon(true);

		dir = new File(minecraftFolder, "resources/");

		if(!dir.exists() && !dir.mkdirs())
		{
			throw new RuntimeException("The working directory could not be created: " + dir);
		}
	}

	@Override
	public void run()
	{
		BufferedReader reader = null;

		try {
			ArrayList<String> list = new ArrayList<String>();

			URL base = new URL("http://dl.dropbox.com/u/40737374/minecraft_resources/");
			URL url = new URL(base, "resources/");

			URLConnection con = url.openConnection();

			con.setConnectTimeout(20000);

			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String line = null;

			while((line = reader.readLine()) != null)
			{
				list.add(line);
			}

			reader.close();

			/*for(String s : list)
			{
				try {
					String split[] = s.split(",");
					int size = Integer.parseInt(split[1]);
					File file = new File(dir, split[0]);

					if(!file.exists() || file.length() != size)
					{
						try {
							file.getParentFile().mkdirs();
						} catch(SecurityException e) {
							e.printStackTrace();
						}

						URL url1 = new URL(base, split[0].replaceAll(" ", "%20"));

						download(url1, file, size);
					} else {
						int index = split[0].indexOf("/");

						if(split[0].substring(0, index).equalsIgnoreCase("sound"))
						{
							minecraft.sound.registerSound(file, split[0].substring(index + 1, split[0].length() - 4).replaceAll("[1-9]", "").replaceAll("/", "."));
							//this.mc.audio.registerSound(split[0].substring(index + 1, split[0].length() - 4).replaceAll("[1-9]", "").replaceAll("/", "."), file.toURI().toURL(), true);
						} else if (split[0].substring(0, index).equalsIgnoreCase("music")) {
							if(split[0].contains("sweden"))
							{
								//this.mc.audio.registerMusic("menu", file.toURI().toURL(), true);
							}

							//this.mc.audio.registerMusic("bg", file.toURI().toURL(), true);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (!this.running) return;
			}*/

			for(String s : list)
			{
				String split[] = s.split(",");
				int size = Integer.parseInt(split[1]);
				File file = new File(dir, split[0]);

				File musicFolder = new File(dir, "music");

				if(!file.exists() || file.length() != size)
				{
					try {
						file.getParentFile().mkdirs();
					} catch(SecurityException e) {
						e.printStackTrace();
					}

					URL url1 = new URL(base, split[0].replaceAll(" ", "%20"));

					if(file.getPath().contains("music"))
					{
						if(file.getName().equals("minecraft.ogg") && !new File(musicFolder, "calm1.ogg").exists())
						{
							download(url1, file, size);
						} else if(file.getName().equals("clark.ogg") && !new File(musicFolder, "calm2.ogg").exists()) {
							download(url1, file, size);
						} else if(file.getName().equals("sweden.ogg") && !new File(musicFolder, "calm3.ogg").exists()) {
							download(url1, file, size);
						}
					} else {
						download(url1, file, size);
					}
				}

				File minecraftOGG = new File(musicFolder, "minecraft.ogg");
				File clarkOGG = new File(musicFolder, "clark.ogg");
				File swedenOGG = new File(musicFolder, "sweden.ogg");

				minecraftOGG.renameTo(new File(musicFolder, "calm1.ogg"));
				clarkOGG.renameTo(new File(musicFolder, "calm2.ogg"));
				swedenOGG.renameTo(new File(musicFolder, "calm3.ogg"));
			}

			File soundsFolder = new File(dir, "sound");
			File stepsFolder = new File(soundsFolder, "step");

			for(int i = 1; i <= 4; i++)
			{
				minecraft.sound.registerSound(new File(stepsFolder, "grass" + i + ".ogg"), "step/grass" + i + ".ogg");
				minecraft.sound.registerSound(new File(stepsFolder, "gravel" + i + ".ogg"), "step/gravel" + i + ".ogg");
				minecraft.sound.registerSound(new File(stepsFolder, "stone" + i + ".ogg"), "step/stone" + i + ".ogg");
				minecraft.sound.registerSound(new File(stepsFolder, "wood" + i + ".ogg"), "step/wood" + i + ".ogg");
			}

			File musicFolder = new File(dir, "music");

			for(int i = 1; i <= 3; i++)
			{
				minecraft.sound.registerMusic("calm" + i + ".ogg", new File(musicFolder, "calm" + i + ".ogg"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.finished = true;
	}

	private File dir;
	private Minecraft minecraft;
	boolean running = false;

	private boolean finished = false;
	private int progress = 0;

	private void download(URL url, File file, int size)
	{
		System.out.println("Downloading: " + file.getName() + "...");

		//minecraft.progressBar.setText(file.getName());

		DataInputStream in = null;
		DataOutputStream out = null;

		try
		{
			byte[] data = new byte[4096];

			in = new DataInputStream(url.openStream());
			FileOutputStream fos = new FileOutputStream(file);
			out = new DataOutputStream(fos);

			int done = 0;

			do
			{
				int length = in.read(data);

				if(length < 0)
				{
					in.close();
					out.close();

					return;
				}

				out.write(data, 0, length);

				done += length;
				progress = (int)(((double)done / (double)size) * 100);
			} while(!running);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try
			{
				if(in != null)
				{
					in.close();
				}

				if(out != null)
				{
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//minecraft.progressBar.setText("");
		progress = 0;

		System.out.println("Downloaded: " + file.getName());
	}

	public boolean isFinished()
	{
		return finished;
	}

	public int getProgress()
	{
		return progress;
	}
}
