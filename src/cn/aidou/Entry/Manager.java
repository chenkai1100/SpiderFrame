package cn.aidou.Entry;

import java.util.*;

import org.apache.log4j.Logger;

import cn.aidou.TaskDistributor.SpiderContainer;
import cn.aidou.TaskDistributor.spider.Spiders.CreateSpider;
import cn.aidou.bean.SpiderBean;
import cn.aidou.dao.BaseDao;
import cn.aidou.thread.SuchThread;
import cn.aidou.thread.ThreadPool;
import cn.aidou.util.Encrypt;


/**
 * 做爬去任务时 线程和爬虫均由任务管理器来管理维护。
 * 一个线程负责一个爬虫，线程从线程池中获得，并由线程池管理线程的生命周期
 * member代指爬虫
 * workspace代指线程
 *
 * @author aidou
 */

/**
 * 现状：线程是否能够有效地管理：线程置于线程队列当中， 一个任务过来会激活一个爬虫和一个线程
 *
 * @author aidou 问题：如何处理线程交叉处理任务的过程？ 线程用多少初始化多少？ 爬虫依据执行不同的执行策略和任务种类去选择合适的执行环境？
 *         线程依据用户输入后再去第二个任务的执行，？ 动态创建线程的问题 ，是否需要动态代理的实现？
 *         <p>
 *         <p>
 *         URL任务队列、已爬取的URL表. 爬虫中多线程的管理实际是需要维护一个线程池；URL去重也是使用MD5结合布隆过滤器进行实现的.
 *         抓取线程主动去任务队列找活干，如果没活就等待，有活了就通知那些等待的抓取线程。
 *         <p>
 *         <p>
 *         采用生产者与消费者模式，生产者负责生产爬虫，爬虫存在于爬虫队列中，消费者用于管理执行线程，生产一个那么就会消费一个，
 *         在应用程序初始化的时候会初始化爬虫参数和线程参数，至于爬虫与线程之间的执行任务部分则会在线程生命周期中完成执行，并
 *         将需要的结果持久化到数据库。与此同时，当需要二次 数据加工的时候，可以从数据库中将数据取出后有爬虫管理者定义爬虫管理类的对象
 *         再次执行相同的任务。
 */
public class Manager {
	private static Logger logger = Logger.getLogger(Manager.class);
	private int spiderCount;// 爬虫数量=任务数量 //一种类型的任务是由1个爬虫来完成的
	private CreateSpider suchSpider;
	private ThreadPool threadPool = null;
	private List<Thread> threadQueue;
	private SpiderContainer sc = new SpiderContainer();

	/**
	 * 1.初始化运行数据库参数
	 */
	public Manager(String[] args) {
		new BaseDao(getDefaultParam_mysql());
		// if (checkNotNull(args))
		// {
		// new BaseDao(initDB(args));
		// } else
		// {
		// System.exit(-1);
		// }
	}

	private Map<String, String> getDefaultParam_mysql() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/Spider";
		String username = "root";
		String password = "1992";
		Map<String, String> mysqlnfo = new HashMap<String, String>();
		mysqlnfo.put("driver", driver);
		mysqlnfo.put("dbUrl", url);
		mysqlnfo.put("dbUsername", username);
		mysqlnfo.put("dbPassword", password);
		return mysqlnfo;
	}

	/**
	 * //String driver = "com.mysql.jdbc.Driver"; //String url =
	 * "jdbc:mysql://localhost:3306/Spider"; //String username = "root";
	 * //String password = "1992";
	 *
	 * @param args
	 * @return
	 */
	public static Map<String, String> initDB(String[] args) {
		Map<String, String> DBinfo = new HashMap<String, String>();
		DBinfo.put("driver", args[0]);
		DBinfo.put("url", args[1]);
		DBinfo.put("username", args[2]);
		DBinfo.put("password", args[3]);
		return DBinfo;
	}

	private static boolean checkNotNull(String[] args) {
		if (args[0] == null || args[1] == null || args[2] == null || args[3] == null) {
			try {
				throw new NullPointerException("初始运行参数不能为空！");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				return false;
			}
		}
		return true;
	}

	/**
	 * 3.创建指定深度和数量的爬虫，初始化爬虫队列
	 *
	 * @param spiderCount
	 *            爬虫数量
	 * @param Depth
	 *            爬虫爬取深度
	 */
	public void initMember(int spiderCount, int Depth) {
		this.spiderCount = spiderCount;
		for (int i = 0; i < spiderCount; i++) {
			SpiderBean sb = new SpiderBean(getSpiderName(), Depth);
			logger.info("第" + i + "个爬虫ID为" + sb.getspiderID());
			suchSpider = new CreateSpider(sb);
			sc.addSpider(suchSpider);
		}
	}

	public String getSpiderName() {
		return Encrypt.md5AndSha(String.valueOf(Math.random() * 50) + 50000 + System.nanoTime());
	}

	/**
	 * 2.创建线程池
	 *
	 * @注：无参代表默认标准
	 * @【线程池定义参考标准】 默认为 initialThreads 10，incrementalThreads 5，maxThreads 50
	 */
	public void initWorkSpace() {
		threadPool = new ThreadPool();
		try {
			threadPool.createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initWorkSpace(int initialThreads, int incrementalThreads, int maxThreads) {
		threadPool = new ThreadPool(initialThreads, incrementalThreads, maxThreads);
		try {
			threadPool.createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkRunningEnv() {
		/**
		 * 0.对任务的初始化工作
		 */
		/**
		 * input [对输入信息进行处理，将信息呈递给需要的目标] 01.获得任务 a.通过文件方式 b.网页源代码方式
		 * c.进行网页分析获得数据流 d.视频流技术鉴别 e.图像流输入。如 对牌照图像识别进而识别套牌车 f.对声音进行识别，去除噪音后的语音识别
		 * g.传感器输入信号，对输入信号的处理 h.对文件夹进行监控，将文件信息上传至hdfs
		 */
		/**
		 * 如果涉及集群，还需要考虑分布式对任务处理的问题。
		 */
		/**
		 * 02.对任务根据情况来解压缩
		 */
		// System.out.println("开始执行任务【 " + taskName+"】");
		/**
		 * 1.由任务分发器取出适合当前任务的爬虫 这里需要增加爬虫管理的容器，把爬虫的状态交由容器管理，应用程序不对爬虫负责！
		 */
	}

	/**
	 * 4.处理线程中的任务 前提是任务+线程+爬虫都准备好了！
	 */
	public void executeWork() {
		try {
			checkRunningEnv();// 对初始化参数进行检查
			threadQueue = new ArrayList<Thread>();
			for (int i = 0; i < spiderCount; i++) {
				Thread thread = threadPool.getThread();
				((SuchThread) thread).setRunningFlag(true);
				threadQueue.add(thread);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
