package bookshop.vo;

public class OrdersBookVO {

	private String memberName;
	private long ordersNo;
	private long bookNo;
	private long cnt;
	
	private String bookTitle;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public long getOrdersNo() {
		return ordersNo;
	}

	public void setOrdersNo(long ordersNo) {
		this.ordersNo = ordersNo;
	}

	public long getBookNo() {
		return bookNo;
	}

	public void setBookNo(long bookNo) {
		this.bookNo = bookNo;
	}

	public long getCnt() {
		return cnt;
	}

	public void setCnt(long cnt) {
		this.cnt = cnt;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	
	@Override
	public String toString() {
		return "OrdersBookVO [memberName=" + memberName + ", ordersNo=" + ordersNo + ", bookNo=" + bookNo + ", cnt="
				+ cnt + ", bookTitle=" + bookTitle + "]";
	}

	public OrdersBookVO() {
		super();
	}

	public OrdersBookVO(long ordersNo, long bookNo, long cnt) {
		super();
		this.ordersNo = ordersNo;
		this.bookNo = bookNo;
		this.cnt = cnt;
	}
	
	
}
