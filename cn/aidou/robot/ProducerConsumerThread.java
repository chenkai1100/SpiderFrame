package cn.aidou.robot;

public class ProducerConsumerThread {
	public static void main(String[] args) {
		Resource r = new Resource();
		Producer p = new Producer(r);
		Consumer c = new Consumer(r);

		Thread t1 = new Thread(p);
		Thread t2 = new Thread(p);
		Thread t3 = new Thread(p);
		Thread t4 = new Thread(p);

		Thread t5 = new Thread(c);
		Thread t6 = new Thread(c);
		Thread t7 = new Thread(c);
		Thread t8 = new Thread(c);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();

	}
}

class Resource {
	private String name;
	private int count = 1;
	private boolean flag = false;

	public synchronized void input(String name) {
		while (flag) {
			try {
				wait();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		this.name = name + "--" + count++;
		System.out.println(Thread.currentThread() + "生产者" + this.name);
		flag = true;
		this.notifyAll();
	}

	public synchronized void output() {
		while (!flag) {
			try {
				wait();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		System.out.println(Thread.currentThread() + "消费者----" + this.name);
		flag = false;
		this.notifyAll();

	}

}

class Producer implements Runnable {
	private Resource res;

	Producer(Resource res) {
		this.res = res;
	}

	@Override
	public void run() {
		while (true) {
			res.input("+商品+");
		}
	}

}

class Consumer implements Runnable {
	private Resource res;

	Consumer(Resource res) {
		this.res = res;
	}

	@Override
	public void run() {
		while (true) {
			res.output();
		}
	}

}