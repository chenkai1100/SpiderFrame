package cn.aidou.thread;

public class PooledThread {
	Thread thread = null; // 数据库连接
	boolean busy = false; // 此连接线程是否正在使用的标志，默认没有正在使用
	// 构造函数，根据一个 Thread 构告一个 PooledThread 对象

	public PooledThread(Thread thread) {
		this.thread = thread;
	}

	// 返回此对象中的线程
	public Thread getThread() {
		return thread;
	}

	// 设置此对象的线程
	public void setThread(Thread thread) {
		this.thread = thread;
	}

	// 获得对象线程是否忙
	public boolean isBusy() {
		return busy;
	}

	// 设置对象的线程正在忙
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
}
