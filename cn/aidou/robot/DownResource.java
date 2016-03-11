package cn.aidou.robot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 2015.10.8 下载网页源码
 */

public class DownResource implements IDownResource {
	String link = null;
	URL url = null;
	HttpURLConnection urlConnection;
	BufferedReader reader = null;
	BufferedWriter writer = null;

	/**
	 * 下载网页源代码
	 */
	public void downPageSourceHandler(String link) {
		int responsecode;
		String line;
		try {
			url = new URL("http://" + link);
			// 打开url链接
			urlConnection = (HttpURLConnection) url.openConnection();
			reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			Document doc = Jsoup.connect("http://" + link).get();
			String title = doc.title();
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File("D:\\" + title + ".html"))));
			// 生成一个url对象，要获取源代码的网页地址为：http://www.sina.com.cn
			// 获取返回的状态码
			responsecode = urlConnection.getResponseCode();
			if (responsecode == 200) {
				while ((line = reader.readLine()) != null) {
//					System.out.println(line);
					writer.write(line);
					writer.newLine();
				}
				System.out.println("下载成功！");
			} else {
				System.out.println("获取不到网页的源码，服务器响应代码为：" + responsecode);
			}
		} catch (Exception e) {
			System.out.println("获取不到网页的源码,出现异常：" + e);
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下载图片资源
	 */
	public void downImageSource() {

	}
}
