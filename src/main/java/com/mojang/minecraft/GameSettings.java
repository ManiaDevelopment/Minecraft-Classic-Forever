package com.mojang.minecraft;

import com.mojang.minecraft.render.TextureManager;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.imageio.ImageIO;
import org.lwjgl.input.Keyboard;

public final class GameSettings
{
	public GameSettings(Minecraft minecraft, File minecraftFolder)
	{
		bindings = new KeyBinding[] {forwardKey, leftKey, backKey, rightKey, jumpKey, buildKey, chatKey, toggleFogKey, saveLocationKey, loadLocationKey};

		settingCount = 8;

		this.minecraft = minecraft;

		settingsFile = new File(minecraftFolder, "options.txt");

		load();
	}

	private static final String[] renderDistances = new String[]{"FAR", "NORMAL", "SHORT", "TINY"};
	public boolean music = true;
	public boolean sound = true;
	public boolean invertMouse = false;
	public boolean showFrameRate = false;
	public int viewDistance = 0;
	public boolean viewBobbing = true;
	public boolean anaglyph = false;
	public boolean limitFramerate = false;
	public KeyBinding forwardKey = new KeyBinding("Forward", 17);
	public KeyBinding leftKey = new KeyBinding("Left", 30);
	public KeyBinding backKey = new KeyBinding("Back", 31);
	public KeyBinding rightKey = new KeyBinding("Right", 32);
	public KeyBinding jumpKey = new KeyBinding("Jump", 57);
	public KeyBinding buildKey = new KeyBinding("Build", 48);
	public KeyBinding chatKey = new KeyBinding("Chat", 20);
	public KeyBinding toggleFogKey = new KeyBinding("Toggle fog", 33);
	public KeyBinding saveLocationKey = new KeyBinding("Save location", 28);
	public KeyBinding loadLocationKey = new KeyBinding("Load location", 19);
	public KeyBinding[] bindings;
	private Minecraft minecraft;
	private File settingsFile;
	public int settingCount;

	public String getBinding(int key)
	{
		return bindings[key].name + ": " + Keyboard.getKeyName(bindings[key].key);
	}

	public void setBinding(int key, int keyID)
	{
		bindings[key].key = keyID;

		save();
	}

	public void toggleSetting(int setting, int fogValue)
	{
		if(setting == 0)
		{
			music = !music;
		}

		if(setting == 1)
		{
			sound = !sound;
		}

		if(setting == 2)
		{
			invertMouse = !invertMouse;
		}

		if(setting == 3)
		{
			showFrameRate = !showFrameRate;
		}

		if(setting == 4)
		{
			viewDistance = viewDistance + fogValue & 3;
		}

		if(setting == 5)
		{
			viewBobbing = !viewBobbing;
		}

		if(setting == 6)
		{
			anaglyph = !anaglyph;

			TextureManager textureManager = minecraft.textureManager;
			Iterator iterator = this.minecraft.textureManager.textureImages.keySet().iterator();

			int i;
			BufferedImage image;

			while(iterator.hasNext())
			{
				i = (Integer)iterator.next();
				image = (BufferedImage)textureManager.textureImages.get(Integer.valueOf(i));

				textureManager.load(image, i);
			}

			iterator = textureManager.textures.keySet().iterator();

			while(iterator.hasNext())
			{
				String s = (String)iterator.next();

				try
				{
					if(s.startsWith("##"))
					{
						image = TextureManager.load1(ImageIO.read(TextureManager.class.getResourceAsStream(s.substring(2))));
					} else {
						image = ImageIO.read(TextureManager.class.getResourceAsStream(s));
					}

					i = (Integer)textureManager.textures.get(s);

					textureManager.load(image, i);
				} catch (IOException var6) {
					var6.printStackTrace();
				}
			}
		}

		if(setting == 7)
		{
			limitFramerate = !limitFramerate;
		}

		save();
	}

	public String getSetting(int id)
	{
		return id == 0 ? "Music: " + (music ? "ON" : "OFF")
				: (id == 1 ? "Sound: " + (sound ? "ON" : "OFF")
				: (id == 2 ? "Invert mouse: " + (invertMouse ? "ON" : "OFF")
				: (id == 3 ? "Show FPS: " + (showFrameRate ? "ON" : "OFF")
				: (id == 4 ? "Render distance: " + renderDistances[viewDistance]
				: (id == 5 ? "View bobbing: " + (viewBobbing ? "ON" : "OFF")
				: (id == 6 ? "3d anaglyph: " + (anaglyph ? "ON" : "OFF")
				: (id == 7 ? "Limit framerate: " + (limitFramerate ? "ON" : "OFF")
				: "")))))));
	}

	private void load()
	{
		try
		{
			if(settingsFile.exists())
			{
				FileReader fileReader = new FileReader(settingsFile);
				BufferedReader reader = new BufferedReader(fileReader);

				String line = null;

				while((line = reader.readLine()) != null)
				{
					String[] setting = line.split(":");

					if(setting[0].equals("music"))
					{
						music = setting[1].equals("true");
					}

					if(setting[0].equals("sound"))
					{
						sound = setting[1].equals("true");
					}

					if(setting[0].equals("invertYMouse"))
					{
						invertMouse = setting[1].equals("true");
					}

					if(setting[0].equals("showFrameRate"))
					{
						showFrameRate = setting[1].equals("true");
					}

					if(setting[0].equals("viewDistance"))
					{
						viewDistance = Integer.parseInt(setting[1]);
					}

					if(setting[0].equals("bobView"))
					{
						viewBobbing = setting[1].equals("true");
					}

					if(setting[0].equals("anaglyph3d"))
					{
						anaglyph = setting[1].equals("true");
					}

					if(setting[0].equals("limitFramerate"))
					{
						limitFramerate = setting[1].equals("true");
					}

					for(int index = 0; index < this.bindings.length; index++)
					{
						if(setting[0].equals("key_" + bindings[index].name))
						{
							bindings[index].key = Integer.parseInt(setting[1]);
						}
					}
				}

				reader.close();
			}
		} catch (Exception e) {
			System.out.println("Failed to load options");

			e.printStackTrace();
		}
	}

	private void save()
	{
		try {
			FileWriter fileWriter = new FileWriter(this.settingsFile);
			PrintWriter writer = new PrintWriter(fileWriter);

			writer.println("music:" + music);
			writer.println("sound:" + sound);
			writer.println("invertYMouse:" + invertMouse);
			writer.println("showFrameRate:" + showFrameRate);
			writer.println("viewDistance:" + viewDistance);
			writer.println("bobView:" + viewBobbing);
			writer.println("anaglyph3d:" + anaglyph);
			writer.println("limitFramerate:" + limitFramerate);

			for(int binding = 0; binding < bindings.length; binding++)
			{
				writer.println("key_" + bindings[binding].name + ":" + bindings[binding].key);
			}

			writer.close();
		} catch (Exception e) {
			System.out.println("Failed to save options");

			e.printStackTrace();
		}
	}

}
