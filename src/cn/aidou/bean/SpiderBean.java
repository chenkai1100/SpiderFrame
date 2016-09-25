package cn.aidou.bean;

/**
 * Created by aidou on 2016/4/30.
 */
public class SpiderBean
{
    private String spiderID;
    private int spiderStatus;
    private int spiderDepth;
    public SpiderBean(String spiderID, int spiderDepth)
    {
        this.spiderID = spiderID;
        this.spiderStatus = 1;
        this.spiderDepth = spiderDepth;
    }

    public SpiderBean(String spiderID, int spiderStatus, int spiderDepth)
    {
        this.spiderID = spiderID;
        this.spiderStatus = spiderStatus;
        this.spiderDepth = spiderDepth;
    }

    public String getspiderID()
    {
        return spiderID;
    }

    public void setspiderID(String spiderID)
    {
        this.spiderID = spiderID;
    }

    public int getSpiderStatus()
    {
        return spiderStatus;
    }

    public void setSpiderStatus(int spiderStatus)
    {
        this.spiderStatus = spiderStatus;
    }

    public int getSpiderDepth()
    {
        return spiderDepth;
    }

    public void setSpiderDepth(int spiderDepth)
    {
        this.spiderDepth = spiderDepth;
    }


    @Override
    public String toString()
    {
        return "SpiderInfo{" +
                "spiderID='" + spiderID + ", spiderStatus='" + spiderStatus + ", spiderDepth='" + spiderDepth + '\'' +
                '}';
    }
}
