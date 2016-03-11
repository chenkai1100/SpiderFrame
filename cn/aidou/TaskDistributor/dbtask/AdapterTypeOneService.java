package cn.aidou.TaskDistributor.dbtask;

/**
 * One类型的数据库存储结构信息。
 * @author aidou
 *
 */
public class AdapterTypeOneService extends AdapterType {
	public AdapterTypeOneService() {
	}

	private double dataSize = 100000000000000.00;// 任务数据量
	private String taskName;
	private final String DBGradeCode = "6c19d660d330cf1edf2de58132ea0259b6b7ecd9";// Mysql

	void setTaskName() {
		taskName = this.getClass().getName();
	}

	public String getTaskName() {
		return taskName;
	}

	@Override
	public double getDataSize() {
		return dataSize;
	}

	@Override
	public String getDBGradeCode() {
		return DBGradeCode;
	}
}
