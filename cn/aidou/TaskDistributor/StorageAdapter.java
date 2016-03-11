package cn.aidou.TaskDistributor;

import java.util.Iterator;
import java.util.List;

import cn.aidou.TaskDistributor.dbtask.AdapterTypeEightService;
import cn.aidou.TaskDistributor.dbtask.AdapterTypeFiveService;
import cn.aidou.TaskDistributor.dbtask.AdapterTypeFourService;
import cn.aidou.TaskDistributor.dbtask.AdapterTypeOneService;
import cn.aidou.TaskDistributor.dbtask.AdapterTypeSevenService;
import cn.aidou.TaskDistributor.dbtask.AdapterTypeSixService;
import cn.aidou.TaskDistributor.dbtask.AdapterTypeThreeService;
import cn.aidou.TaskDistributor.dbtask.AdapterTypeTwoService;
import cn.aidou.TaskDistributor.dbtask.GetDBType;
import cn.aidou.TaskDistributor.spider.Spiders.CreateSpider;
import cn.aidou.robot.SpaceQueue;

/**
 * 存储适配器中间件
 * @author aidou
 */
public class StorageAdapter {
	private String typeName;// 存储适配名称
	private double dataSize;// 任务数据量
	private String DBGradeCode = "";// 数据库等级编码
	private CreateSpider cs = null;
	private AdapterTypeOneService adapterTypeOneService = null;
	private AdapterTypeTwoService adapterTypeTwoService = null;
	private AdapterTypeThreeService adapterTypeThreeService = null;
	private AdapterTypeFourService adapterTypeFourService = null;
	private AdapterTypeFiveService adapterTypeFiveService = null;
	private AdapterTypeSixService adapterTypeSixService = null;
	private AdapterTypeSevenService adapterTypeSevenService = null;
	private AdapterTypeEightService adapterTypeEightService = null;
	
	public StorageAdapter() {
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setDataSize(double dataSize) {
		this.dataSize = dataSize;
	}

	public double getDataSize() {
		return dataSize;
	}

	public void setDBGradeCode(String DBGradeCode) {
		this.DBGradeCode = DBGradeCode;
	}

	public void getDBType() {
	}

	/**
	 * 由任务发动者(user)配置存储适配器
	 */
	public void setDBType(String type) {
		setTypeName(type);
		GetDBType dbType = new GetDBType();
		/**
		 * 得到由不同类型的数据库存储结构信息
		 */
		switch (type) {
		case "One":
			adapterTypeOneService = (AdapterTypeOneService) (dbType.getDBType(type));
			this.setDataSize(adapterTypeOneService.getDataSize());
			this.setDBGradeCode(adapterTypeOneService.getDBGradeCode());
			break;
		case "Two":
			adapterTypeTwoService = (AdapterTypeTwoService) (dbType.getDBType(type));
			this.setDataSize(adapterTypeTwoService.getDataSize());
			this.setDBGradeCode(adapterTypeTwoService.getDBGradeCode());
			break;
		case "Three":
			adapterTypeThreeService = (AdapterTypeThreeService) (dbType.getDBType(type));
			this.setDataSize(adapterTypeThreeService.getDataSize());
			this.setDBGradeCode(adapterTypeThreeService.getDBGradeCode());
			this.setDBGradeCode(adapterTypeThreeService.getDBGradeCode());
			break;
		case "Four":
			adapterTypeFourService = (AdapterTypeFourService) (dbType.getDBType(type));
			this.setDataSize(adapterTypeFourService.getDataSize());
			this.setDBGradeCode(adapterTypeFourService.getDBGradeCode());
			break;
		case "Five":
			adapterTypeFiveService = (AdapterTypeFiveService) (dbType.getDBType(type));
			this.setDataSize(adapterTypeFiveService.getDataSize());
			this.setDBGradeCode(adapterTypeFiveService.getDBGradeCode());
			break;
		case "Six":
			adapterTypeSixService = (AdapterTypeSixService) (dbType.getDBType(type));
			this.setDataSize(adapterTypeSixService.getDataSize());
			this.setDBGradeCode(adapterTypeSixService.getDBGradeCode());
			break;
		case "Seven":
			adapterTypeSevenService = (AdapterTypeSevenService) (dbType.getDBType(type));
			this.setDataSize(adapterTypeSevenService.getDataSize());
			this.setDBGradeCode(adapterTypeSevenService.getDBGradeCode());
			break;
		case "Eight":
			adapterTypeEightService = (AdapterTypeEightService) (dbType.getDBType(type));
			this.setDataSize(adapterTypeEightService.getDataSize());
			this.setDBGradeCode(adapterTypeEightService.getDBGradeCode());
			break;
		default:
			try {
				throw new Exception(type + "号DB不存在！");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	 * 存储信息分发给响应的爬虫
	 */
	public void workDistribution() {
		/**
		 * 将数据库等级编码分发给得到的爬虫
		 */
	}

	/**
	 * 由存储适配器从爬虫队列中循环侦测出空闲的爬虫
	 * 
	 * @return
	 */
	public synchronized CreateSpider getSpiderObj() {
		return CircleCheck();
	}

	/**
	 * 循环检测爬虫队列中的空闲爬虫
	 * 
	 * @return
	 */
	public synchronized CreateSpider CircleCheck() {
		cs = getSpderstate();
		while (cs == null) {
			try {
				wait(500);
				cs = getSpderstate();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return cs;
	}

	/**
	 * 得到爬虫当前的使用状态等信息
	 */
	public synchronized CreateSpider getSpderstate() {
		List<CreateSpider> li = SpaceQueue.spiderQueue;
		Iterator<CreateSpider> it = li.iterator();
		while (it.hasNext()) {
			CreateSpider CreateSpide = it.next();
			if (!CreateSpide.isUsed()) {
				return CreateSpide;
			}
		}
		System.out.println("爬虫处于忙碌状态");
		return null;
	}
}
