package bookshop.vo;

public class MemberVO {

	private long no;
	private String name;
	private String tel;
	private String email;
	private String pass;
	private String addr;
	
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
	@Override
	public String toString() {
		return "MemberVO [name=" + name + ", tel=" + tel + ", email=" + email + ", pass=" + pass + ", addr=" + addr
				+ "]";
	}
	
	public MemberVO() {
		super();
	}
	public MemberVO(String name, String tel, String email, String pass, String addr) {
		super();
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.pass = pass;
		this.addr = addr;
	}
	public MemberVO(long no, String name, String tel, String email, String pass, String addr) {
		super();
		this.no = no;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.pass = pass;
		this.addr = addr;
	}
	
}
