package com.resgain.lion.iface;

import java.util.List;

import com.resgain.lion.entity.base.DictCode;

/**
 * 数据字典数据服务提供接口
 * @author gyl
 */
public interface IDictService
{
    
    /**
     * 取得指定类型的数据字典列表(主要用于数据字典树形显示的时候异步加载数据使用)
     * @param id
     * @return
     */
    public abstract List<DictCode> getCateList(String id);    
    
    /**
     * 获取指定分类和键值的数据字典列表
     * @param 字典分类
     * @param 要获取的数据的父code
     * @param 查询关键字
     * @return
     */
    public abstract List<DictCode> getDataList(String category, String pcode, String keyword);
	
    /**
     * 取得指定类型的字典数据.
     * @param category 字典分类
     * @param code
     * @return
     */
	public abstract DictCode getDictData(String category, String code);	
}
