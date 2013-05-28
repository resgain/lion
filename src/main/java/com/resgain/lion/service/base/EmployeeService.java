package com.resgain.lion.service.base;

import org.springframework.stereotype.Service;

import com.resgain.lion.abst.AbstractService;
import com.resgain.lion.abst.bean.QueryPage;
import com.resgain.lion.abst.bean.ResultPage;
import com.resgain.lion.entity.base.Employee;

@Service
public class EmployeeService extends AbstractService<Employee>
{
	public ResultPage<Employee> getPageDatas(QueryPage qp){
		return getPage(qp, null, null);
	}

	public Employee getEmpByCode(String code) {
		return get(null, "a.code=?", code);
	}
	
}
