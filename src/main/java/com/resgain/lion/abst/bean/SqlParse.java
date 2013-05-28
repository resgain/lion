package com.resgain.lion.abst.bean;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.resgain.lion.util.ViewUtil;

/**
 * Hql或Sql串解析，主要是简化程序中的拼串。
 * @author memphis.guo
 */
public class SqlParse 
{
	private static Logger logger = LoggerFactory.getLogger(SqlParse.class);
	
	private String osql;
	private Map<String, Object> oparams = new HashMap<String, Object>();

	public SqlParse(String sql){
		this.osql = sql;
	}

	public SqlParse bind(String key, Object param)
	{
		this.oparams.put(key, param);
		return this;
	}
	
	public SqlParam parse() {
		try {
			VelocityContext velocityContext = new VelocityContext(oparams);
			velocityContext.put("vt", ViewUtil.getInstance());
			StringWriter sw = new StringWriter();
			Velocity.evaluate(velocityContext, sw, "LOG", osql);
			return parseParam(sw.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("解析sql[{}]发生错误", osql, e);
			throw new RuntimeException("解析sql发生错误");
		}		
	}
	
	private SqlParam parseParam(String str)
	{
		List<Object> po = new ArrayList<Object>();
		Pattern pattern = Pattern.compile(":([a-zA-Z]+[a-z_0-9A-Z\\.\\[\\]]+)");
		logger.debug(str);
		Matcher m = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while(m.find())
		{
			logger.debug(m.group(1)+"\t:\t"+MVEL.eval(m.group(1), oparams));
			po.add(MVEL.eval(m.group(1), oparams));
			m.appendReplacement(sb, "?");
		}
		m.appendTail(sb);		
		return new SqlParam(sb.toString(), po);
	}

}

