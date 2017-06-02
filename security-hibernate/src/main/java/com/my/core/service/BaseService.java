package com.my.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.my.core.model.BaseModel;
import com.my.core.page.Paginatable;
import com.my.core.page.PaginatedList;

public interface BaseService {


	// ~ Methods for convinent use
	// ================================================================

	public void saveObject(BaseModel model);
	public void deleteObject(BaseModel model);

	public BaseModel getObject(BaseModel model);

	public List<?> getObjects(String hql);

	
	 public List<?> getPageDataList(String hql,BaseModel form,Map<?, ?> para);
	 public List<?> getObjects(String hql,Map<?, ?> para);
	 
	 
	 /**
	     * Generic method used to get a all objects of a particular type. 
	     * @param clazz the type of objects 
	     * @return List of populated objects
	     */
	    public <T> List<T> getObjects(Class<T> clazz);
	    
	    /**
	     * Generic method to get an object based on class and identifier. 
	     * 
	     * @param clazz model class to lookup
	     * @param id the identifier (primary key) of the class
	     * @return a populated object 
	     * @see org.springframework.orm.ObjectRetrievalFailureException
	     */
	    public <T> T get(Class<T> clazz, Serializable id);

	    /**
	     * Generic method to save an object.
	     * @param o the object to save
	     */
	    public void saveObject(Object o);

	    /**
	     * Generic method to delete an object based on class and id
	     * @param clazz model class to lookup
	     * @param id the identifier of the class
	     */
	    public void removeObject(Class<?> clazz, Serializable id);
	    public PaginatedList getPagedDataList(String hql,Paginatable form,Map<?, ?> para);
	    
	    public Object load(Class<?> clazz, Serializable id);
	    
	    public void update(Object o);
	    
	    public List<?> findBySql(String sqlQueryString, Object[] params);
	    
	    public List<?> findPagedDataBySql(String sqlQueryString, BaseModel form,Object[] params);
	    
	    public int bulkUpdate(String queryString) ;
	    
	    public int executeSql(String sqlQueryString, Object[] params);
}
