package com.my.core.page;

import java.util.List;

public interface PaginatedList  {
	void setList(List<?> list);
	void setCurrentPage(int pageNumber);
	void setPageSize(int objectsPerPage);
	void setTotalCount(int tocalCount);
	int getTotalCount();
	int getCurrentPage();
	int getPageSize();
}
