package com.resgain.lion.actions.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.resgain.lion.abst.AbstractAction;
import com.resgain.lion.beans.DictDataRequest;
import com.resgain.lion.entity.base.DictCode;
import com.resgain.lion.service.base.DictService;

/**
 * 字典数据提供的Action类
 * @author gyl
 */
public class DictAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(DictAction.class);

	private String cond;
    private String id;
    private DictCode dict;

    @Autowired private DictService dictService;
    
	@Override
	public String execute() throws Exception
	{
		List<DictDataRequest> reqs = JSON.parseArray(cond, DictDataRequest.class);
		return json(dictService.getDictDatas(reqs));
	}
	
    //字典信息列表
    public String list() throws Exception
	{
        logger.debug("获取字典信息列表数据");
		return RSTPL;
	}
	
    //字典信息查看
	public String view() throws Exception
	{
        logger.debug("获取字典信息指定ID：{}的详细数据", id);
		return json();
	}
    
    //字典信息保存
    public String save() throws Exception
	{
        logger.debug("保存字典信息数据:");
		return json();
	}    
	
    //字典信息删除方法
	public String del() throws Exception
	{
        logger.warn("删除字典信息数据, ID:{}", id);
		return json();
	}

	public String getCond()
	{
		return cond;
	}
	public void setCond(String cond)
	{
		this.cond = cond;
	}

	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}

	public DictCode getDict()
	{
		return dict;
	}
	public void setDict(DictCode dict)
	{
		this.dict = dict;
	}
}