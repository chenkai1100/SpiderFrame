package cn.aidou.listener;

import java.util.EventListener;

public class ExampleListener implements EventListener {
	public ExampleListener() {
		//初始化事件监听器配置信息
	}
	//事件发生后的回调方法
	public void callBackFunction(RunTimeEvent runTimeEvent) {
		//写监听器监听到事件后，回调函数的代码
	}
}
