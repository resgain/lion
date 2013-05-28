package com.resgain.lion.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.resgain.lion.abst.PersistentObject;
import com.resgain.lion.annotation.Desc;
import com.resgain.lion.annotation.Label;

/**
 * 员工信息实体类, 创建于2013-05-07 09:59
 * @author gyl
 */
@Entity(name="BASE_EMPLOYEE")
@Desc("员工信息")
public class Employee extends PersistentObject
{
    @Column(name="EMP_CODE")
    @Label(name="代码", nullFlag=false)
    private String code;
    
    @Column(name="EMP_PASS")
    @Label(name="密码", nullFlag=false)
    private String pass;
    
    @Column(name="EMP_NAME")
    @Label(name="姓名", nullFlag=false)
    private String name;
    
    @Column(name="EMP_STATUS")
    @Label(name="状态", nullFlag=false)
    private int status;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getPass()
	{
		return pass;
	}

	public void setPass(String pass)
	{
		this.pass = pass;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
}