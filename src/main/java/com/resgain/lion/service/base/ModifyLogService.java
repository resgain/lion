package com.resgain.lion.service.base;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resgain.lion.abst.PersistentObject;
import com.resgain.lion.entity.base.ModifyLog;
import com.resgain.lion.util.JSONUtil;
import com.resgain.lion.util.ResgainUtil;
import com.resgain.lion.util.SessionUtil;

@Service
public class ModifyLogService
{
	@Autowired SessionFactory sessionFactory;
	
	private static Logger logger = LoggerFactory.getLogger(ModifyLogService.class);

	//保存两个实例的差异
	@SuppressWarnings("unchecked")
	public <T extends PersistentObject> void saveDiff(T no)
	{
		Session session = sessionFactory.openSession();
		try {
			T old = (T) session.createQuery(" from " + no.getClass().getName() + " where id = :id ").setParameter("id", no.getId()).uniqueResult();
			session.evict(old);
			Transaction ts = session.beginTransaction();
			ModifyLog log = new ModifyLog(no.getClass().getName(), no.getId(), JSONUtil.toJson(compareBeans(no, old)), old.getEditorId()==null?old.getCreatorId():old.getEditorId(), old.getEditTime()==null?old.getCreateTime():old.getEditTime());
			session.save(log);
			ts.commit();
		} catch (Exception e) {
			logger.error("保存修改痕迹出错，操作人ID:{}, 操作时间：{} ,出错原因：{}", SessionUtil.getLoginUserId(), ResgainUtil.getToday(), e);
		} finally {
			session.close();
		}
	}
	
    public Map<String, Object> compareBeans(Object newBean, Object oldBean) throws Exception 
    {  
	    if(newBean.getClass() != oldBean.getClass())
	    	return null;
	    Map<String, Object> changesArr = new LinkedHashMap<String, Object>();  
	    
	    List<Field> fields = getAllFields(newBean.getClass());
	    for (Field field : fields) {
	    	Object oldValue = PropertyUtils.getProperty(oldBean, field.getName());  
		    Object newValue = PropertyUtils.getProperty(newBean, field.getName());
		    if( oldValue != newValue ) {// Ignores when both are "null"  
		        if( ( (oldValue != null) && !oldValue.equals(newValue) )   || ( (newValue != null) && !newValue.equals(oldValue) ) )
		        	changesArr.put(field.getName(), oldValue);
		    }
		}
	    return changesArr;  
    }  
	
    private static List<Field> getAllFields(Class<?> clazz)
    {
        List<Field> ret = new LinkedList<Field>();
        if(clazz!=PersistentObject.class){
            ret.addAll(getAllFields(clazz.getSuperclass()));
            ret.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        return ret;
    }
}