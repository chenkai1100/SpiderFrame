package cn.aidou.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import cn.aidou.constant.Constant;


public class BaseDao implements Constant{
	/**
	 *  初始化数据库连接池
	 */
	public BaseDao() {
		try {
			connPool.createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *	释放资源 
	 * @param ResultSet rs 
	 * @param PreparedStatement pst
	 * @param Statement st
	 */
	public void releaseResource(ResultSet rs, PreparedStatement pst, Statement st) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (pst != null) {
				pst.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
