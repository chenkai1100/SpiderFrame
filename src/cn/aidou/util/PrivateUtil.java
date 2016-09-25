package cn.aidou.util;

import java.lang.reflect.Method;
/**
 * 【使用：】
 *  PrivateUtil.invoke(new B(), "printlnA", new Class[] { String.class }, new Object[] { "test" });
 *	PrivateUtil.invoke(new B(), "printlnB");
 */
public class PrivateUtil {
	/** 
     * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。 
     *  
     * @param clazz 
     *            目标类 
     * @param methodName 
     *            方法名 
     * @param classes 
     *            方法参数类型数组 
     * @return 方法对象 
     * @throws Exception 
     */   
	/*********************************************/
	//public Method[] getMethods()返回某个类的所有公用（public）方法包括其继承类的公用方法，当然也包括它所实现接口的方法。
	//public Method[] getDeclaredMethods()对象表示的类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，
	//但不包括继承的方法。当然也包括它所实现接口的方法。
	/*********************************************/
    public static Method getMethod(Class clazz, String methodName,  
            final Class[] classes) throws Exception {  
        Method method = null;  
        try {  
            method = clazz.getDeclaredMethod(methodName, classes);  
        } catch (NoSuchMethodException e) {  
            try {  
                method = clazz.getMethod(methodName, classes);  
            } catch (NoSuchMethodException ex) {  
                if (clazz.getSuperclass() == null) {  
                    return method;  
                } else {  
                    method = getMethod(clazz.getSuperclass(), methodName,  
                            classes);  
                }  
            }  
        }  
        return method;  
    }  
  
    /** 
     * @param obj 
     *            调整方法的对象 
     * @param methodName 
     *            方法名 
     * @param classes 
     *            参数类型数组 
     * @param objects 
     *            参数数组 
     * @return 方法的返回值 
     */  
    public static Object invoke(final Object obj, final String methodName,  
            final Class[] classes, final Object[] objects) {  
        try {  
            Method method = getMethod(obj.getClass(), methodName, classes);  
            method.setAccessible(true);// 调用private方法的关键一句话  
            return method.invoke(obj, objects);  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
    /** 
     * @param obj 
     *            调整方法的对象 
     * @param methodName 
     *            方法名 
     * @param classes 
     *            参数类型数组 
     * @return 方法的返回值 
     */ 
    public static Object invoke(final Object obj, final String methodName,  
            final Class[] classes) {  
        return invoke(obj, methodName, classes, new Object[] {});  
    }  
    /** 
     * @param obj 
     *            调整方法的对象 
     * @param methodName 
     *            方法名 
     * @return 方法的返回值 
     */ 
    public static Object invoke(final Object obj, final String methodName) {  
        return invoke(obj, methodName, new Class[] {}, new Object[] {});  
    }  
}
