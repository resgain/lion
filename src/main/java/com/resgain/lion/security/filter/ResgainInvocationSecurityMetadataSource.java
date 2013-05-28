package com.resgain.lion.security.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * Spring Security的资源源数据定义，即定义某一资源可以被哪些角色访问
 * @author memphis.guo
 */
public class ResgainInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource 
{
	
//	private static Logger logger = LoggerFactory.getLogger(ResgainInvocationSecurityMetadataSource.class);
	
//	private UrlMatcher urlMatcher = new AntUrlPathMatcher();;
//	private static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

	
	public ResgainInvocationSecurityMetadataSource()
	{
		loadResourceDefine();
	}

	private void loadResourceDefine() 
	{
//		File[] files = new File(GlobalParameter.getAppRealPath()).listFiles();
//		File file = null;
//		try {
//			for (int i = 0; i < files.length; i++) {
//				file = new File(files[i].getPath()+File.separatorChar+"auth.properties");
//				if(file.exists())
//				{
//					FileInputStream fin = new FileInputStream(file);
//					Properties p = new Properties();
//					p.load(fin);
//					fin.close();
//					
//					Iterator<Entry<Object, Object>> it = p.entrySet().iterator();
//					while(it.hasNext())
//					{
//						Entry<Object, Object> entry = it.next();
//						String key = entry.getKey().toString();
//						if(key.indexOf('.')<0)
//							key = key+".do";
//						if(!key.startsWith("/"))
//							key="/"+files[i].getName().toLowerCase()+"/"+key;
//						resourceMap.put(key, getAuths(entry.getValue().toString()));
//					}
//					
//				}
//			}
//		} catch (Exception e) {
//			logger.error("授权文件[{}]读取错误！原因：{}", file.getPath(), e);
//			System.exit(1);
//		}
	}


	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException 
	{
//		String url = ((FilterInvocation)object).getRequestUrl();
//		Iterator<String> ite = resourceMap.keySet().iterator();
//		while (ite.hasNext()) {
//			String t = ite.next();
//			if (urlMatcher.pathMatchesUrl(url, t)) {
//				return resourceMap.get(t);
//			}
//		}
		return getAuths("ROLE_USER");
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
	
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}
	
	private Collection<ConfigAttribute> getAuths(String auths)
	{
		Collection<ConfigAttribute> ret = new ArrayList<ConfigAttribute>();
		if(auths!=null)
		{
			String tmps[] = auths.split("[,;]");
			for (String s : tmps) {
				ret.add(new SecurityConfig(s));
			}
		}
		return ret;
	}
}