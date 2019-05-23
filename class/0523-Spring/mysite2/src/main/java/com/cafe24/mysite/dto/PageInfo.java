package com.cafe24.mysite.dto;

public class PageInfo {

	// 한 페이지에 보여줄 게시글의 수
	private int pageSize;
	
	// 페이지에 보여줄 번호의 수
	// ex)3 ---> < 1 2 3 >
	private int pageNumberSize;
	
	// 현재 페이지의 번호
	private int currentPage;
	
	// < 1 2 3 > 중에서 1
	private int beginPageNum;
	
	// < 1 2 3 > 중에서 3
	private int endPageNum;
	
	// 전체 게시글의 수
	private long totalBoardCount;
	
	// 마지막 페이지의 넘버
	private int lastPageNum;
	
	// 검색 키워드
	private String searchKeyword;
	
	public PageInfo() {
		this.pageSize = 5;
		this.pageNumberSize = 3;
		this.currentPage = 1;
		
		this.beginPageNum = 1;
		this.endPageNum = 1+pageNumberSize-1;
		
		this.searchKeyword = "";
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getBeginPageNum() {
		return beginPageNum;
	}

	public void setBeginPageNum(int beginPageNum) {
		this.beginPageNum = beginPageNum;
	}

	public int getEndPageNum() {
		return endPageNum;
	}

	public void setEndPageNum(int endPageNum) {
		this.endPageNum = endPageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumberSize() {
		return pageNumberSize;
	}

	public void setPageNumberSize(int pageNumberSize) {
		this.pageNumberSize = pageNumberSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		this.beginPageNum = (int) (Math.floor((currentPage-1)/pageNumberSize)*pageNumberSize)+1;
		this.endPageNum = beginPageNum+pageNumberSize-1;
	}

	public long getTotalBoardCount() {
		return totalBoardCount;
	}

	public void setTotalBoardCount(long totalBoardCount) {
		this.totalBoardCount = totalBoardCount;
		this.lastPageNum = (int) Math.ceil(totalBoardCount/(float)pageSize);
	}

	public int getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(int lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	@Override
	public String toString() {
		return "PageInfo [pageSize=" + pageSize + ", pageNumberSize=" + pageNumberSize + ", currentPage=" + currentPage
				+ ", beginPageNum=" + beginPageNum + ", endPageNum=" + endPageNum + ", totalBoardCount="
				+ totalBoardCount + ", lastPageNum=" + lastPageNum + ", searchKeyword=" + searchKeyword + "]";
	}


}
