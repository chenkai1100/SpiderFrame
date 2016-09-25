package cn.aidou.dao;

import cn.aidou.util.PackageParamObj;
import cn.aidou.util.ParamObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class BaseDao
{
    Map<String, Object> map = new HashMap<String, Object>();
    ParamObject po = PackageParamObj.getParamObject();
    /**
     * 初始化数据库连接池
     */
    public BaseDao(Map<String, String> dbinfo)
    {
        try
        {
            ConnectionPool cp = new ConnectionPool(dbinfo);
            map.put("connPool", cp);
            po.setEnvRoot(map);
            cp.createPool();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     *
     * @param ResultSet         rs
     * @param PreparedStatement pst
     * @param Statement         st
     */
    public static void releaseResource(ResultSet rs, PreparedStatement pst, Statement st)
    {
        try
        {
            if (rs != null)
            {
                rs.close();
            }
            if (st != null)
            {
                st.close();
            }
            if (pst != null)
            {
                pst.close();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
