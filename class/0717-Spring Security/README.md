# Spring Security

### �⺻ ����
- �⺻ Spring ������ �߰�
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
- ������ ��Ʈ�� ��� ������ �߰�
```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>2.1.6.RELEASE</version>
</dependency>
```
- mysite 3����
  - web.xml���� ����
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
- mysite 4����
  - AbstractSecurityWebApplicationInitializer�� ��ӹ޴� Ŭ���� ����

- mysite 5����
  - ����

### Security Filter Chain
- ChannelProcessingFilter
- SecurityContextPersistenceFilter		( auto-config default, �߿� )
- ConcurrentSessionFilter
- LogoutFilter					( auto-config default, �߿� )
- UsernamePasswordAuthenticationFilter		( auto-config default, �߿� )
- DefaultLoginPageGeneratingFilter		( auto-config default )
- CasAuthenticationFilter
- BasicAuthenticationFilter			( auto-config default, �߿� )
- RequestCacheAwareFilter			( auto-config default )
- SecurityContextHolderAwareRequestFilter	( auto-config default )
- JaasApiIntegrationFilter
- RememberMeAuthenticationFilter
- AnonymousAuthenticationFilter			( auto-config default )
- SessionManagementFilter			( auto-config default )
- ExceptionTranslationFilter			( auto-config default, �߿� )
- FilterSecurityInterceptor			( auto-config default, �߿� )	

### Security Config ����
- configure 3�� �������̵�
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
- WebSecurity configure : ���Ȱ� ���õ��� ���� URL�� ����
```java
	@Override
	public void configure(WebSecurity web) throws Exception {
		// super.configure(web);
		// ���Ȱ� ���õ��� ���� URL�� ����
		web.ignoring().antMatchers("/assets/**");
		web.ignoring().antMatchers("/favicon.ico");
	}
```
- HttpSecurity configure : Interceptor URL�� ��û�� �����ϰ� ��ȣ(����)
```java
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http);
		
		http.authorizeRequests()
		
		// ���� ���� Ȯ��
			.antMatchers("/user/update", "/user/logout").authenticated()
			.antMatchers("/board/write", "/board/delete/**", "/board/modify/**").authenticated()
			
		// admin ���� Ȯ��
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
			//.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN");
			.antMatchers("/admin/**").hasRole("ADMIN")
		
		// ��� ���
			//.antMatchers("/**").permitAll();
			.anyRequest().permitAll();
		
		// �α��� ����
		http
		.formLogin()
		.loginPage("/user/login")
		.loginProcessingUrl("/user/auth")
		.failureUrl("/user/login?result=fail")
		.defaultSuccessUrl("/", true)
		.usernameParameter("email")
		.passwordParameter("password");
		
		// �α׾ƿ� ����
		http
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true);
	}
```

- AuthenticationManagerBuilder configure : UserDetailsService �� ����
```java
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// super.configure(auth);
		auth.userDetailsService(userDetailsService);
	}

// UserDetailsService �������̽��� �����ϱ� ���� Ŭ���� ����
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

// UserDetails Ÿ�� ��ȯ�� ���� Ŭ������ �����
// ���� ������� ���� �κе��� return true�� ��ȯ
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