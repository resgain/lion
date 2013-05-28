package com.resgain.lion.abst;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.resgain.lion.abst.bean.QueryPage;
import com.resgain.lion.abst.bean.ResultPage;
import com.resgain.lion.util.ResgainUtil;

/**
 * 业务服务基础方法类
 * @author memphis
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractService<T extends AbstractObject>
{
	@Autowired SessionFactory sessionFactory;
	protected Class<T> clazz;

	public AbstractService() {
		Type genType = getClass().getGenericSuperclass();  
		if(genType instanceof ParameterizedType){
	        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
	        this.clazz = (Class) params[0];  
		}
	}

	//获取session
	protected Session getSession()
	{
		return this.sessionFactory.getCurrentSession();
	}

	//获取数据列表
	public List<T> getAll(){
		return getList(null, null);
	}
	
	//获取指定信息
	public T get(String id)
	{
		Assert.notNull(id);
		return (T) getSession().get(clazz, id);
	}

	//保存实体信息
	public String save(T po)
	{
		Assert.notNull(po);
		if (ResgainUtil.isNS(po.getId())) {
			po.setId(null);
			getSession().save(po);
		} else
			getSession().update(po);
		return po.getId();
	}

	//删除实体信息(逻辑删除)
	public void delete(T po)
	{
		if(po!=null)
			get(po.getId()).setDelFlag(true);
	}

	//批量逻辑删除数据信息。
	public void delete(String... ids)
	{
		Assert.notNull(ids);
		for (int i = 0; i < ids.length; i++) { // 如果不需要考虑缓存同步问题可以改为直接的update语句。
			T tmp = get(ids[i]);
			if(tmp!=null)
				tmp.setDelFlag(true);
		}
	}

	//清除实体信息(彻底物理删除-一般不建议使用)
	@Deprecated
	protected void clear(AbstractObject po)
	{
		Assert.notNull(po);
		getSession().delete(po);
	}
	
	//根据查询HQL与参数列表创建Query对象.
	protected Query query(String hql, Object... params)
	{
		Query query = getSession().createQuery(hql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}

	//获得指定条件的实体信息
	protected T get(String join, String where, Object... params)
	{
		return (T) query(getHql(clazz, join, where), params).uniqueResult();
	}

	//取得指定条件下的数据列表
	protected List<T> getList(String join, String where, Object... params)
	{
		String tmp = "";
		if (where != null) {
			if (where.toUpperCase().indexOf(" ORDER ") > 0 && where.toUpperCase().indexOf(" BY ") > 0) {
				tmp = where.substring(where.toUpperCase().indexOf(" ORDER "));
				where = where.substring(0, where.toUpperCase().indexOf(" ORDER "));
			}
		}
		List<T> ret = (List<T>) query(getHql(clazz, join, where) + tmp, params).list();
		if (!ResgainUtil.isNS(join))
			return new ArrayList(new HashSet<T>(ret));
		return ret;
	}

	//取得指定条件下的分页数据
	protected ResultPage<T> getPage(QueryPage qp, String join, String where, Object... params)
	{
		String hql = getHql(clazz, join, where);
		String tmp = hql;
		if (!ResgainUtil.isNS(qp.getOrder_()) && tmp.toUpperCase().indexOf(" ORDER ") > 0 && tmp.toUpperCase().indexOf(" BY ") > 0)
			tmp = tmp.substring(0, tmp.toUpperCase().indexOf(" ORDER ")) + " order by " + qp.getOrder_();
		else if (!ResgainUtil.isNS(qp.getOrder_()))
			tmp = tmp + " order by " + qp.getOrder_();
		long count = getCount(hql, params);
		List<T> list = new ArrayList<T>();
		if (count > 0) {
			Query query = query(tmp, params);
			if (qp.getPage() > 0)
				query.setFirstResult((int) ((qp.getPage() - 1) * qp.getLimit()));
			else {
				query.setFirstResult((int) qp.getStart());
				qp.setPage((int) (qp.getStart() / qp.getLimit() + 1));
			}
			query.setMaxResults((int) qp.getLimit());
			list = query.list();
		}
		return new ResultPage(list, count, qp.getPage(), qp.getLimit());
	}

	private String getHql(Class<T> cls, String join, String where)
	{
		return ResgainUtil.str("from ", cls.getName(), " a", getJoinString(join), " where a.delFlag=false", ResgainUtil.isNS(where) ? "" : " and (" + where + ")");
	}

	//取得指定HQL语句的结果记录数量
	private long getCount(String hql, Object... params)
	{
		return ((Long) query(getCountSQL(hql), params).list().get(0)).longValue();
	}

	private String getCountSQL(String hql)
	{
		String tmp = "select count(*) " + hql.substring(hql.toLowerCase().indexOf("from "), hql.length());
		if (tmp.toLowerCase().lastIndexOf(" order ") > 0)
			tmp = tmp.substring(0, tmp.toLowerCase().lastIndexOf(" order "));
		return tmp;
	}

	private String getJoinString(String str)
	{
		if (ResgainUtil.isNS(str))
			return "";
		StringBuffer ret = new StringBuffer();
		for (String t : str.split(",")) {
			ret.append(" left join fetch a.").append(t.trim());
		}
		return ret.toString();
	}
}