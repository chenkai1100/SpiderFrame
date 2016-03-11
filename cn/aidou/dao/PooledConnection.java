package cn.aidou.dao;

import java.sql.Connection;

public class PooledConnection {
	Connection connection = null; // 数据库连接
	boolean busy = false; // 此连接是否正在使用的标志，默认没有正在使用
	// 构造函数，根据一个 Connection 构告一个 PooledConnection 对象

	public PooledConnection(Connection connection) {
		this.connection = connection;
	}

	// 返回此对象中的连接
	public Connection getConnection() {
		return connection;
	}

	// 设置此对象的，连接
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// 获得对象连接是否忙
	public boolean isBusy() {
		return busy;
	}

	// 设置对象的连接正在忙
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
}
