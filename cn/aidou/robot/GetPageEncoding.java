package cn.aidou.robot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPageEncoding {

	String link = null;
	URL url = null;
	HttpURLConnection urlConnection;
	BufferedReader reader = null;
	BufferedWriter writer = null;

	/**
	 * 2015.10.8 获取网页编码方式
	 * 
	 * @param link
	 * @return
	 */

	public String getCharset(String link) {
		String result = null;
		try {
			url = new URL(link);
			// 打开url链接
			urlConnection = (HttpURLConnection) url.openConnection();
			// User-Agent头域的内容包含发出请求的用户信息
			urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
			urlConnection.connect();
			String contentType = urlConnection.getContentType();
			// 在header里面找charset
			result = findCharset(contentType);
			// 如果没找到的话，则一行一行的读入页面的html代码，从html代码中寻找
			if (result == null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String line = reader.readLine();
				while (line != null) {
					if (line.contains("Content-Type")) {
						result = findCharset(line);
						break;
					}
					line = reader.readLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			urlConnection.disconnect();
		}
		return result;
	}

	/**
	 * 2015.10.8 辅助函数
	 * 
	 * @param line
	 * @return
	 */
	private String findCharset(String line) {
		System.out.println(line);
		int x = line.indexOf("charset=");
		int y = line.lastIndexOf('\"');
		if (x < 0)
			return null;
		else if (y >= 0)
			return line.substring(x + 8, y);
		else
			return line.substring(x + 8);
	}
}
