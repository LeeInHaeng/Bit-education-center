package bookshop.vo;

public class OrdersVO {

	private long no;
	private String orderNo;
	private long price;
	private String addr;
	private long memberNo;
	
	private String memberName;
	private String memberEmail;
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public long getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(long memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	
	@Override
	public String toString() {
		return "OrdersVO [orderNo=" + orderNo + ", price=" + price + ", addr=" + addr + ", memberName=" + memberName
				+ ", memberEmail=" + memberEmail + "]";
	}
	
	public OrdersVO() {
		super();
	}
	public OrdersVO(long price, String addr, long memberNo) {
		super();
		this.price = price;
		this.addr = addr;
		this.memberNo = memberNo;
	}
	public OrdersVO(String orderNo, long price, String addr, long memberNo) {
		super();
		this.orderNo = orderNo;
		this.price = price;
		this.addr = addr;
		this.memberNo = memberNo;
	}
	public OrdersVO(long no, String orderNo, long price, String addr, long memberNo) {
		super();
		this.no = no;
		this.orderNo = orderNo;
		this.price = price;
		this.addr = addr;
		this.memberNo = memberNo;
	}
	public OrdersVO(long no, String orderNo, long price, String addr, long memberNo, String memberName,
			String memberEmail) {
		super();
		this.no = no;
		this.orderNo = orderNo;
		this.price = price;
		this.addr = addr;
		this.memberNo = memberNo;
		this.memberName = memberName;
		this.memberEmail = memberEmail;
	}

	
}
