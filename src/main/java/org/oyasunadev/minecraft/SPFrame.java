package org.oyasunadev.minecraft;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.MinecraftApplet$1;

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
		setLayout(new BorderLayout());
	}

	public void startMinecraft()
	{
		MCApplet applet = new MCApplet();
		MinecraftApplet$1 canvas = new MinecraftApplet$1(applet);

		Minecraft minecraft = new Minecraft(canvas, applet, getWidth(), getHeight(), false);

		canvas.setSize(getWidth(), getHeight());

		add(canvas, "Center");

		pack();

		new Thread(minecraft).start();
	}

	public void finish()
	{
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2);
	}
}
