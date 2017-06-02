package com.my.core.page;

import java.util.List;

import com.my.core.model.BaseModel;

/**
 * 
 * 
 * <p>
 * Title: IPage.java
 * </p>
 * <p>
 * Description:分页接口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Kingdee International Software Group
 * </p>
 * 
 * @author wuweiwen
 * @version 1.0
 */
public interface IPage {

    public abstract IPage query(BaseModel baseModel);

    public abstract int getFirstPage();

    public abstract void setFirstPage(int firstPage);

    public abstract int getLastPage();

    public abstract void setLastPage(int lastPage);

    public abstract int getNextPage();

    public abstract void setNextPage(int nextPage);

    public abstract int getPageCount();

    public abstract void setPageCount(int pageCount);

    public abstract int getPageNO();

    public abstract void setPageNO(int pageNO);

    public abstract int getPageSize();

    public abstract void setPageSize(int pageSize);

    public abstract int getPreviousPage();

    public abstract void setPreviousPage(int previousPage);

    public abstract List<?> getRetList();

    public abstract void setRetList(List<?> retList);

    public abstract int getTotalCount();

    public abstract void setTotalCount(int totalCount);
}
