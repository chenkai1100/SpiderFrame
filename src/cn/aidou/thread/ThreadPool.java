package cn.aidou.thread;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.log4j.Logger;

import cn.aidou.Entry.EntryClass;

/**
 * java--线程池
 * 
 * @author aidou
 */
public class ThreadPool {
	private int initialThreads = 10;
	private int incrementalThreads = 5;
	private int maxThreads = 50;
	private Vector<PooledThread> threads = null; // 存放连接池中数据库连接的向量
													// 初始时为 null
	private static Logger logger = Logger.getLogger(ThreadPool.class);
	public ThreadPool() {
	}

	/**
	 * 线程池定义参考标准 默认为initialThreads 10，incrementalThreads 5，maxThreads 50
	 * 
	 * @param initialThreads
	 *            线程池的初始大小 10
	 * @param incrementalThreads
	 *            线程池自动增加的大小 5
	 * @param maxThreads
	 *            连接池最大的大小 50
	 */
	public ThreadPool(int initialThreads, int incrementalThreads, int maxThreads) {
		this.initialThreads = initialThreads;
		this.incrementalThreads = incrementalThreads;
		this.maxThreads = maxThreads;
	}

	public int getInitialThreads() {
		return initialThreads;
	}

	public void setInitialThreads(int initialThreads) {
		this.initialThreads = initialThreads;
	}

	public int getIncrementalThreads() {
		return incrementalThreads;
	}

	public void setIncrementalThreads(int incrementalThreads) {
		this.incrementalThreads = incrementalThreads;
	}

	public int getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public synchronized void createPool() throws Exception {
		if (threads != null) {
			return;
		}
		threads = new Vector<PooledThread>();
		createThreads(this.initialThreads);
		logger.info("Thread pool created successfully"+threads);
	}

	private void createThreads(int numThreads) throws SQLException {
		for (int x = 0; x < numThreads; x++) {
			if (this.maxThreads > 0 && this.threads.size() >= this.maxThreads) {
				break;
			}
			try {
				threads.addElement(new PooledThread(newThread()));// connections中放置的是Connection对象
			} catch (SQLException e) {
				logger.info("Thread pool creation failed ！"+e.getMessage());
				throw new SQLException();
			}
			logger.info("A pool of threads created successfully ...！");
		}
	}

	private Thread newThread() throws SQLException {
		SuchThread thread = new SuchThread();
		thread.start();
		return thread;
	}

	public synchronized Thread getThread() throws SQLException {
		if (threads == null) {
			logger.warn("Thread pool has not been created!");
			return null;
		}
		Thread thread = getFreeThread();
		while (thread == null) {
			wait(250);
			thread = getFreeThread();
		}
		return thread;
	}

	private Thread getFreeThread() throws SQLException {
		Thread thread = findFreeThread();
		if (thread == null) {
			createThreads(incrementalThreads);
			thread = findFreeThread();
			if (thread == null) {
				return null;
			}
		}
		return thread;
	}

	private Thread findFreeThread() throws SQLException {
		Thread thread = null;
		PooledThread pThread = null;
		/**
		 * boolean hasMoreElemerts()
		 * 测试Enumeration枚举对象中是否还含有元素，如果返回true，则表示还含有至少一个的元素。 ·Object
		 * nextElement() ：如果Bnumeration枚举对象还含有元素，该方法得到对象中的下一个元素。
		 */
		Enumeration<PooledThread> enumerate = threads.elements();
		while (enumerate.hasMoreElements()) {
			pThread = (PooledThread) enumerate.nextElement();
			if (!pThread.isBusy()) {
				thread = pThread.getThread();
				pThread.setBusy(true);
				if (!thread.isAlive()) {
					try {
						thread = newThread();
					} catch (SQLException e) {
						logger.error("Thread creation failed"+ e.getMessage());
					}
					pThread.setThread(thread);
				}
				break;
			}
		}
		return thread;
	}

	public void returnThread(Thread thread) {
		if (threads == null) {
			// 线程池不存在，无法返回此线程到线程池中 
			logger.warn("The thread pool does not exist and can not return this thread to the thread pool!");
			return;
		}
		PooledThread pThread = null;
		Enumeration<PooledThread> enumerate = threads.elements();
		while (enumerate.hasMoreElements()) {
			pThread = (PooledThread) enumerate.nextElement();
			if (thread == pThread.getThread()) {
				pThread.setBusy(false);
				break;
			}
		}
	}

	public synchronized void refreshThreads() throws SQLException {
		if (threads == null) {
			//线程池不存在，无法刷新
			logger.warn("Thread pool does not exist, can not be refreshed");
			return;
		}
		PooledThread pThread = null;
		Enumeration<PooledThread> enumerate = threads.elements();
		while (enumerate.hasMoreElements()) {
			pThread = (PooledThread) enumerate.nextElement();
			if (pThread.isBusy()) {
				wait(5000);
			}
			closeThread(pThread.getThread());
			pThread.setThread(newThread());
			pThread.setBusy(false);
		}
		System.gc();
	}

	public synchronized void closeThreadPool() throws SQLException {
		if (threads == null) {
			// 线程池不存在，无法关闭 
			logger.error("Thread pool does not exist, can not be closed");
			return;
		}
		PooledThread pThread = null;
		Enumeration<PooledThread> enumerate = threads.elements();
		while (enumerate.hasMoreElements()) {
			pThread = (PooledThread) enumerate.nextElement();
			if (pThread.isBusy()) {
				wait(5000);
			}
			closeThread(pThread.getThread());
			threads.removeElement(pThread);
			System.out.println(pThread);
		}
		threads = null;
		System.gc();
	}

	private void closeThread(Thread thread) {
		try {
			thread = null;
			logger.info("Thread closed");
		} catch (Exception e) {
			logger.equals("Error closing thread"+e.getMessage());
		}
	}

	private void wait(int mSeconds) {
		try {
			Thread.sleep(mSeconds);
		} catch (InterruptedException e) {
		}
	}

}
