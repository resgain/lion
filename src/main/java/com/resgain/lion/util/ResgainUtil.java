package com.resgain.lion.util;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * 常用的工具方法类
 * @author memphis.guo
 */
public class ResgainUtil
{
    /**
     * 取得当前系统日期，主要是保证在多个服务器之间获取的日期是一致的-暂时采用new Date
     * @return
     */
    public static Date getToday() {
        return new Date();
    }    	
	
    public static String getDateTimeString(Date date)
    {
        return convert(date, ConfigUtil.getValue("datetime-format", "yyyy-MM-dd HH:mm:ss"));
    }
    
    public static String getDateString(Date date)
    {
        return convert(date, ConfigUtil.getValue("date-format", "yyyy-MM-dd"));
    }
    
    public static String getTimeString(Date date)
    {
        return convert(date, ConfigUtil.getValue("time-format", "HH:mm:ss"));
    }   
    
    private static String convert(Date date, String style)
    {
    	if(date==null || style==null)
    		return null;
    	return new SimpleDateFormat(style).format(date);
    }

	public static boolean isNS(String str)
	{
	    if (str == null || str.trim().length() < 1)
	        return true;
	    return false;
	}

	public static String str(String... str)
	{
	    StringBuilder sb = new StringBuilder();
	    for (String s : str) {
	        sb.append(s);
	    }
	    return sb.toString();
	}
	
	public static String foraNumber(Number num, String parten)
	{
		if(parten==null)
			parten = "####.000";
		DecimalFormat df1 = new DecimalFormat(parten);
		return df1.format(num);
	}

	public static String parseParam(String str, Map<String, Object> param)
	{
		try {
			VelocityContext velocityContext = new VelocityContext(param);
			velocityContext.put("vt", ViewUtil.getInstance());
			StringWriter sw = new StringWriter();
			Velocity.evaluate(velocityContext, sw, "LOG", str);
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}	
	}
}