package cn.aidou.aop;

import java.util.ArrayList;
import java.util.List;
/**
 * 面向切面编程
 * @author aidou
 */
public class TestAopInJDK {
	public static void main(String[] args) {
		
		CalculatorImpl calcImpl = new CalculatorImpl();
		BeforeHandler before = new BeforeHandlerImpl();
		AfterHandler after = new AfterHandlerImpl();
		/**
		 * 将通知处理类分发到目标函数周围
		 */
		List<AbstractHandler> handlers = new ArrayList<AbstractHandler>();
		handlers.add(before);//前置通知
		handlers.add(after);//后置通知
		Calculator proxy = (Calculator) ProxyFactory.getProxy(calcImpl,
				handlers);
		int result = proxy.calculate(20, 10);
		System.out.println("Final Result :::" + result);
	}

}
