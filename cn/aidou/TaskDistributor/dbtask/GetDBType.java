package cn.aidou.TaskDistributor.dbtask;

public class GetDBType {

	public GetDBType() {
	}

	public Object getDBType(String str) {
		Object obj = getObj(str);
		if (obj instanceof AdapterTypeOneService) {
			System.out.println("DB类型为：One");
			return (AdapterTypeOneService)obj;
		}
		if (obj instanceof AdapterTypeTwoService) {
			System.out.println("DB类型为：Two");
			return (AdapterTypeTwoService)obj;
		}
		if (obj instanceof AdapterTypeThreeService) {
			System.out.println("DB类型为：Three");
			return (AdapterTypeThreeService)obj;
		}
		if (obj instanceof AdapterTypeFourService) {
			System.out.println("DB类型为：Four");
			return (AdapterTypeFourService)obj;
		}
		if (obj instanceof AdapterTypeSixService) {
			System.out.println("DB类型为：Six");
			return (AdapterTypeSixService)obj;
		}
		if (obj instanceof AdapterTypeSevenService) {
			System.out.println("DB类型为：Seven");
			return (AdapterTypeSevenService)obj;
		}
		if (obj instanceof AdapterTypeEightService) {
			System.out.println("DB类型为：Eight");
			return (AdapterTypeEightService)obj;
		}
		else{
			try {
				throw new Exception(str+"号--任务不存在！");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	private Object getObj(String str) {
		String clazzName = "cn.aidou.TaskDistributor.dbtask.AdapterType" + str + "Service";
		try {
			Class<?> clazz = Class.forName(clazzName);
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
