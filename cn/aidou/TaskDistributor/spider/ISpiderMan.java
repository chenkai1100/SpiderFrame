package cn.aidou.TaskDistributor.spider;

public interface ISpiderMan {
	//设置创建爬虫时间
	public void setCreateObjectTime();
	//控制爬虫状态
	public void setUsed(boolean flag);
	//判断爬虫是否被使用
	public boolean isUsed();
	//得到爬虫URL的方式
	public void spiderManURL();
	//爬虫处理方法
	public void spiderManHandler(String url);
}
