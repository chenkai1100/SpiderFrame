package cn.aidou.listener;

import java.util.EventObject;  


/** 
 * 事件类,用于封装事件源及一些与事件相关的参数. 
 * @author Eric 
 */  
public class RunTimeEvent extends EventObject {  
    private static final long serialVersionUID = 1L;  
    private Object source;//事件源  
      
    public RunTimeEvent(Object source){  
        super(source);  
        this.source = source;  
    }  
  
    public Object getSource() {  
        return source;  
    }  
  
    public void setSource(Object source) {  
        this.source = source;  
    }  
}  
