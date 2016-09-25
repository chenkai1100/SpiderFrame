package cn.aidou.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.aidou.bean.UrlQueue;
import cn.aidou.util.ObjFactory;
import cn.aidou.util.PackageParamObj;

import static cn.aidou.dao.BaseDao.releaseResource;

public class URLLinkDao{
	public URLLinkDao()
	{
	}

	private UrlQueue uq = null;
	private List<UrlQueue> li = new ArrayList<UrlQueue>();
	private Connection conn = null;
	private Statement st = null;
	private ConnectionPool connPool = ObjFactory.connPool;
	public List<UrlQueue> selectURL(){
		ResultSet rs = null;
		try {
			String sql = " select * from urlqueue ";
			conn = connPool.getConnection();
			Statement st = conn.createStatement();
			conn.prepareStatement(sql);
			rs = st.executeQuery(sql);
			while(rs.next()){
				uq = (UrlQueue) ObjFactory.createObj("cn.aidou.bean.UrlQueue");
				uq.setUrlid(rs.getInt("urlid"));
				uq.setUrl(rs.getString("url"));
				uq.setStatus(rs.getInt("status"));
				li.add(uq);
			}
			return li;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			releaseResource(rs, null, st);
			connPool.returnConnection(conn);
		}
	}
	public void updateURL(){
		
	}
	public void deleteURL(){
		
	}
	public void insertURL(String url, int status){
		String sql = "insert into urlqueue(url,status) values('" + url + "','" + status + "'); ";
		try {
			st = connPool.getConnection().createStatement();
			int j = st.executeUpdate(sql);
			System.out.println("被影响的行数：" + j);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			BaseDao.releaseResource(null, null, st);
			connPool.returnConnection(conn);
		}  
	}
}
