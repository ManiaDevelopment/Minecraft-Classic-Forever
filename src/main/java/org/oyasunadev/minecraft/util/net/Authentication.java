package org.oyasunadev.minecraft.util.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public final class Authentication
{
	public Authentication(String urlStr, String sep, String params) throws Exception
	{
		url = new URL(urlStr + sep + params);
		connection = (HttpURLConnection)url.openConnection();

		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
	}

	private final URL url;
	private final HttpURLConnection connection;

	public void connect(boolean net) throws Exception
	{
		connection.connect();
	}

	public String[] getOutput() throws Exception
	{
		Collection<String> result = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		try {
			String line;
			while((line = in.readLine()) != null)
			{
				result.add(line);
			}

			return result.toArray(new String[result.size()]);
		}

		finally
		{
			in.close();
		}
	}
}
