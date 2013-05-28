package com.resgain.lion.abst.bean;

/**
 * 分页查询请求信息类
 * @author memphis.guo
 */
public class QueryPage
{
    private int page;   //页数
    private long start; //起始记录
    private int limit=20; //每页大小
    private String order; //排序
    private String dir; //升序还是降序,主要是根据extjs的需要添加的
    
    public QueryPage(){}

    /**
     * 分页查询类构造方法
     * 分页一般有2种，一是指定页数和每页记录数，另一是指定起始记录和记录数
     * @param 指定页(与起始记录为互斥关系, 如果page值大于0则已page为准，否则以start为准)
     * @param 起始记录
     * @param 记录数限制
     * @param 排序
     */
    public QueryPage(int page, long start, int limit, String order)
    {
        this.page = page;
        this.start = start;
        this.limit = limit;        
        this.order = order;
    }
    
    public String getOrder_()
    {
        if(!isNullOrSpace(order) && !isNullOrSpace(dir))
            return order+" "+dir;
        return order;
    }
    public void setOrder(String order)
    {
        this.order = order;
    }
    public int getPage()
    {
        return page;
    }
    public void setPage(int pageNo)
    {
        this.page = pageNo;
    }
    public int getLimit()
    {
        return limit;
    }
    public void setLimit(int pageSize)
    {
        this.limit = pageSize;
    }

    public long getStart()
    {
        return start;
    }

    public void setStart(int start)
    {
        this.start = start;
    }

    public String getDir()
    {
        return dir;
    }

    public void setDir(String dir)
    {
        this.dir = dir;
    }

    public String getOrder()
    {
        return order;
    }
    
    private boolean isNullOrSpace(String str)
    {
        if (str == null || str.trim().length() < 1)
            return true;
        return false;
    }    
}
