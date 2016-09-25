package cn.aidou.util;

import org.apache.log4j.Logger;
import cn.aidou.dao.ConnectionPool;

/**
 * 工厂类
 * @author aidou
 *
 */
public class ObjFactory
{
	public static ConnectionPool connPool = (ConnectionPool) PackageParamObj.getParamObject().getEnvRoot().get("connPoll");
	//创建指定类名的对象
	public static Object createObj(String className) throws Exception {
		return Class.forName(className).newInstance();
	}
}
