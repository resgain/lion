package com.resgain.lion.actions.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.resgain.lion.abst.AbstractAction;
import com.resgain.lion.abst.bean.ResultPage;
import com.resgain.lion.entity.base.Employee;
import com.resgain.lion.service.base.EmployeeService;

public class EmployeeAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	
	private String empId; //员工ID
	private ResultPage<Employee> employeeDatas; //员工信息分页数据
	private Employee emp; //员工信息
	
	@Autowired private EmployeeService employeeService;
	
	//保存员工信息
	public String save() throws Exception 
	{
		employeeService.save(emp);
		return success();
	}
	
	//查看员工信息
	public String view() throws Exception 
	{
		return json(employeeService.get(empId));
	}
	
	//删除员工信息
	public String del() throws Exception 
	{
		employeeService.delete(empId);
		return success();
	}
	
	public ResultPage<Employee> getEmployeeDatas(){
		if(employeeDatas==null)
			employeeDatas = employeeService.getPageDatas(getQp());
		return employeeDatas;
	}

	public String getEmpId()
	{
		return empId;
	}
	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public Employee getEmp()
	{
		return emp;
	}
	public void setEmp(Employee emp)
	{
		this.emp = emp;
	}
}