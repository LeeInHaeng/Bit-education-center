# Servlet JSP 사용

### Servlet에서 JSP로 데이터 넘기기
- Servlet에서 request.setAttribute 사용
```java
GuestbookDAO dao = new GuestbookDAO();
List<GuestbookVO> list = dao.getList();
			
request.setAttribute("list", list);
			
WebUtil.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");
```
- JSP에서 request.getAttribute로 데이터 받기
  - 명시적 캐스팅 사용
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
- 파라미터 값일 경우 request.getParameter을 사용

### jsp 포함 시키기
- header, navigation, footer 등의 공통적으로 적용되는 부분을 jsp 파일로 따로 빼고, 다른 jsp에서 include 하도록 한다.
```jsp
		<jsp:include page="/WEB-INF/views/includes/header.jsp"/>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
```

### java 쿠키 및 세션 읽기, 쓰기
- 쿠키 쓰기
  - 쿠키 클래스 생성자에 이름과 값을 셋팅한다.
  - 쿠키 만료 기간과 쿠키가 최초 셋팅될 경로를 설정한다.
  - response에 쿠키를 추가한다.
```java
		count++;
		Cookie cookie = new Cookie("visitCount", String.valueOf(count));
		cookie.setMaxAge(24*60*60);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
```
- 쿠키 읽기
  - 사용자가 설정한 쿠키값 외에도 JSESSIONID가 셋팅된다.
  - getName을 통해 조작할 쿠키 값을 필터한다.
  - getValue를 통해 쿠키 값을 얻어낸다.(String 값)
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
- 세션 쓰기
  - setAttribute로 이름과 데이터 값을 셋팅한다.
```java
HttpSession session = request.getSession(true);
session.setAttribute("authUser", authUser);
```
- 세션 읽기
  - getAttribute를 통해 데이터를 얻는다.
```java
UserVo authUser = (UserVo)session.getAttribute("authUser");
```
- 세션이 존재할 경우 파기
  - getSession으로 세션이 존재하는지 확인
  - removeAttribute로 세션값 제거
  - invalidate로 다음에 오는 세션 아이디를 변경 시킨다.
```java
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute("authUser")!=null) {
			session.removeAttribute("authUser");
			session.invalidate();
		}
```

### Servlet 생명주기
- 최초 요청시 init() ---> service() ---> doGet() or doPost()
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
- 다음 요청시 service() ---> doGet() or doPost()
- 스왑될 때 destroy() 호출
- 유지되는 생명 page < request < session < application(전역)
  - application(전역) : getServletContext().setAttribute("cacheUser", map)

### Servlet filter
- New ---> Filter 로 filter 클래스 파일 생성
- web.xml
  - init-param 태그를 이용해 초기 파라미터를 넘길 수 있다.
  - url-pattern에서 아스트릭을 붙여야 된다는 주의점이 있다.
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
  - web.xml에서 init-param을 설정했다면 init 메서드에서 그 설정을 얻어올 수 있다.
  - web.xml에서 param-value에 값을 적어놓지 않으면 getInitParameter에서 null 값이 반환된다.
  - doFilter 메서드에서 request와 response에 대한 필터를 적용한다.
  - web.xml에 설정해 놓은 url-pattern으로 인해 해당 url에 해당 doFilter가 적용된다.
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
		// request 처리
		request.setCharacterEncoding(encoding);
		
		chain.doFilter(request, response);
		
		// response 처리
	}
	
    public EncodingFilter() {
    }

	public void destroy() {
	}

}
```