package com.resgain.lion.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.resgain.lion.abst.AbstractAction;

/**
 * 功能：拦截指定的返回值为类+方法（与view名字匹配,避免action中注解）、拦截输出日志
 * 日志内容包括操作人及其IP地址、Action方法、耗费时间、相关参数、操作内容
 * @author memphis.guo
 */
public class ResgainInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -4741336719024419577L;

    private static final Logger logger = LoggerFactory.getLogger("ActionExecute");

	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {

        // 如果ACTION执行方法的返回值为METHOD_VIEW则动态修改为:类名(去除尾部的ACTION)-方法名
        invocation.addPreResultListener(new PreResultListener() {
            public void beforeResult(ActionInvocation ai, String ret) {
                if (AbstractAction.RSTPL.equals(ret)) {
                    ai.setResultCode(nameBuild(invocation.getProxy().getMethod()));
                }
                
            }
        });	    
	    
		long s = System.currentTimeMillis();
		String result = null;
		try {
			result = invocation.invoke();
		} catch (Exception e) {
			logger.error("action执行发生异常:{}", e);
			if(invocation.getAction() instanceof AbstractAction){
				result = ((AbstractAction)invocation.getAction()).error(e.getLocalizedMessage());
			}
		}
		
		long time = System.currentTimeMillis() - s;
		HttpServletRequest request = ServletActionContext.getRequest();
		
		logger.info("响应({}@{})请求{}耗时{}ms。请求参数：{}", new Object[]{"demo", request.getRemoteAddr(), request.getRequestURL(), time, request.getQueryString()==null?"无":request.getQueryString()});
		return result;
	}
	
	private String nameBuild(String method)
	{
		String cls = method;
        char[] ca = cls.toCharArray();
        StringBuilder build = new StringBuilder(String.valueOf(ca[0]));
        boolean lower = true;
        for (int i = 1; i < ca.length; i++) {
            char c = ca[i];
            if (Character.isUpperCase(c) && lower) {
                build.append("-");
                lower = false;
            } else if (!Character.isUpperCase(c)) {
                lower = true;
            }
            build.append(c);
        }        
        return build.toString().toLowerCase();
	}
	
//	private String nameBuild(String cls, String method, String actionSuffix)
//	{
//        if (cls.endsWith(actionSuffix)) {
//            cls = cls.substring(0, cls.length() - actionSuffix.length());
//        }
//        if(method!=null && method.length()>0)
//            cls = cls+"-"+method;
//        char[] ca = cls.toCharArray();
//        StringBuilder build = new StringBuilder(String.valueOf(ca[0]));
//        boolean lower = true;
//        for (int i = 1; i < ca.length; i++) {
//            char c = ca[i];
//            if (Character.isUpperCase(c) && lower) {
//                build.append("-");
//                lower = false;
//            } else if (!Character.isUpperCase(c)) {
//                lower = true;
//            }
//
//            build.append(c);
//        }        
//        return build.toString().toLowerCase();
//	}
}
