package com.resgain.lion.service.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Service;

import com.resgain.lion.abst.AbstractService;
import com.resgain.lion.beans.DictDataRequest;
import com.resgain.lion.entity.base.DictCode;
import com.resgain.lion.iface.IDictService;
import com.resgain.lion.util.SpringBeanFactory;

/**
 * 字典业务方法类
 * @author gyl
 */
@Service
public class DictService extends AbstractService<DictCode> implements IDictService
{
	/**
	 * 根据
	 * @param ddr
	 * @return
	 */
	public Map<String, Object> getDictDatas(List<DictDataRequest> condList)
	{
		Map<String, Object> ret = new HashMap<String, Object>();
		for (DictDataRequest ddr : condList) {
			if(!StringUtil.isBlank(ddr.getBeanId())) {
				List<DictCode> tmp = ((IDictService)SpringBeanFactory.getBean(ddr.getBeanId())).getDataList(ddr.getCategory(), ddr.getPcode(), null);
				ret.put(ddr.getKey(), tmp);
			} else if(!StringUtil.isBlank(ddr.getCategory())){
				ret.put(ddr.getKey(), getDataList(ddr.getCategory(), ddr.getPcode(), null));
			} else {
				//非法请求,不处理
			}
		}
		return ret;
	}

	@Override
	public List<DictCode> getCateList(String id)
	{
		return null;
	}

	@Override
	public List<DictCode> getDataList(String category, String pcode, String keyword)
	{
		return getList(null, "a.category=?", category);
	}

	@Override
	public DictCode getDictData(String category, String code)
	{
		return null;
	}
}