package com.my.core.page;

import java.util.List;


/**
 * @author danielding
 *
 */
public class Paginator implements PaginatedList {

	private List<?> list;
	private int pageNumber;
	private int objectsPerPage = Page.DEFAULT_SIZE;
	private int fullListSize;
	private String sortCriterion;
	private String searchId;

	public Paginator(List<?> list,int pageNumber,int objectsPerPage,int fullListSize){
		this.list = list;
		this.pageNumber = pageNumber;
		this.objectsPerPage = objectsPerPage;
		this.fullListSize = fullListSize;
	}


	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getList()
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * 返回当前页的列表
	 * @return
	 */
	public List<?> getCurList() {
		if(list == null || list.size() == 0)
			return list;
		else
			return list.subList((pageNumber-1)*objectsPerPage,
					pageNumber*objectsPerPage<fullListSize?pageNumber*objectsPerPage:fullListSize);
	}

	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getPageNumber()
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getObjectsPerPage()
	 */
	public int getObjectsPerPage() {
		return objectsPerPage;
	}

	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getFullListSize()
	 */
	public int getFullListSize() {
		return fullListSize;
	}

	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getSortCriterion()
	 */
	public String getSortCriterion() {
		return sortCriterion;
	}


	/* (non-Javadoc)
	 * @see org.displaytag.pagination.PaginatedList#getSearchId()
	 */
	public String getSearchId() {
		return searchId;
	}

	/**
	 * @param fullListSize The fullListSize to set.
	 */
	public void setFullListSize(int fullListSize) {
		this.fullListSize = fullListSize;
	}

	/**
	 * @param list The list to set.
	 */
	public void setList(List<?> list) {
		this.list = list;
	}

	/**
	 * @param objectsPerPage The objectsPerPage to set.
	 */
	public void setObjectsPerPage(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}

	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @param searchId The searchId to set.
	 */
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	/**
	 * @param sortCriterion The sortCriterion to set.
	 */
	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}


	public void setCurrentPage(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageSize(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}

	public void setTotalCount(int tocalCount) {
		this.fullListSize = tocalCount;
	}

	public int getCurrentPage() {
		return pageNumber;
	}

	public int getPageSize() {
		return objectsPerPage;
	}

	public int getTotalCount() {
		return fullListSize;
	}

}
