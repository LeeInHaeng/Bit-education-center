# Spring Security

### 기본 설정
- 기본 Spring 의존성 추가
```xml
		<!-- Spring Security -->
		<dependency>
		    <groupId>org.springframework.security</groupId>
		    <artifactId>spring-security-core</artifactId>
		    <version>4.1.3.RELEASE</version>
		</dependency>
		
		<dependency>
		    <groupId>org.springframework.security</groupId>
		    <artifactId>spring-security-config</artifactId>
		    <version>4.1.3.RELEASE</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-taglibs</artifactId>
		    <version>4.1.3.RELEASE</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-acl</artifactId>
		    <version>4.1.3.RELEASE</version>
		</dependency>
```
- 스프링 부트일 경우 의존성 추가
```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>2.1.6.RELEASE</version>
</dependency>
```
- mysite 3버전
  - web.xml에서 설정
```
	<!-- spring security filter -->
   <filter>
      <filter-name>springSecurityFilterChain</filter-name>
      <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
   </filter>
   <filter-mapping>
      <filter-name>springSecurityFilterChain</filter-name>
      <url-pattern>/*</url-pattern>
   </filter-mapping>
```
- mysite 4버전
  - AbstractSecurityWebApplicationInitializer를 상속받는 클래스 생성

- mysite 5버전
  - 관례

### Security Filter Chain
- ChannelProcessingFilter
- SecurityContextPersistenceFilter		( auto-config default, 중요 )
- ConcurrentSessionFilter
- LogoutFilter					( auto-config default, 중요 )
- UsernamePasswordAuthenticationFilter		( auto-config default, 중요 )
- DefaultLoginPageGeneratingFilter		( auto-config default )
- CasAuthenticationFilter
- BasicAuthenticationFilter			( auto-config default, 중요 )
- RequestCacheAwareFilter			( auto-config default )
- SecurityContextHolderAwareRequestFilter	( auto-config default )
- JaasApiIntegrationFilter
- RememberMeAuthenticationFilter
- AnonymousAuthenticationFilter			( auto-config default )
- SessionManagementFilter			( auto-config default )
- ExceptionTranslationFilter			( auto-config default, 중요 )
- FilterSecurityInterceptor			( auto-config default, 중요 )	

### Security Config 파일
- configure 3개 오버라이딩
```java
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(WebSecurity web) throws Exception {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
}
```
- WebSecurity configure : 보안과 관련되지 않은 URL을 설정
```java
	@Override
	public void configure(WebSecurity web) throws Exception {
		// super.configure(web);
		// 보안과 관련되지 않은 URL을 설정
		web.ignoring().antMatchers("/assets/**");
		web.ignoring().antMatchers("/favicon.ico");
	}
```
- HttpSecurity configure : Interceptor URL의 요청을 안전하게 보호(보안)
```java
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http);
		
		http.authorizeRequests()
		
		// 인증 여부 확인
			.antMatchers("/user/update", "/user/logout").authenticated()
			.antMatchers("/board/write", "/board/delete/**", "/board/modify/**").authenticated()
			
		// admin 여부 확인
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
			//.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN");
			.antMatchers("/admin/**").hasRole("ADMIN")
		
		// 모두 허용
			//.antMatchers("/**").permitAll();
			.anyRequest().permitAll();
		
		// 로그인 설정
		http
		.formLogin()
		.loginPage("/user/login")
		.loginProcessingUrl("/user/auth")
		.failureUrl("/user/login?result=fail")
		.defaultSuccessUrl("/", true)
		.usernameParameter("email")
		.passwordParameter("password");
		
		// 로그아웃 설정
		http
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true);
	}
```

- AuthenticationManagerBuilder configure : UserDetailsService 를 설정
```java
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// super.configure(auth);
		auth.userDetailsService(userDetailsService);
	}

// UserDetailsService 인터페이스를 구현하기 위한 클래스 생성
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVo userVo = userDao.get(username);
		
		SecurityUser securityUser = new SecurityUser();
		
		if(userVo != null) {
			// mock data
			String role = "ROLE_USER";
			
			securityUser.setName(userVo.getName());
			securityUser.setUsername(userVo.getEmail());
			securityUser.setPassword(userVo.getPassword());
			
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(role));
			securityUser.setAuthorities(authorities);
		}
		
		return securityUser;
	}

}

// UserDetails 타입 반환을 위한 클래스를 만든다
// 당장 사용하지 않을 부분들은 return true를 반환
public class SecurityUser implements UserDetails {
	private Collection<? extends GrantedAuthority> authorities;
	private String username;
	private String password;

	// getter, setter

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
}
```