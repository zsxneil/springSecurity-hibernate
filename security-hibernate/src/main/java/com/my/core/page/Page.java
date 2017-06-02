package com.my.core.page;

import java.util.List;

import com.my.core.model.BaseModel;

public class Page implements IPage {
	
	public static final int DEFAULT_SIZE=20;

    protected int firstPage = 1;

    protected int lastPage = 1;

    protected int previousPage = 1;

    protected int nextPage = 1;

    // 第几页,pageNO为0时是第一次查询
    protected int pageNO = 0;

    protected int pageCount = 1;

    protected int totalCount = 1;

    // 每页记录数
    protected int pageSize = DEFAULT_SIZE;

    protected List<?> retList = null;

    public Page() {

    }

    public IPage query(BaseModel baseModel) {

        pageNO = baseModel.getCurrentPage();
        pageCount = baseModel.getPageCount();
        totalCount = baseModel.getTotalCount();
        populate();
        return this;
    }

    private void populate() {

        // 初始化总页数和总记录数

        pageCount = totalCount / pageSize;

        pageCount = (totalCount % pageSize == 0) ? pageCount : pageCount + 1;

        lastPage = pageCount;

        previousPage = pageNO - 1;
        if (previousPage <= 1)
            previousPage = 1;

        nextPage = pageNO + 1;
        if (nextPage >= pageCount)
            nextPage = pageCount;

    }

    /**
     * @see com.framework.page.IPage2#getFirstPage()
     * 
     * @return
     */
    public int getFirstPage() {
        return firstPage;
    }

    /**
     * @see com.framework.page.IPage2#setFirstPage(int)
     * 
     * @param firstPage
     */
    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    /**
     * @see com.framework.page.IPage2#getLastPage()
     * 
     * @return
     */
    public int getLastPage() {
        return lastPage;
    }

    /**
     * @see com.framework.page.IPage2#setLastPage(int)
     * 
     * @param lastPage
     */
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    /**
     * @see com.framework.page.IPage2#getNextPage()
     * 
     * @return
     */
    public int getNextPage() {
        return nextPage;
    }

    /**
     * @see com.framework.page.IPage2#setNextPage(int)
     * 
     * @param nextPage
     */
    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * @see com.framework.page.IPage2#getPageCount()
     * 
     * @return
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * @see com.framework.page.IPage2#setPageCount(int)
     * 
     * @param pageCount
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @see com.framework.page.IPage2#getPageNO()
     * 
     * @return
     */
    public int getPageNO() {
        return pageNO;
    }

    /**
     * @see com.framework.page.IPage2#setPageNO(int)
     * 
     * @param pageNO
     */
    public void setPageNO(int pageNO) {
        this.pageNO = pageNO;
    }

    /**
     * @see com.framework.page.IPage2#getPageSize()
     * 
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @see com.framework.page.IPage2#setPageSize(int)
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @see com.framework.page.IPage2#getPreviousPage()
     * 
     * @return
     */
    public int getPreviousPage() {
        return previousPage;
    }

    /**
     * @see com.framework.page.IPage2#setPreviousPage(int)
     * 
     * @param previousPage
     */
    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    /**
     * @see com.framework.page.IPage2#getRetList()
     * 
     * @return
     */
    public List<?> getRetList() {
        return retList;
    }

    /**
     * @see com.framework.page.IPage2#setRetList(java.util.List)
     * 
     * @param retList
     */
    public void setRetList(List<?> retList) {
        this.retList = retList;
    }

    /**
     * @see com.framework.page.IPage2#getTotalCount()
     * 
     * @return
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @see com.framework.page.IPage2#setTotalCount(int)
     * 
     * @param totalCount
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}