package com.my.core.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.my.core.dao.BaseDAO;
import com.my.core.model.BaseModel;
import com.my.core.page.IPage;
import com.my.core.page.Paginatable;
import com.my.core.page.PaginatedList;
import com.my.core.page.Paginator;
import com.mysql.jdbc.CallableStatement;

@Repository("baseDAO")
public class SimpleDAOHibernate   implements BaseDAO{

	protected SessionFactory sessionFactory;
	//这个是怎么注入的，在applicationContext.xml中有一个bean  sessionFactory
	///在applicationContext.xml中已经定义了，这里会自动注入看下面的autowired就是自动注入。
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private boolean cacheQueries = false;

	// private String queryCacheRegion;

	private int fetchSize = 0;

	private int maxResults = 0;

	public boolean isCacheQueries() {
		return cacheQueries;
	}

	public void setCacheQueries(boolean cacheQueries) {
		this.cacheQueries = cacheQueries;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	// public SqlParser sqlParser = null;

	public void pageQuery(String queryString, String[] paramNames, Object[] values, IPage page) {
		Query query = this.createQuery(queryString, paramNames, values);
		// populate page object
		if (page.getPageNO() == 0) {
			page.setPageNO(1);

		}
		page.setTotalCount(this.getTotalCounts(queryString, paramNames, values));
		// firstResult: the index of the first result object to be retrieved
		query.setFirstResult((page.getPageNO() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		page.setRetList(query.list());
	}

	/**
	 * 根据条件查找实体，如果存在多个符合条件的实体，只返回一个；
	 */
	public Object getObjectByQuery(final String queryString, String[] paramNames, Object[] values) {
		if (paramNames != null && values != null && paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}
		Session session = getSession();
		Query q = session.createQuery(queryString);
		for (int i = 0; i < paramNames.length && i < values.length; i++) {
			q = q.setParameter(paramNames[i], values[i]);
		}
		q.setMaxResults(1);
		List<?> list = q.list();
		return ((list == null || list.size() == 0) ? null : list.get(0));
	}

	/**
	 * 根据条件查找实体，如果存在多个符合条件的实体，返回所有结果；
	 */
	public Object getObjectListByQuery(final String queryString, String[] paramNames, Object[] values) {
		if (paramNames != null && values != null && paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}
		Session session = getSession();
		Query q = session.createQuery(queryString);
		for (int i = 0; i < paramNames.length && i < values.length; i++) {
			q = q.setParameter(paramNames[i], values[i]);
		}
		List<?> list = q.list();
	
		return ((list == null || list.size() == 0) ? null : list);
	}
	//stat和limit来获取对应位置的结果
	public Object getObjectLimitByQuery(final String queryString, String[] paramNames, Object[] values, final int stat, final int limit){
		if (paramNames != null && values != null && paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}
		Session session = getSession();
		Query q = session.createQuery(queryString);
		for (int i = 0; i < paramNames.length && i < values.length; i++) {
			q = q.setParameter(paramNames[i], values[i]);
		}
		q.setFirstResult(stat);
		q.setMaxResults(limit);
		
		List<?> list = q.list();
	
		return ((list == null || list.size() == 0) ? null : list);
	}
	/**
	 *根据实体的类和id获取对象 Return the persistent instance of the given entity class
	 * with the given identifier, or null if there is no such persistent
	 * instance. (If the instance is already associated with the session, return
	 * that instance. This method never returns an uninitialized instance.)
	 * 
	 * @param clazz
	 *            a persistent class
	 * @param id
	 *            an identifier
	 * 
	 * @return a persistent instance or null
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> entityClass, Serializable id) {
		Session session = getSession();
		return (T) session.get(entityClass, id);
	}

	/**
	 * 根据实体名称和id获取对象 Return the persistent instance of the given named entity
	 * with the given identifier, or null if there is no such persistent
	 * instance. (If the instance is already associated with the session, return
	 * that instance. This method never returns an uninitialized instance.)
	 * 
	 * @param entityName
	 *            the entity name
	 * @param id
	 *            an identifier
	 * 
	 * @return a persistent instance or null
	 */
	public Object get(String entityName, Serializable id) {
		Session session = getSession();
		return session.get(entityName, id);
	}

	/**
	 * 与get方法的区别：load方法不会初始化实例的关联对象
	 */
	@SuppressWarnings("unchecked")
	public <T> T load(Class<?> entityClass, Serializable id) {
		Session session = getSession();
		return (T) session.load(entityClass, id);
	}

	public Object load(String entityName, Serializable id) {
		Session session = getSession();
		return session.load(entityName, id);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> loadAll(Class<T> entityClass) {
		Session session = getSession();
		Criteria crit = session.createCriteria(entityClass);
		return crit.list();
	}

	public boolean contains(Object entity) {
		Session session = getSession();
		return session.contains(entity);
	}

	/**
	 * Remove this instance from the session cache. Changes to the instance will
	 * not be synchronized with the database. This operation cascades to
	 * associated instances if the association is mapped with
	 * <tt>cascade="evict"</tt>.
	 * 
	 * @param object
	 *            a persistent instance
	 */
	public void evict(Object entity) {
		Session session = getSession();
		session.evict(entity);
	}

	public Serializable save(Object entity) {
		Session session = getSession();
		return session.save(entity);
	}

	public Serializable save(String entityName, Object entity) {
		Session session = getSession();
		return session.save(entityName, entity);
	}

	public void update(Object entity) {
		Session session = getSession();
		session.update(entity);
	}

	public void update(String entityName, Object entity) {
		Session session = getSession();
		session.update(entityName, entity);
	}

	public void saveOrUpdate(Object entity) {
		Session session = getSession();
		session.saveOrUpdate(entity);
	}

	public void saveOrUpdate(String entityName, Object entity) {
		Session session = getSession();
		session.saveOrUpdate(entityName, entity);
	}

	public Object merge(Object entity) {
		Session session = getSession();
		return session.merge(entity);
	}

	public Object merge(String entityName, Object entity) {
		Session session = getSession();
		return session.merge(entityName, entity);
	}

	public void delete(Object entity) {
		Session session = getSession();
		session.delete(entity);
	}

	public void flush() {
		Session session = getSession();
		session.flush();
	}

	public void clear() {
		Session session = getSession();
		session.clear();
	}

	public List<?> find(String queryString) {
		Session session = getSession();
		return session.createQuery(queryString).list();
	}

	public List<?> find(String queryString, Object value) {
		Session session = getSession();
		Query query = session.createQuery(queryString).setParameter(0, value);

		return query.list();
	}

	public List<?> find(String queryString, Object[] values) {
		Session session = getSession();
		Query query = session.createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query.list();
	}

	public List<?> findByNamedParam(String queryString, String paramName, Object value) {
		Session session = getSession();
		Query query = session.createQuery(queryString).setParameter(paramName, value);

		return query.list();
	}

	public List<?> findByNamedParam(String queryString, String[] paramNames, Object[] values) {
		if (paramNames != null && values != null && paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}
		Session session = getSession();
		Query query = session.createQuery(queryString);
		for (int i = 0; i < values.length && i < paramNames.length; i++) {
			query.setParameter(paramNames[i], values[i]);
		}
		return query.list();
	}

	public List<?> findByNamedQuery(String queryName) {
		Session session = getSession();
		return session.getNamedQuery(queryName).list();
	}

	public List<?> findByNamedQuery(String queryName, Object value) {
		Session session = getSession();
		return session.getNamedQuery(queryName).setParameter(0, value).list();
	}

	public List<?> findByNamedQuery(String queryName, Object[] values) {
		Session session = getSession();
		Query query = session.getNamedQuery(queryName);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}

		return query.list();
	}

	public List<?> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value) {
		Session session = getSession();
		Query query = session.getNamedQuery(queryName);
		query.setParameter(paramName, value);
		return query.list();
	}

	public List<?> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values) {
		if (paramNames != null && values != null && paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}
		Session session = getSession();
		Query query = session.getNamedQuery(queryName);
		for (int i = 0; i < values.length && i < paramNames.length; i++) {
			query.setParameter(paramNames[i], values[i]);
		}
		return query.list();
	}

	public int getTotalCounts(String queryString, String[] paramNames, Object[] values) {
		Query query = this.createQuery(totalHqlParser(queryString), paramNames, values);
		List<?> list = query.list();
		if (list == null)
			return 0;
		else
			return Integer.parseInt(list.get(0).toString());

	}

	public int getTotalCounts(String queryString, Map<?, ?> params) {
		Query query = this.createQuery(totalHqlParser(queryString), params);
		List<?> list = query.list();
		if (list == null)
			return 0;
		else
			return Integer.parseInt(list.get(0).toString());

	}

	protected Query createQuery(String queryString, String[] paramNames, Object[] values) {

		if (paramNames != null && values != null && paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}

		Session session = getSession();
		Query queryObject = session.createQuery(queryString);
		if (paramNames != null && values != null) {
			String key = null;
			Object value = null;
			for (int i = 0, size = paramNames.length; i < size; i++) {
				key = paramNames[i];
				value = values[i];
				if (value instanceof Collection) {
					queryObject.setParameterList(key, (Collection<?>) value);
				} else if (value instanceof Object[]) {
					queryObject.setParameterList(key, (Object[]) value);
				} else {
					queryObject.setParameter(key, value);
				}
			}
		}
		return queryObject;
	}

	protected Query createQuery(String queryString, Map<?, ?> params) {
		Session session = getSession();
		Query query = session.createQuery(queryString);
		if (params != null) {

			Iterator<?> it = params.keySet().iterator();
			String key = null;
			Object value = null;
			while (it.hasNext()) {
				key = it.next().toString().trim();
				value = params.get(key);
				if (value instanceof Collection) {
					query.setParameterList(key, (Collection<?>) value);
				} else if (value instanceof Object[]) {
					query.setParameterList(key, (Object[]) value);
				} else {
					query.setParameter(key, value);
				}
			}
		}
		return query;
	}

	public int bulkUpdate(String queryString) {
		return bulkUpdate(queryString, (Object[]) null);
	}

	public int bulkUpdate(String queryString, Object value) {
		return bulkUpdate(queryString, new Object[] { value });
	}

	public int bulkUpdate(String queryString, Object[] values) {
		Session session = getSession();
		Query queryObject = session.createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject.executeUpdate();
	}
	
	public int bulkUpdate(String queryString, String paramName, Object value) {
		Session session = getSession();
		Query q = session.createQuery(queryString);
		q = q.setParameter(paramName, value);
		return q.executeUpdate();
	}

	public int bulkUpdate(String queryString, String[] paramNames, Object[] values) {
		if (paramNames != null && values != null && paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}
		Session session = getSession();
		Query q = session.createQuery(queryString);
		for (int i = 0; i < paramNames.length && i < values.length; i++) {
			q = q.setParameter(paramNames[i], values[i]);
		}
		return q.executeUpdate();
	}
	
	public void delete(BaseModel model) {
		this.getSession().delete(get(model.getClass(), model.getId()));
	}

	public List<?> getPageDataList(String hql, BaseModel form, Map<?, ?> para) {
		return getObjects(hql, form.getPageSize(), form.getCurrentPage(), para);
	}

	/**
	 *根据hql生成相应的统计符合条件的记录数的hql, 对于同时有distinct和new
	 * 关键字的查询，生成的统计hql也能被hibernate正确解析
	 * 
	 * @param hql
	 * @return
	 */
	public String totalHqlParser(String hql) {
		StringBuffer totalHql = new StringBuffer(hql);
		hql = hql.toLowerCase();
		if (hql.indexOf("select ") == -1 || hql.indexOf("select ") > hql.indexOf("from ")) {
			// 对于from开头的hql不会有distinct关键字，直接在前面加select count(*)
			totalHql.insert(0, "select count(*) ");
		} else {
			if (hql.indexOf(" distinct ") < 0) {
				// 对于select开头且没有distinct关键字的hql，将"from"前的部分用"select count(*) "代替
				totalHql.delete(0, hql.indexOf(" from "));
				totalHql.insert(0, "select count(*)");
			} else {
				// 对于select开头且有distinct关键字的hql，在"select "后插入"count("，在"from "前插入")"
				totalHql.insert(totalHql.indexOf("select ") + 7, "count(");
				totalHql.insert(totalHql.indexOf("from "), ") ");

			}
		}

		// 截断order by 子句
		int orderPos = getBeginOrderSelectInsertPoint(totalHql.toString());
		if (orderPos > 2)
			totalHql.delete(orderPos, totalHql.length());
		if (totalHql.indexOf("new map(") > 0) {
			// String fields =
			// totalHql.substring(totalHql.indexOf("new map(")+8,
			// totalHql.indexOf(") from"));
			totalHql.delete(totalHql.indexOf("new map(") + 7, totalHql.indexOf(") from"));
			return totalHql.toString().replace("new map", "obj.ID" + " ");
		} else
			return totalHql.toString();

	}

	public List<?> getObjects(String hql, int pageSize, int absPage, Map<?, ?> para) {

		Session session = getSession();
		// Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		if (para != null) {

			Iterator<?> it = para.keySet().iterator();
			String key = null;
			Object value = null;
			while (it.hasNext()) {
				key = it.next().toString().trim();
				value = para.get(key);
				if (value instanceof Collection) {
					query.setParameterList(key, (Collection<?>) value);
				} else if (value instanceof Object[]) {
					query.setParameterList(key, (Object[]) value);
				} else {
					query.setParameter(key, value);
				}
			}
		}
		query.setFirstResult(pageSize * (absPage - 1));
		query.setMaxResults(pageSize);
		List<?> up = query.list();

		// tx.commit();
		//session.close();
		return up;
	}

