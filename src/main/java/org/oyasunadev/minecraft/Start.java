package org.oyasunadev.minecraft;

import org.oyasunadev.minecraft.util.net.cookie.CookieList;

import javax.swing.*;
import java.net.CookieHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Oliver Yasuna
 * Date: 9/23/12
 * Time: 4:04 PM
 */
public class Start
{
	public static void main(String[] args)
	{
		new Start();
	}

	public Start()
	{
		CookieList cookielist = new CookieList();
		CookieHandler.setDefault(cookielist);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		SPFrame spFrame = new SPFrame();

		spFrame.setVisible(true);

		try {
			spFrame.startMinecraft();
		} catch (Exception e) {
			e.printStackTrace();
		}
		spFrame.finish();
	}
}
