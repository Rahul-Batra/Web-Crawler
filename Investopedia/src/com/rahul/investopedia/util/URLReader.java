package com.rahul.investopedia.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

@SuppressWarnings("unused")
public class URLReader {
	public static String readURL(String url) {
		URL mapDataURL = null;
		URLConnection urlConnection;
		int responseCode = -1;
		String proxyAddress = "127.0.0.1";
		int proxyPort = 8081;
		StringBuffer buffer = new StringBuffer();
		try {
			mapDataURL = new URL(url);

			if (proxyAddress != null) {
				mapDataURL = new URL(mapDataURL.getProtocol(), proxyAddress,
						proxyPort, url);
			}

			if (mapDataURL != null) {
				urlConnection = mapDataURL.openConnection();
				urlConnection.setConnectTimeout(600000);

				if (urlConnection instanceof HttpURLConnection) {
					HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
					httpURLConnection.connect();
					responseCode = httpURLConnection.getResponseCode();

					if (responseCode == 200) {
						BufferedReader in = new BufferedReader(	new InputStreamReader(urlConnection.getInputStream(),"utf-8"));

						String inputLine;
						while ((inputLine = in.readLine()) != null) {
							buffer.append(inputLine);
						}
						in.close();
					}
					httpURLConnection.disconnect();
				}
			}
		} catch (MalformedURLException e) {
			buffer = new StringBuffer(e.getMessage());
		} catch (IOException e) {
			buffer = new StringBuffer(e.getMessage());
		} catch (Exception e) {
			buffer = new StringBuffer(e.getMessage());
		}

		return buffer.toString();
	}

}
