package cn.aidou.util;
/**
 * 自动增长型数组，自定义初始数组大小16，数组中可以存放任意对象
 * @author aidou
 * 提供给用户增、删、改、查操作函数
 */
public class Array {
	//数组中元素个数  
    private int count = 0;  
    //初始容量大小  
    private int capcity = 16;  
    //增量，每次增加的比例  
    private double increment = 1.2;  
    //初始化数组  
    private Object[] src = new Object[capcity];  
  
    public Array() {  
  
    }  
  
    //自定义原始数组大小  
    public Array(int capcity) {  
        this.capcity = capcity;  
    }  
  
    // 增加  
    public void add(Object obj) {  
        //判断是否越界，如是，则扩充数组  
        if (count >= src.length) {  
            src = extend();  
        }  
        //将新增加的数据放在count处  
        src[count] = obj;  
        count++;  
    }  
  
    // 对数组的扩充  
    private Object[] extend() {  
        //扩充后的数组容量是旧的数组的increment倍  
        Object[] dest = new Object[(int) (src.length * increment)];  
        for (int i = 0; i < src.length; i++) {  
            dest[i] = src[i];  
        }  
  
        //返回扩充后的数组  
        return dest;  
    }  
  
    // 输出  
    public void print() {  
        for (int i = 0; i < count; i++) {  
            System.out.print(src[i] + " ");  
        }  
        System.out.println();  
    }  
  
    // 获得数组大小  
    public int size() {  
  
        return count;  
    }  
  
    // 插入  
    public void insert(int index, Object obj) {  
    	//做插入操作的时候如果模拟数组总数加入一个数值后就会超出数组总数，那么就执行对数组扩充操作
        if(count + 1 >= src.length){  
            src = extend();  
        }  
        //从最后一个元素开始，把前一个元素放到后一个位置来  
        for(int i=count; i>index; i--){  
            src[i] = src[i-1];  
        }  
        //将要插入的元素放在index处  
        src[index] = obj;  
        //在插入一个元素后，长度+1  
        count ++;  
    }  
      
    //替换index处的数据  
    public void replace(int index, Object obj){  
        src[index] = obj;  
    }  
      
    //删除index处的数据元素  
    public void delete(int index){  
        for(int i=index; i<src.length-1; i++){  
            src[i] = src[i+1];  
        }  
        count --;  
    }  
      
    //返回index处的数据  
    public Object get(int index){  
          
        return src[index];  
    } 
}
