package com.resgain.lion.init.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.resgain.lion.abst.AbstractTestCase;
import com.resgain.lion.entity.base.DictCode;
import com.resgain.lion.service.base.DictService;

public class DictServiceTest extends AbstractTestCase
{
	@Autowired DictService dictService;
	
	@Test
	public void insertDict(){
		initEmployeeStatus();
	}
	
	private void initEmployeeStatus(){
		String category = "EMPLOYEE_STATUS";
		dictService.save(new DictCode(category, "0", null, "正常"));
		dictService.save(new DictCode(category, "1", null, "密码锁定"));
		dictService.save(new DictCode(category, "3", null, "已离职"));
	}
}
