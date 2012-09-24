package org.oyasunadev.minecraft.util.net.cookie;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.*;

public final class CookieList extends CookieHandler
{
	@Override
	public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException
	{
		StringBuilder cookies = new StringBuilder();
		Object object = cookieJar.iterator();

		do
		{
			if(!((Iterator) object).hasNext())
			{
				break;
			}

			Cookie cookie = (Cookie)((Iterator) object).next();
			if(cookie.hasExpired())
			{
				((Iterator) object).remove();
			} else if (cookie.matches(uri)) {
				if(cookies.length() > 0)
				{
					cookies.append(", ");
				}

				cookies.append(cookie.toString());
			}
		} while(true);

		object = new HashMap(requestHeaders);

		if(cookies.length() > 0)
		{
			List<String> list = Collections.singletonList(cookies.toString());

			((Map) object).put("Cookie", list);
		}

		System.out.println("CookieMap: " + object);

		return Collections.unmodifiableMap((Map) object);
	}

	@Override
	public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException
	{
		List<String> setCookieList = responseHeaders.get("Set-Cookie");

		if(setCookieList != null)
		{
			for(final String item : setCookieList)
			{
				Cookie cookie = new Cookie(uri, item);
				for(Cookie existingCookie : cookieJar)
				{
					if((cookie.getUri().equals(existingCookie.getUri())) && (cookie.getName().equals(existingCookie.getName())))
					{
						cookieJar.remove(existingCookie);

						break;
					}
				}

				cookieJar.add(cookie);
			}
		}
	}

	private final List<Cookie> cookieJar = new LinkedList<Cookie>();
}