	public List<?> getPageDataList(String hql, int pageSize, int absPage, Map<?, ?> para) {

		return getObjects(hql, pageSize, absPage, para);
	}

	// add by Camation
	/*
	 * public void createSqlParser(MopContext context) { sqlParser = new
	 * SqlParser(context); }
	 */

	/**
	 * @see com.kingdee.skeleton.dao.DAO#saveObject(java.lang.Object)
	 */
	public void saveObject(Object o) {
		Session session = getSession();
		session.saveOrUpdate(o);
	}

	/**
	 * @see com.kingdee.skeleton.dao.DAO#getObjects(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getObjects(Class<T> clazz) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(clazz);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	private static int getBeginOrderSelectInsertPoint(String sql) {
		int orderPos = sql.toLowerCase().indexOf(" order by ");
		return orderPos;

	}

	public List<?> getObjects(String hql) {
		return getObjects(hql, null);
	}

	public List<?> getObjects(String hql, Map<?, ?> para) {
		Session session = getSession();
		Query query = session.createQuery(hql);
		if (para != null) {

			Iterator<?> it = para.keySet().iterator();
			String key = null;
			Object value = null;
			while (it.hasNext()) {
				key = it.next().toString().trim();
				value = para.get(key);
				if (value instanceof Collection) {
					query.setParameterList(key, (Collection<?>) value);
				} else if (value instanceof Object[]) {
					query.setParameterList(key, (Object[]) value);
				} else {
					query.setParameter(key, value);
				}
			}
		}

		List<?> up = query.list();
		//session.close();
		return up;
	}

	/**
	 * @see com.kingdee.skeleton.dao.DAO#removeObject(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public void removeObject(Class<?> clazz, Serializable id) {
		Session session = getSession();
		session.delete(get(clazz, id));
	}

	public int updateBatch(String hql) {
		return bulkUpdate(hql);
	}

	/**
	 * 根据hql、分页信息及参数获得分页对象
	 * 
	 * @param String
	 *            hql
	 * @param Paginatable
	 *            form
	 * @param Map
	 *            para 参数
	 * @return PaginatedList
	 */
	public PaginatedList getPagedDataList(String hql, Paginatable form, Map<?, ?> para) {
		String totalHQL = totalHqlParser(hql);
		PaginatedList pageObject;
		int pageNO;
		if (form.getPageSize() == -1) {
			// 不分页，例如导出所有结果
			pageNO = 1;
			int totalCount = new Integer(getObjects(totalHQL, para).get(0).toString()).intValue();
			pageObject = new Paginator(getObjects(hql, para), pageNO, totalCount, totalCount);
		} else {
			// 分页
			pageNO = (form.getCurrentPage() == 0 ? 1 : form.getCurrentPage());
			pageObject = new Paginator(getObjects(hql, form.getPageSize(), pageNO, para), pageNO, form.getPageSize(),
					new Integer(getObjects(totalHQL, para).get(0).toString()).intValue());
		}
		return pageObject;
	}
	/*
     * 方便在job中调用存储过程
     * 调用无参数的存储过程，传入存储过程名字
     */
    public  int callProcedure(final String procedureName)
    {
    	int k = 0;
    	getSession().doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				CallableStatement  cs = (CallableStatement) connection.prepareCall("{call ?:=" + procedureName + "('saveseq_name')}");
				cs.registerOutParameter(1, Types.INTEGER);  
				cs.executeQuery();  
//				k=cs.getInt(1);
				String ss=cs.getString(1);  
				System.out.println(ss);  
			}
		});
//    	.createSQLQuery("{?=call "+ procedureName +"('saveseq_name')}");  
        return k;
    }

	@Override
	public List<?> findBySql(String sqlQueryString, Object[] params) {
		Session session = getSession();
		Query query = session.createSQLQuery(sqlQueryString);
		if(params!=null&&params.length>0){
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		
		return query.list();
	}

	@Override
	public int executeSql(String sqlQueryString, Object[] params) {
		Session session = getSession();
		Query queryObject = session.createSQLQuery(sqlQueryString);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				queryObject.setParameter(i, params[i]);
			}
		}
		return queryObject.executeUpdate();
	}

	@Override
	public List<?> findPagedDataBySql(String sqlQueryString, BaseModel form,
			Object[] params) {
		Session session = getSession();
		int pageSize=form.getPageSize(); 
		int currentPage=form.getCurrentPage();
		Query query = session.createSQLQuery(sqlQueryString);
		
		if(params!=null&&params.length>0){
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		query.setFirstResult(pageSize * (currentPage - 1));
		query.setMaxResults(pageSize);
		return query.list();
	}
}
