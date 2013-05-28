package com.resgain.lion.abst.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页信息结构类
 * @author memphis.guo
 */
public class ResultPage<T>
{
    private List<T> resultList;
    private int allRows; //总行数
    private int maxPage; //最大页
    private int currentPage; //当前页
    private int pageRows; //每页的行数

    @SuppressWarnings("unused")
	private ResultPage() {}

    /**
     * 分页信息构造
     * @param result 信息列表
     * @param 总行数
     * @param 当前页
     * @param 每页的行数
     */
    public ResultPage(List<T> result, long ar1, int cp, int pr) {
        this.resultList = result;
        this.allRows = (int)ar1;
        this.maxPage = (allRows - 1) / pr + 1;
        if(cp > maxPage)
            this.currentPage = maxPage;
        else
            this.currentPage = cp;
        this.pageRows = pr;
    }
    
    public ResultPage(List<T> result) {
        this.resultList = result;
        this.allRows = result.size();
        this.maxPage = (int) ((allRows - 1) / 99999 + 1);
        this.currentPage = 1;
        this.pageRows = 99999;
    }   

    /**
     * 取得总记录数
     * @return 总记录数
     */
    public long getAllRows()
    {
        return allRows;
    }

    /**
     * 取得当前页数
     * @return 当前页数
     */
    public int getCurrentPage()
    {
        return currentPage;
    }

    /**
     * 取得总页数
     * @return 总页数
     */
    public int getMaxPage()
    {
        return maxPage;
    }

    /**
     * 取得每页的记录数
     * @return 每页的记录数
     */
    public int getPageRows()
    {
        return pageRows;
    }

    /**
     * 取得当前记录集
     * @return 当前记录集
     */
    public List<T> getResultList()
    {
        return resultList;
    }

    /**
     * 清除记录集
     */
    public void clear()
    {
        resultList.clear();
    }
    
	/**
	 * 根据最大页返回页列表
	 * @param maxPage
	 * @return
	 */
    public List<Integer> getPages()
    {
        int currPage = this.currentPage;
        int count_ = 10;
        int mPage = maxPage <= count_ ? maxPage : count_;
        List<Integer> ret = new ArrayList<Integer>();
        if(maxPage < count_ || maxPage > count_ && currPage < count_ / 2 - 1)
        {
            for(int i = 1; i <= mPage; i++)
                ret.add(new Integer(i));

        } else
        {
            int starPage = (currPage - count_ / 2) + 1;
            if(starPage<1)
            	starPage=1;
            if(starPage + mPage <= maxPage)
            {
                for(int i = 1; i <= mPage; i++)
                    ret.add(new Integer(starPage + i));

            } else
            {
                for(int i = 1; i <= mPage; i++)
                    ret.add(new Integer((starPage + i) - ((starPage + mPage) - maxPage)));

            }
        }
        return ret;
    }     
}
