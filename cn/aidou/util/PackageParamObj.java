package cn.aidou.util;

import java.lang.reflect.Constructor;

/**
 * 对底层基础参数进行封装[ParamObject外壳类]
 * @author aidou
 */
/**
 * 【使用：】 ParamObject po = PackageParamObj.getParamObject();
 * po.setParam("chenkai", "chenkai"); po.getParam("chenkai", "defValue");
 * 
 * @author aidou
 */
public final class PackageParamObj {
	private volatile static PackageParamObj packageParamObj;
	private static ParamObject PO = null;
	private PackageParamObj() {
		init();
	}
	/*
	 * Constructor constructor =
	 * ParamObject.class.getClass().getDeclaredConstructor(); Class c =
	 * cn.aidou.util.ParamObject.class.getClass();--为什么报错？
	 * 【Exception】java.lang.SecurityException: Can not make a java.lang.Class
	 * constructor accessible at
	 * java.lang.reflect.AccessibleObject.setAccessible0(Unknown Source)
	 */
	private static void init() {
		if (PO == null) {
			try {
				/**
				 * 调用ParamObject类的模块修改时间：2016/1/10 17:27 修改人：aidou
				 */
				/* 以下调用无参的、私有构造函数 */
				Class c = Class.forName("cn.aidou.util.ParamObject");
				Constructor constructor = c.getDeclaredConstructor();
				constructor.setAccessible(true);
				PO = (ParamObject) PrivateUtil.invoke(constructor.newInstance(), "getParamObject");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static ParamObject getParamObject() {
		if (PO == null) {
			getPackageParamObj();
		}
		return PO;
	}
	private static PackageParamObj getPackageParamObj() {
		if (packageParamObj == null) {
			synchronized (ParamObject.class) {
				if (packageParamObj == null) {
					packageParamObj = new PackageParamObj();
				}
			}
		}
		return packageParamObj;
	}
}
