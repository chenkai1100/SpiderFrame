package cn.aidou.Entry;


import cn.aidou.Entry.Manager;
import cn.aidou.util.PackageParamObj;
import cn.aidou.util.ParamObject;
/**
 * 程序有哪些不足 多线程怎样对任务管理的比较好？效率会高一些？ 一个线程负责一个对象，一个对象拥有1把锁，当锁中的对象出现故障的时候有监听器自行断电，
 * 终止线程，进而抛出异常给开发人员，当线程与任务链接状况很好的情况下（需要有很好的监听
 * 排错机制），每个需要访问任务的线程拥有对象任务的一把锁，实现了线程对任务的互斥管理， 方便了责任的划分。
 * 
 * @author aidou
 */
public class EntryClass {

	public static void main(String[] args) {
		ParamObject po = PackageParamObj.getParamObject();
		po.getParam("mysql", "");
		Manager mg = new Manager();// 初始化数据库连接参数
		mg.initMember(1, 2);// 创建指定深度和数量的爬虫，初始化爬虫队列
		mg.initWorkSpace();// 在线程池中创建指定数量的线程用于执行爬虫
		mg.executeWork();// 爬虫、线程已初始化完毕，待任务到来时唤醒线程
	}
}
