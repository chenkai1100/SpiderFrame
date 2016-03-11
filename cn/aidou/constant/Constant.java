package cn.aidou.constant;

import cn.aidou.TaskDistributor.StorageAdapter;
import cn.aidou.dao.ConnectionPool;
import cn.aidou.util.PackageParamObj;

/**
 * 在后期的时候可以反序列化配置对象，读取xml文件中整个项目中的配置信息，
 * 使得整个项目更好的分层！
 * 20151023
 * @author aidou
 * 常量接口：存放平台中的全局变量。
 */
public interface Constant {
	static final String driver = "com.mysql.jdbc.Driver";
	static final String url = "jdbc:mysql://localhost:3306/Spider";
	static final String username = "root";
	static final String password = "1992";
	static final ConnectionPool connPool = new ConnectionPool(driver, url, username, password);
	static final StorageAdapter storageDistributor = new StorageAdapter();//初始化存储适配器中间件
}
