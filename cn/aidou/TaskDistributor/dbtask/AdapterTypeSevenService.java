package cn.aidou.TaskDistributor.dbtask;


public class AdapterTypeSevenService extends AdapterType{
	public AdapterTypeSevenService(){}
	private int taskID;//任务ID
	private double dataSize;//任务数据量
	
	private String taskName;
	private final String DBGradeCode = "6c19d660d330cf1edf2de58132ea0259b6b7ecd9";
	public void getTaskID(){
		
	}
	public double getDataSize(){
		return dataSize;
	}
	public String getDBGradeCode(){
		return DBGradeCode;
	}
	void setTaskName(){
		taskName = this.getClass().getName();
	}
	public String getTaskName(){
		return taskName;
	}
}
