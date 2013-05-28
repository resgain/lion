package com.resgain.lion.util;

import java.util.Date;

/**
 * View层格式化数据显示方法
 * @author memphis.guo
 */
public class ViewUtil 
{
	private static ViewUtil instance = new ViewUtil();
	
	private ViewUtil(){}
	
	public static ViewUtil getInstance()
	{
		return instance;
	}

	//格式化日期时间
	public String fdt(Date date)
	{
		return ResgainUtil.getDateTimeString(date);
	}	
	
	//格式化日期
	public String fd(Date date)
	{
		return ResgainUtil.getDateString(date);
	}
	
	//格式化时间
	public String ft(Date date)
	{
		return ResgainUtil.getTimeString(date);
	}	
	
	//格式化金额
	public String fm(Number n)
	{
		return ResgainUtil.foraNumber(n, ConfigUtil.getValue("money-format", "##,###,###,###.00"));
	}
	
	//格式化数字数据
	public String fn(Number n)
	{
		return ResgainUtil.foraNumber(n, ConfigUtil.getValue("number-format", "###.##"));
	}
	
	//判断当前用户是否具有某个授权, 参数可以是逗号分隔的权限名称，如果拥有其中一个则返回true
//	public boolean hasAuth(String auth){
//		return SessionUtil.hasAuth(auth);
//	}
}
