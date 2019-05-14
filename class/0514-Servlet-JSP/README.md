# Servlet JSP ���

### Servlet���� JSP�� ������ �ѱ��
- Servlet���� request.setAttribute ���
```java
GuestbookDAO dao = new GuestbookDAO();
List<GuestbookVO> list = dao.getList();
			
request.setAttribute("list", list);
			
WebUtil.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");
```
- JSP���� request.getAttribute�� ������ �ޱ�
  - ����� ĳ���� ���
```jsp
<%
	List<GuestbookVO> list = (List<GuestbookVO>)request.getAttribute("list");

%>
...

	<%
		int count = list.size();
		int index=0;
		for(GuestbookVO vo : list){
	%>
```
- �Ķ���� ���� ��� request.getParameter�� ���

### jsp ���� ��Ű��
- header, navigation, footer ���� ���������� ����Ǵ� �κ��� jsp ���Ϸ� ���� ����, �ٸ� jsp���� include �ϵ��� �Ѵ�.
```jsp
		<jsp:include page="/WEB-INF/views/includes/header.jsp"/>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
```

### java ��Ű �� ���� �б�, ����
- ��Ű ����
  - ��Ű Ŭ���� �����ڿ� �̸��� ���� �����Ѵ�.
  - ��Ű ���� �Ⱓ�� ��Ű�� ���� ���õ� ��θ� �����Ѵ�.
  - response�� ��Ű�� �߰��Ѵ�.
```java
		count++;
		Cookie cookie = new Cookie("visitCount", String.valueOf(count));
		cookie.setMaxAge(24*60*60);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
```
- ��Ű �б�
  - ����ڰ� ������ ��Ű�� �ܿ��� JSESSIONID�� ���õȴ�.
  - getName�� ���� ������ ��Ű ���� �����Ѵ�.
  - getValue�� ���� ��Ű ���� ����.(String ��)
```java
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			for(Cookie c : cookies) {
				if("visitCount".equals(c.getName())) {
					count = Integer.parseInt(c.getValue());
				}
			}
		}
```
- ���� ����
  - setAttribute�� �̸��� ������ ���� �����Ѵ�.
```java
HttpSession session = request.getSession(true);
session.setAttribute("authUser", authUser);
```
- ���� �б�
  - getAttribute�� ���� �����͸� ��´�.
```java
UserVo authUser = (UserVo)session.getAttribute("authUser");
```
- ������ ������ ��� �ı�
  - getSession���� ������ �����ϴ��� Ȯ��
  - removeAttribute�� ���ǰ� ����
  - invalidate�� ������ ���� ���� ���̵� ���� ��Ų��.
```java
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute("authUser")!=null) {
			session.removeAttribute("authUser");
			session.invalidate();
		}
```

### Servlet �����ֱ�
- ���� ��û�� init() ---> service() ---> doGet() or doPost()
```java
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("init() called");
	}

	public void destroy() {
		System.out.println("destroy() called");
		super.destroy();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("service() called");
		super.service(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet() called");
		
		response.getWriter().println("<h1>Servlet Life Circle</h1>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
```
- ���� ��û�� service() ---> doGet() or doPost()
- ���ҵ� �� destroy() ȣ��
- �����Ǵ� ���� page < request < session < application(����)
  - application(����) : getServletContext().setAttribute("cacheUser", map)

### Servlet filter
- New ---> Filter �� filter Ŭ���� ���� ����
- web.xml
  - init-param �±׸� �̿��� �ʱ� �Ķ���͸� �ѱ� �� �ִ�.
  - url-pattern���� �ƽ�Ʈ���� �ٿ��� �ȴٴ� �������� �ִ�.
```
	<!-- Encoding filter -->
	<filter>
		<display-name>EncodingFilter</display-name>
		<filter-name>EncodingFilter</filter-name>
		<filter-class>com.cafe24.web.filter.EncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>utf-8</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>EncodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
```
- EncodingFilter.java
  - web.xml���� init-param�� �����ߴٸ� init �޼��忡�� �� ������ ���� �� �ִ�.
  - web.xml���� param-value�� ���� ������� ������ getInitParameter���� null ���� ��ȯ�ȴ�.
  - doFilter �޼��忡�� request�� response�� ���� ���͸� �����Ѵ�.
  - web.xml�� ������ ���� url-pattern���� ���� �ش� url�� �ش� doFilter�� ����ȴ�.
```java
public class EncodingFilter implements Filter {

	private String encoding;
	
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("Encoding filter init");
		encoding = fConfig.getInitParameter("encoding");
		
		if(encoding == null) {
			encoding = "utf-8";
		}
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// request ó��
		request.setCharacterEncoding(encoding);
		
		chain.doFilter(request, response);
		
		// response ó��
	}
	
    public EncodingFilter() {
    }

	public void destroy() {
	}

}
```