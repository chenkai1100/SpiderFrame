package cn.aidou.robot;

import java.util.ArrayList;
import java.util.List;

import cn.aidou.TaskDistributor.spider.Spiders.CreateSpider;


public interface SpaceQueue {
	static List<CreateSpider> spiderQueue = new ArrayList<CreateSpider>(); 
}
