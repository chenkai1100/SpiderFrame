package cn.aidou.robot;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ReadLabelHandler {
	/**
	 * 2015.10.9 读取超链接标签 <a href="http://localhost:8080/Spider"/>
	 */
	public void readALabel(String link) {
		Document doc;
		String linkHref = "";
		try {
			doc = Jsoup.connect("http://" + link).get();
			String title = doc.title();
			Elements links = doc.select("a[href]"); // 带有href属性的a元素
			for (Element el : links) {
				linkHref = el.attr("href");// 获取href的值
				// String linkText = el.text();//获取a标签的内容
				// String outerHtml = el.outerHtml();//获取整个a标签

//				System.out.println("linkHref:" + linkHref);// 这个位置该往数据库里边存放了！！！！！！！！！！！！！！！！！
			}
			// 定义过滤规则
			// if(linkHref.matches("[a-zA-z]+://[^\\s]*")){
			// System.out.println("linkHref:"+linkHref);
			// }
			System.out.println("title:" + title);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
