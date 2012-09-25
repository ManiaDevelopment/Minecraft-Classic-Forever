package org.oyasunadev.minecraft;

import com.mojang.minecraft.Minecraft;

import javax.swing.*;
import java.awt.*;

public final class SPFrame extends JFrame
{
	public SPFrame()
	{
		setTitle("MinecraftMania - Single Player");
		setSize(854, 480);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private Minecraft minecraft;

	public void startMinecraft()
	{
		MCApplet mcApplet = new MCApplet();
		Canvas canvas = new Canvas();

		mcApplet.setBounds(0, 0, getWidth(), getHeight());

		minecraft = new Minecraft(canvas, mcApplet, getWidth(), getHeight(), false);

		add(canvas);

		new Thread(minecraft).start();
	}

	public void finish()
	{
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2);
	}
}
