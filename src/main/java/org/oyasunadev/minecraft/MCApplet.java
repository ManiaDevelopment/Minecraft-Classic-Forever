package org.oyasunadev.minecraft;

import com.mojang.minecraft.MinecraftApplet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Oliver Yasuna
 * Date: 9/23/12
 * Time: 4:17 PM
 */
public class MCApplet extends MinecraftApplet
{
	public MCApplet()
	{
		MP = false;

		parameters = new HashMap();
	}

	public MCApplet(String username, String sessionid, boolean haspaid, String server, int port, String mppass)
	{
		MP = true;

		parameters = new HashMap();

		parameters.put("username", username);
		parameters.put("sessionid", sessionid);
		parameters.put("haspaid", String.valueOf(haspaid));
		parameters.put("server", server);
		parameters.put("port", String.valueOf(port));
		parameters.put("mppass", mppass);
	}

	@Override
	public URL getDocumentBase()
	{
		try {
			return new URL("http://minecraft.net:80/play.jsp");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public URL getCodeBase()
	{
		try {
			return new URL("http://minecraft.net:80/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getParameter(String name)
	{
		if(MP)
		{
			return (String)parameters.get(name);
		} else {
			return super.getParameter(name);
		}
	}

	private final boolean MP;
	public final Map parameters;
}
