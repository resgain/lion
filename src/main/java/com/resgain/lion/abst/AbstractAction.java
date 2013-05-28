package com.resgain.lion.abst;

import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.resgain.lion.Constants;
import com.resgain.lion.abst.bean.QueryPage;
import com.resgain.lion.util.JSONUtil;
import com.resgain.lion.util.ResgainUtil;
import com.resgain.lion.util.ViewUtil;

/**
 * sturts2的统一action父类
 * @author memphis.guo
 */
public class AbstractAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;

    final public static String RSTPL="RSTPL"; //此类型表示为匹配与方法同名的模版，主要是为了避免写action注解
    
    private QueryPage qp = new QueryPage(0, 0, 10, null); //查询页数\排序等请求信息
        
    public String success(Object ...objs) throws Exception{
    	outJsonMsg(result(true, "操作成功!", objs));
    	return null;
    }
    
    public String error(String msg, Object ...objs) throws Exception{
    	outJsonMsg(result(false, msg, objs));
    	return null;
    }
    
    public String json(Object ...objs) throws Exception{
    	if(objs==null)
    		outJsonMsg(null);
    	else if(objs.length==1)
    		outJsonMsg(JSONUtil.toJson(objs[0]));
    	else
    		outJsonMsg(JSONUtil.toJson(objs));
    	return null;
    }
    
	private String parameter_;
	public String getAction(String singleId, int page) throws Exception
	{
		if (ResgainUtil.isNS(parameter_)) {
			Enumeration<String> keys = ServletActionContext.getRequest().getParameterNames();
			StringBuffer sb = new StringBuffer();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				if ("qp.page".equalsIgnoreCase(key) || Constants.ALONE_REQUEST.equalsIgnoreCase(key))
					continue;
				sb.append("&").append(key).append("=").append(URLEncoder.encode(ServletActionContext.getRequest().getParameter(key), "UTF-8"));
			}
			parameter_ = sb.toString();
		}
		String parameter = parameter_;
		if (!ResgainUtil.isNS(singleId))
			parameter = parameter + "&" + Constants.ALONE_REQUEST + "=" + singleId;
		return ServletActionContext.getRequest().getRequestURI() + "?qp.page=" + page + parameter;
	}   
	
    private String result(boolean success, String msg, Object... objs) {
    	Map<String, Object> ret = new HashMap<String, Object>();
    	ret.put("success", success);
    	ret.put("message", msg);
    	if(objs==null)
    		ret.put("data", null);
    	else if(objs.length==1)
    		ret.put("data", objs[0]);
    	else
    		ret.put("data", objs);
    	return JSONUtil.toJson(ret);
    }
    
    private void outJsonMsg(String msg) throws Exception
    {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.reset();
     	response.setLocale(new Locale(new String("zh"), new String("CN")));
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("text/plain; charset=utf-8");
    	ServletOutputStream out = response.getOutputStream();
        OutputStreamWriter ow = new OutputStreamWriter(out,"UTF-8"); 
        ow.append(msg);
        ow.flush();
        ow.close();
        out.close();
    }


    public QueryPage getQp()
    {
        return qp;
    }
    public void setQp(QueryPage qp)
    {
        this.qp = qp;
    }
    
    public ViewUtil getVt()
    {
    	return ViewUtil.getInstance();
    }
    
    public String getWebapp()
    {
        return ServletActionContext.getRequest().getContextPath();
    }
}