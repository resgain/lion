package com.resgain.lion.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.resgain.lion.security.bean.ResgainUser;

/**
 * 操作环境信息，主要是为各业务方法提供当前登录用户的一些信息，并为测试方法单独提供一些相关数据
 * @author memphis.guo
 */
public class SessionUtil 
{
	public final static String TEST_USER_ID="99999999999999999999999999999999";
	
	private SessionUtil(){}
	
	//取得当前登录的用户ID
	public static String getLoginUserId() {
		ResgainUser user = getLoginUser();
		return user==null?TEST_USER_ID:user.getId();
	}

	/**
	 * 取得当前登录用户信息
	 * @return
	 */
	public static ResgainUser getLoginUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			Object obj = auth.getPrincipal();
			if (obj!=null && obj instanceof UserDetails)
				return (ResgainUser) obj;
		}
		return null;
	}	
	
	/**
	 * 判断当前用户是否具有某个授权, 参数可以是逗号分隔的权限名称，如果拥有其中一个则返回true
	 * @param auth
	 * @return
	 */
	public static boolean hasAuth(String auth){
		ResgainUser user = getLoginUser();
		if(user!=null)
			return user.hasAuth(auth);
		return false;
	}
}
