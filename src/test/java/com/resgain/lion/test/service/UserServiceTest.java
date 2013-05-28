package com.resgain.lion.test.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.resgain.lion.abst.AbstractTestCase;
import com.resgain.lion.entity.base.Employee;
import com.resgain.lion.service.base.EmployeeService;

public class UserServiceTest extends AbstractTestCase
{
	@Autowired EmployeeService employeeService;
	
	@Test
	public void updateUserPass(){
		List<Employee> empList = employeeService.getAll();
		for (Employee emp : empList) {
			if(!"cc@cc.com".equalsIgnoreCase(emp.getCode())){
				emp.setPass(emp.getPass().substring(1));
				employeeService.save(emp);
			}
		}
	}
}
