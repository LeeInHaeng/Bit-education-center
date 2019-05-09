package bookshop.vo;

public class BookVO {

	private long no;
	private String title;
	private long price;
	private long categoryNo;
	private String category;
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(long categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "BookVO [title=" + title + ", price=" + price + ", category=" + category + "]";
	}
	
	public BookVO() {
		super();
	}
	public BookVO(String title, long price, long categoryNo) {
		super();
		this.title = title;
		this.price = price;
		this.categoryNo = categoryNo;
	}
	public BookVO(long no, String title, long price, long categoryNo) {
		super();
		this.no = no;
		this.title = title;
		this.price = price;
		this.categoryNo = categoryNo;
	}
	
	
}
