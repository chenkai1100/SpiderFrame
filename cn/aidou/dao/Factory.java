package cn.aidou.dao;

/**
 * 工厂类
 * @author aidou
 *
 */
public class Factory {
	
	//创建指定类名的对象
	public static Object createObj(String className) throws Exception {
		return Class.forName(className).newInstance();
	}
}
