package bookshop.vo;

public class CategoryVO {

	private long no;
	private String name;
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "category [no=" + no + ", name=" + name + "]";
	}
	
	public CategoryVO() {
		super();
	}
	public CategoryVO(String name) {
		super();
		this.name = name;
	}
	public CategoryVO(long no, String name) {
		super();
		this.no = no;
		this.name = name;
	}

}
