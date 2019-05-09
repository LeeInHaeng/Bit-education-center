package bookshop.vo;

public class CartVO {

	private long bookNo;
	private long memberNo;
	private long cnt;
	private long price;
	private String bookTitle;
	
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public long getBookNo() {
		return bookNo;
	}
	public void setBookNo(long bookNo) {
		this.bookNo = bookNo;
	}
	public long getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(long memberNo) {
		this.memberNo = memberNo;
	}
	public long getCnt() {
		return cnt;
	}
	public void setCnt(long cnt) {
		this.cnt = cnt;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "CartVO [cnt=" + cnt + ", price=" + price + ", bookTitle=" + bookTitle + "]";
	}
	
	public CartVO() {
		super();
	}
	public CartVO(long bookNo) {
		super();
		this.bookNo = bookNo;
	}
	public CartVO(long bookNo, long memberNo, long cnt) {
		super();
		this.bookNo = bookNo;
		this.memberNo = memberNo;
		this.cnt = cnt;
	}
	public CartVO(long bookNo, long memberNo, long cnt, long price) {
		super();
		this.bookNo = bookNo;
		this.memberNo = memberNo;
		this.cnt = cnt;
		this.price = price;
	}
	
	
}
