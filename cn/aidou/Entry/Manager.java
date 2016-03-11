package cn.aidou.Entry;

import java.util.ArrayList;
import java.util.List;

import cn.aidou.TaskDistributor.StorageAdapter;
import cn.aidou.TaskDistributor.dbtask.AdapterTypeOneService;
import cn.aidou.TaskDistributor.spider.Spiders.CreateSpider;
import cn.aidou.constant.Constant;
import cn.aidou.dao.BaseDao;
import cn.aidou.robot.SpaceQueue;
import cn.aidou.thread.SuchThread;
import cn.aidou.thread.ThreadPool;
import cn.aidou.util.PackageParamObj;
import cn.aidou.util.ParamObject;

/**
 * 做爬去任务时 线程和爬虫均由任务管理器来管理维护。
 * 一个线程负责一个爬虫，线程从线程池中获得，并由线程池管理线程的生命周期
 * member代指爬虫
 * workspace代指线程
 * @author aidou
 *
 */
/**
 * 现状：线程是否能够有效地管理：线程置于线程队列当中， 一个任务过来会激活一个爬虫和一个线程
 * 
 * @author aidou 问题：如何处理线程交叉处理任务的过程？ 线程用多少初始化多少？ 爬虫依据执行不同的执行策略和任务种类去选择合适的执行环境？
 *         线程依据用户输入后再去第二个任务的执行，？ 动态创建线程的问题 ，是否需要动态代理的实现？
 *
 *
 *         URL任务队列、已爬取的URL表. 爬虫中多线程的管理实际是需要维护一个线程池；URL去重也是使用MD5结合布隆过滤器进行实现的.
 *         抓取线程主动去任务队列找活干，如果没活就等待，有活了就通知那些等待的抓取线程。
 *
 *
 *         采用生产者与消费者模式，生产者负责生产爬虫，爬虫存在于爬虫队列中，消费者用于管理执行线程，生产一个那么就会消费一个，
 *         在应用程序初始化的时候会初始化爬虫参数和线程参数，至于爬虫与线程之间的执行任务部分则会在线程生命周期中完成执行，并
 *         将需要的结果持久化到数据库。与此同时，当需要二次 数据加工的时候，可以从数据库中将数据取出后有爬虫管理者定义爬虫管理类的对象
 *         再次执行相同的任务。
 *
 */
public class Manager implements Constant{
	private int Depth;// 爬虫爬取深度
	private int SpiderCount;// 爬虫数量=任务数量 //一种类型的任务是由1个爬虫来完成的
	private CreateSpider suchSpider;
	private ThreadPool threadPool = null;
	private List<Thread> threadQueue;
	/**
	 *  1.初始化运行数据库参数
	 */
	public Manager() {
		new BaseDao();
	}
	/**
	 * 2.创建指定深度和数量的爬虫，初始化爬虫队列
	 * @param SpiderCount 爬虫数量
	 * @param Depth 爬虫爬去深度
	 */
	public void initMember(int SpiderCount, int Depth) {
		this.SpiderCount = SpiderCount;
		this.Depth = Depth;
		for (int i = 0; i < SpiderCount; i++) {
			suchSpider = new CreateSpider(Depth);
			suchSpider.setSpiderName(i);
			SpaceQueue.spiderQueue.add(suchSpider);
		}
	}
	/**
	 * 3.在线程池中创建指定数量的线程用于执行爬虫
	 * @注：无参代表默认标准
	 * @【线程池定义参考标准】
	 * 默认为
	 * initialThreads 10，incrementalThreads 5，maxThreads 50
	 * @param initialThreads 线程池的初始大小 10
	 * @param incrementalThreads 线程池自动增加的大小 5 
	 * @param maxThreads 连接池最大的大小 50
	 * 
	 */
	public void initWorkSpace() {
		threadPool = new ThreadPool();
		try {
			threadPool.createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void initWorkSpace(int initialThreads, int incrementalThreads,int maxThreads) {
		threadPool = new ThreadPool(initialThreads,incrementalThreads,maxThreads);
		try {
			threadPool.createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  4.处理线程中的任务
	 */
	public void executeWork() {
		
		try {
			threadQueue = new ArrayList<Thread>();
			for (int i = 0; i < SpiderCount; i++) {
//				System.out.println("---"+(String) PackageParamObj.getParamObject().getParam("chenkai", "sb"));
//				storageDistributor.setDBType("One");//将存储类型参数等信息告知存储分发器
//				System.out.println(storageDistributor.getTypeName()+"DB的数据量："+storageDistributor.getDataSize());
				Thread thread = threadPool.getThread();
//				((SuchThread) thread).setTaskName(replace(i+1));
				((SuchThread) thread).setRunningFlag(true);
				threadQueue.add(thread);
//				System.out.println("线程名字："+((SuchThread) thread).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String replace(int i){
		String str = "";
		switch (i) {
		case 1:
			str ="One";
			break;
		case 2:
			str ="Two";
			break;
		case 3:
			str ="Three";
			break;
		case 4:
			str ="Four";
			break;
		case 5:
			str ="Five";
			break;
		case 6:
			str ="Six";
			break;
		case 7:
			str ="Seven";
			break;
		case 8:
			str ="Eight";
			break;
		default:
			try {
				throw new Exception("没有找到匹配项！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	public void spiderDied() {
	}

	public void threadDied() {
	}

}
