package cn.aidou.TaskDistributor.spider.Spiders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.aidou.TaskDistributor.spider.ISpiderMan;
import cn.aidou.bean.SpiderBean;
import cn.aidou.bean.UrlQueue;
import cn.aidou.dao.URLLinkDao;
import cn.aidou.robot.DownResource;
import cn.aidou.robot.IDownResource;
import cn.aidou.robot.ReadLabelHandler;
import cn.aidou.util.PackageParamObj;
import cn.aidou.util.ParamObject;
/**
 * 持久化部分有太多的数据库连接了
 * @author aidou
 *
 */

public class CreateSpider implements ISpiderMan{
	private String SpiderID;//爬虫ID
	private int Depth;// 爬虫爬取深度;
	private boolean used = false;// 爬虫是否被使用
	private String StartTime;// 爬虫开始时间;
	private static Logger logger = Logger.getLogger(CreateSpider.class);
	//private URLLinkDao uRLLinkDao = null;
	/**
	 * 设置创建爬虫时间
	 */
	public void setCreateObjectTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss ");
		StartTime = sdf.format(date);
		logger.info("当前时间" + StartTime);
	}
	/**
	 * 控制爬虫状态
	 * @param flag
	 * false 休眠
	 * true 忙碌
	 */
	public void setUsed(boolean flag){
		this.used = flag;
	}
	/**
	 * 判断爬虫是否被使用
	 * @return
	 */
	public boolean isUsed(){
		return used;
	}
	public CreateSpider(SpiderBean sb) {
		this.SpiderID=sb.getspiderID();
		this.Depth = sb.getSpiderDepth();
		setCreateObjectTime();
	}

	/**
	 * 【注意：】多线程环境下抛出异常：待解决
	 */
	public void spiderManURL(){
		List<UrlQueue> li = null;
		for(UrlQueue list : li){
			String url = list.getUrl();
			spiderManHandler(url);
		}
	}
	public void spiderManHandler(String url) {
		/**
		 * 0.链接输入(该模块针对不同的输入源备用)
		 */
		
		/**
		 * 1.链接处理：获取网页源码
		 */
		IDownResource dps = new DownResource();
		dps.downPageSourceHandler(url);
		/**
		 * 2.对获取的源代码的处理
		 */
		ReadLabelHandler rlh = new ReadLabelHandler();
		rlh.readALabel(url);
		/**
		 * 3.链接输出：持久化操作、存入本地磁盘
		 */
		//uRLLinkDao.insertURL(url, 0);
		/**
		 * 4.因为线程不需要知道自己执行的是哪一个爬虫，只需要知道自己执行的是哪种类型的任务即可
		 * 具体哪种类型的爬虫执行了什么特征的任务由任务分发器负责，所以此处不需要关心爬虫是由
		 * 那个线程执行的！也就是说线程对爬虫是透明的！
		 */
		//【作废】System.out.println(SpiderName + "是由：" + ThreadName + "执行的！");
	}
}
