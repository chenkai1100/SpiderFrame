package cn.aidou.thread;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import cn.aidou.TaskDistributor.spider.Spiders.CreateSpider;
import cn.aidou.listener.ExampleListener;
import cn.aidou.listener.RunTimeEvent;
import cn.aidou.listener.RunTimeListener;

/**
 * 一个线程由一个或多个监听器监控其内部各个关节的状态，视情况做出相应的响应！
 * 
 * @author aidou
 *
 */

public class SuchThread extends Thread{
	private boolean runningFlag;// 线程状态
	private String threadName;
	private BigDecimal SpiderRunTime;
	// 给线程（事件源）添加监听器容器
	private Set<Object> threadListener;
	private CreateSpider spider;

	public SuchThread() {
		this.runningFlag = false;// false代表线程没有运行的标志位！
		threadListener = new HashSet<Object>();
	}

	public void setThreadName(int num) {
		this.threadName = "线程" + num;
	}

	public String getThreadName() {
		return threadName;
	}
	

	// 线程执行
	public synchronized void run() {
		try {
			while (true) {
				if (!runningFlag) {
					this.wait();
				} else {


					//spider = storageContext.getSpiderObj();
					long startTime = System.nanoTime();// 纳秒
					/**
					 * 2.开始执行爬虫
					 */

					spider.spiderManURL();
					/**
					 * 2.3.
					 */
					/**
					 * 04.
					 */
					/**
					 * 05.
					 */
					/**
					 * 06.
					 */
					long endTime = System.nanoTime();// 纳秒
					/**
					 * 3.任务执行完毕
					 */
					System.out.println("startTime:" + startTime + " endTime:" + endTime);
					SpiderRunTime = BigDecimal.valueOf(endTime - startTime, 9);// 秒级差值
					/**
					 * 4.某一爬虫执行时间如果超过规定数值，则认为该爬虫已经出现异常
					 */
					if (SpiderRunTime.doubleValue() == 2) {
						notifies();
						//throw new Exception("爬虫人在本次"+taskName+"任务中执行时间为"+SpiderRunTime+"，已超时！");
					}

					/**
					 * 5.统计当前爬虫执行本次任务所需的时间
					 */
					//System.out.println("爬虫人爬取任务【"+taskName+"】的时间为：" + SpiderRunTime + "s");
					// ***************************
					// 线程所执行的爬虫的任务结束
					// ***************************
					/**
					 * 6.设置执行当前任务的线程为休眠状态
					 */
					setRunningFlag(false);
					/**
					 * 6.1.释放爬虫所占用的锁及爬虫占用的资源，将爬虫至于限制状态
					 */
					spider.setUsed(false);
					//System.out.println("任务【"+taskName + "】结束！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 控制线程活跃状态
	 * @param flag   
	 * |--false 休眠
	 * |--true 激活
	 */
	public synchronized void setRunningFlag(boolean flag) {
		this.runningFlag = flag;
		if (runningFlag) {
			this.notify();
			System.out.println("唤醒线程!");
		}
	}
	

	// **********************************
	// 给事件源注册监听事件开始
	// **********************************
	public void addListenerEvent(Object objListener) {
		System.out.println("给事件源注册监听事件");
		this.threadListener.add(objListener);
	}

	public BigDecimal getSpiderRunTime() {
		return SpiderRunTime;
	}

	// **********************************
	// 当事件源中的事件被触发时执行此方法
	// **********************************
	protected void notifies() {
		for (Object objListener : this.threadListener) {
			if (objListener instanceof RunTimeListener) {
				((RunTimeListener) objListener).callBackFunction(new RunTimeEvent(this));
			} else {
				((ExampleListener) objListener).callBackFunction(new RunTimeEvent(this));
			}
		}
	}
}
