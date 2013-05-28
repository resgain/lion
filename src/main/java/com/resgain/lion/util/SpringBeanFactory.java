package com.resgain.lion.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanFactory implements ApplicationContextAware
{

    private static ApplicationContext acontext;
    
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException
    {
        acontext = ac;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls)
    {
        return (T)acontext.getBean(String.valueOf(cls.getSimpleName().charAt(0)).toLowerCase() + cls.getSimpleName().substring(1));
    }
    
    public static Object getBean(String beanId)
    {
        return acontext.getBean(beanId);
    } 

	public static ApplicationContext getAcontext()
	{
		return acontext;
	}

}
