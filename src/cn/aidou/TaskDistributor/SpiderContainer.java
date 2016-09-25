package cn.aidou.TaskDistributor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.aidou.TaskDistributor.spider.Spiders.CreateSpider;

/**
 * 爬虫容器
 * 对爬虫的生命周期负责
 * @author aidou
 */
public class SpiderContainer
{
	private List<CreateSpider> spiderQueue = new ArrayList<CreateSpider>();
	private CreateSpider cs = null;

	public void addSpider(CreateSpider cs)
	{
		spiderQueue.add(cs);
	}

	public void setSpiderQueue(List<CreateSpider> spiderQueue)
	{
		this.spiderQueue = spiderQueue;
	}

	public SpiderContainer() {
	}

	/**
	 * 由存储适配器从爬虫队列中循环侦测出空闲的爬虫
	 * 
	 * @return
	 */
	public synchronized CreateSpider getSpiderObj() {
		return CircleCheck();
	}

	/**
	 * 循环检测爬虫队列中的空闲爬虫
	 * 
	 * @return
	 */
	public synchronized CreateSpider CircleCheck() {
		cs = getSpderstate();
		while (cs == null) {
			try {
				wait(500);
				cs = getSpderstate();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return cs;
	}

	/**
	 * 得到爬虫当前的使用状态等信息
	 */
	public synchronized CreateSpider getSpderstate() {
		Iterator<CreateSpider> it = spiderQueue.iterator();
		while (it.hasNext()) {
			CreateSpider CreateSpide = it.next();
			if (!CreateSpide.isUsed()) {
				return CreateSpide;
			}
		}
		System.out.println("爬虫处于忙碌状态");
		return null;
	}
}
