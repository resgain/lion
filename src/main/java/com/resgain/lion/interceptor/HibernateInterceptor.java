package com.resgain.lion.interceptor;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.resgain.lion.abst.AbstractObject;
import com.resgain.lion.abst.PersistentObject;
import com.resgain.lion.service.base.ModifyLogService;
import com.resgain.lion.util.ResgainUtil;
import com.resgain.lion.util.SessionUtil;
import com.resgain.lion.util.SpringBeanFactory;

/**
 * Hibernate拦截器，主要是自动设置一些常用字段值，不用每个程序自己处理
 * @author memphis.guo
 */
public class HibernateInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		if (entity instanceof AbstractObject) {
			for (int i = 0; i < propertyNames.length; i++) {
				if ("editTime".equals(propertyNames[i])) {
					currentState[i] = ResgainUtil.getToday();
				} else if ("editorId".equals(propertyNames[i])) {
					currentState[i] = SessionUtil.getLoginUserId();
				}
			}
			if(entity instanceof PersistentObject){
				SpringBeanFactory.getBean(ModifyLogService.class).saveDiff((PersistentObject) entity);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (entity instanceof PersistentObject) {
			for (int i = 0; i < propertyNames.length; i++) {
				if ("createTime".equals(propertyNames[i])) {
					state[i] = ResgainUtil.getToday();
				} else if ("creatorId".equals(propertyNames[i])) {
					state[i] = SessionUtil.getLoginUserId();
				}
			}
			return true;
		}
		return false;
	}
}
