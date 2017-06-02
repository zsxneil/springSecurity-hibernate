package com.my.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.my.core.model.BaseModel;
import com.my.core.page.IPage;
import com.my.core.page.Paginatable;
import com.my.core.page.PaginatedList;


public interface BaseDAO {
	
	
	public boolean isCacheQueries();

	public void setCacheQueries(boolean cacheQueries);

	public int getFetchSize();

	public void setFetchSize(int fetchSize);

	public int getMaxResults();

	public void setMaxResults(int maxResults);

	
	//public SqlParser sqlParser = null;
	


	public Object get(String entityName, Serializable id);

	public Object load(String entityName, Serializable id);


	public boolean contains(Object entity);

	
	public void evict(Object entity);

	public Serializable save(Object entity);

	public Serializable save(String entityName, Object entity);

	public void update(Object entity);

	public void update(String entityName, Object entity);

	public void saveOrUpdate(Object entity);

	public void saveOrUpdate(String entityName, Object entity);

	public Object merge(Object entity);

	public Object merge(String entityName, Object entity);

	public void delete(Object entity);

	public void flush();

	public void clear();

	public List<?> find(String queryString);

	public List<?> find(String queryString, Object value);

	public List<?> find(String queryString, Object[] values);
	
	public List<?> findBySql(String sqlQueryString, Object[] params);
	
	public List<?> findPagedDataBySql(String sqlQueryString, BaseModel form,Object[] params);

	public List<?> findByNamedParam(String queryString, String paramName, Object value);

	public List<?> findByNamedParam(String queryString, String[] paramNames, Object[] values);

	public List<?> findByNamedQuery(String queryName);

	public List<?> findByNamedQuery(String queryName, Object value);

	public List<?> findByNamedQuery(String queryName, Object[] values);

	public List<?> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value);

	public List<?> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values);

	public int bulkUpdate(String queryString) ;

	public int bulkUpdate(String queryString, Object value);

	public int bulkUpdate(String queryString, Object[] values);
	
	public int executeSql(String sqlQueryString, Object[] params);
	
	int bulkUpdate(final String queryString, String paramName, Object value);

	int bulkUpdate(final String queryString, String[] paramNames, Object[] values);
    
    /**
     * 根据hql生成相应的统计符合条件的记录数的hql,
     * 对于同时有distinct和new 关键字的查询，生成的统计hql也能被hibernate正确解析
     * @param hql
     * @return
     */
    public String totalHqlParser(String hql);

	public List<?> getPageDataList(String hql, int pageSize, int absPage, Map<?,?> para);

		    
    public List<?> getObjects(String hql);

	public void pageQuery(final String queryString, String[] paramNames, Object[] values, IPage page);
	
	public Object getObjectByQuery(final String queryString, String[] paramNames, Object[] values);
	
	 public List<?> getPageDataList(String hql,BaseModel form,Map<?, ?> para);
	 
	 public List<?> getObjects(String hql,Map<?, ?> para);
	 
	 public void delete(final BaseModel model);
	
	//public void createSqlParser(MopContext context) ;
	 
	 public  <T> T get(Class<T> entityClass, Serializable id);
	
    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @param clazz the type of objects (a.k.a. while table) to get data from
     * @return List of populated objects
     */
    public <T> List<T> getObjects(Class<T> clazz);
    
   

    
    public <T> T load(Class<?> entityClass, Serializable id);
    
    public <T> List<T> loadAll(Class<T> entityClass);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param o the object to save
     */
    public void saveObject(Object o);

    /**
     * Generic method to delete an object based on class and id
     * @param clazz model class to lookup
     * @param id the identifier (primary key) of the class
     */
    public void removeObject(Class<?> clazz, Serializable id);
    public List<?> getObjects(String hql,int pageSize,int absPage,Map<?, ?> para);

    public int updateBatch(String hql);
    public PaginatedList getPagedDataList(String hql,Paginatable form,Map<?, ?> para);
    
    public int getTotalCounts(String queryString, String[] paramNames, Object[] values);
    
    public int getTotalCounts(String queryString, Map<?,?> params);
}