package cn.aidou.util;

import java.util.HashMap;
import java.util.Map;
/**
 * ParamObject类用于存放项目中运行所需要的参数
 * @author aidou
 */
public class ParamObject {
	private volatile static ParamObject paramObject;
	private Map<String, Object> ParamRoot = new HashMap<String, Object>();// 保存运行参数
	private Map<String, Object> EnvRoot = new HashMap<String, Object>();// 保存环境变量
	private ParamObject() {}
	@SuppressWarnings("unused")
	private static ParamObject getParamObject() {
		if (paramObject == null) {
			synchronized (ParamObject.class) {
				if (paramObject == null) {
					paramObject = new ParamObject();
				}
			}
		}
		return paramObject;
	}
	public Map<String, Object> getParamRoot() {
		return ParamRoot;
	}
	public void setParamRoot(Map<String, Object> paramRoot) {
		this.ParamRoot = paramRoot;
	}
	public Map<String, Object> getEnvRoot() {
		return EnvRoot;
	}
	public void setEnvRoot(Map<String, Object> envRoot) {
		this.EnvRoot = envRoot;
	}
	public void setParam(String key, Object value) {
		if (ParamRoot == null) {
			ParamRoot = new HashMap<String, Object>();
		}
		if (value == null && ParamRoot.containsKey(key)) {
			ParamRoot.remove(key);
		} else {
			ParamRoot.put(key, value);
		}
	}
	public Object getParam(String key, Object defValue) {
		if (ParamRoot == null || !ParamRoot.containsKey(key) || ParamRoot.get(key) == null) {
			return defValue;
		}
		return ParamRoot.get(key);
	}
	public void setEnv(String key, Object value) {
		if (EnvRoot == null) {
			EnvRoot = new HashMap<String, Object>();
		}
		if (value == null) {
			EnvRoot.remove(key);
		} else {
			EnvRoot.put(key, value);
		}
	}
	public Object getEnv(String key, Object defValue) {
		if (EnvRoot == null || !EnvRoot.containsKey(key) || EnvRoot.get(key) == null) {
			return defValue;
		}
		return EnvRoot.get(key);
	}
}
