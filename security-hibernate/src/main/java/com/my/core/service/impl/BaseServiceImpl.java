package com.my.core.service.impl;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.my.core.dao.BaseDAO;
import com.my.core.model.BaseModel;
import com.my.core.page.IPage;
import com.my.core.page.Paginatable;
import com.my.core.page.PaginatedList;
import com.my.core.service.BaseService;

@Service("baseService")
public class BaseServiceImpl implements BaseService  {
	protected final Logger log = Logger.getLogger(getClass());
	
	protected BaseDAO baseDAO;
	

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	@Autowired
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	protected IPage page = null;
	

	public IPage getPage() {
		return page;
	}

	public void setPage(IPage page) {
		this.page = page;
	}


	public void afterPropertiesSet() throws Exception {
		Assert.notNull(baseDAO, "dao required");
		// Assert.notNull(basicAclExtendedDao, "basicAclExtendedDao required");
	}

	// ~ Methods for convinent use
	// ================================================================

	public void saveObject(BaseModel model) {
		baseDAO.saveOrUpdate(model);
	}

	public void deleteObject(BaseModel model) {
		baseDAO.delete(model);
	}

	public BaseModel getObject(BaseModel model) {
		return (BaseModel) baseDAO.get(model.getClass(), model.getId());
	}


	public List<?> getPageDataList(String hql, BaseModel form, Map<?, ?> para) {
		
		return baseDAO.getPageDataList(hql, form,  para);
	}

	public List<?> getObjects(String hql, Map<?, ?> para) {
		
		return baseDAO.getObjects(hql,  para);
	}
	
	 /**
     * @see com.kingdee.BaseService.service.Manager#getObject(java.lang.Class, java.io.Serializable)
     */
    public <T> T get(Class<T> clazz, Serializable id) {
        return baseDAO.get(clazz, id);
    }
    
    /**
     * @see com.kingdee.BaseService.service.Manager#getObjects(java.lang.Class)
     */
    public <T> List<T> getObjects(Class<T> clazz) {
        return baseDAO.getObjects(clazz);
    }
    
    /**
     * @see com.kingdee.BaseService.service.Manager#removeObject(java.lang.Class, java.io.Serializable)
     */
    public void removeObject(Class<?> clazz, Serializable id) {
        baseDAO.removeObject(clazz, id);
    }
    
    /**
     * @see com.kingdee.BaseService.service.Manager#saveObject(java.lang.Object)
     */
    public void saveObject(Object o) {
        baseDAO.saveObject(o);
    }

	public PaginatedList getPagedDataList(String hql, Paginatable form, Map<?, ?> para) {
		return baseDAO.getPagedDataList(hql, form, para);
	}

	public Object load(Class<?> clazz, Serializable id) {
		return baseDAO.load(clazz, id);
	}

	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub
		baseDAO.update(o);
	}

	@Override
	public List<?> getObjects(String hql) {
		return baseDAO.getObjects(hql);
	}

	@Override
	public List<?> findBySql(String sqlQueryString, Object[] params) {
		// TODO Auto-generated method stub
		return baseDAO.findBySql(sqlQueryString, params);
	}

	@Override
	public int bulkUpdate(String queryString) {
		// TODO Auto-generated method stub
		return baseDAO.bulkUpdate(queryString);
	}

	@Override
	public List<?> findPagedDataBySql(String sqlQueryString, BaseModel form,
			Object[] params) {
		// TODO Auto-generated method stub
		return baseDAO.findPagedDataBySql(sqlQueryString, form, params);
	}

	@Override
	public int executeSql(String sqlQueryString, Object[] params) {
		// TODO Auto-generated method stub
		return baseDAO.executeSql(sqlQueryString, params);
	}
   
    
	
}
