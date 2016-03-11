package cn.aidou.listener;

import java.util.EventListener;
import cn.aidou.thread.SuchThread;
public class RunTimeListener implements EventListener {
	public RunTimeListener() {
		System.out.println("初始化蜘蛛人运行时间监控的事件监听器");
	}
	//事件发生后的回调方法
	public void callBackFunction(RunTimeEvent runTimeEvent) {
		System.out.println("蜘蛛人运行时间监控的事件监听器被调用！");
		System.out.println("事件源发生相应的事件后调用此回调函数，对监听器监听到的事件源的改变的处理事件的代码存放到监听器端");
		SuchThread suchThread = (SuchThread) runTimeEvent.getSource();
		System.out.println("运行超时了！");
		System.out.println("爬虫运行时间为：" + suchThread.getSpiderRunTime().toString() + "\"");
	}
}
