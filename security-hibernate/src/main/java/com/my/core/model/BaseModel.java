package com.my.core.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.my.core.page.Paginatable;

public abstract class BaseModel implements Serializable, Cloneable, Paginatable {

	private static final long serialVersionUID = -5107536981694859309L;
	
	public static final int DEFAULT_PAGESIZE = 15;
	/**
	 * 瀹炰綋鏍囪瘑
	 */
	protected Serializable id;

	/**
	 * 鎬婚〉鏁�
	 */
	private int pageCount;

	/**
	 * 褰撳墠椤电爜
	 */
	private int currentPage;
	private int pageSize = DEFAULT_PAGESIZE;

	/**
	 * 鎬昏褰曟暟
	 */
	private int totalCount;

	public Serializable getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + currentPage;
		result = prime * result + pageCount;
		result = prime * result + pageSize;
		result = prime * result + totalCount;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseModel other = (BaseModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (currentPage != other.currentPage)
			return false;
		if (pageCount != other.pageCount)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (totalCount != other.totalCount)
			return false;
		return true;
	}

	/**
	 * @overwrite toString()
	 * @return String returns this object in a String
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * clone
	 * 
	 * @return Object
	 */
	public Object clone() {
		BaseModel obj = null;
		try {
			obj = (BaseModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
