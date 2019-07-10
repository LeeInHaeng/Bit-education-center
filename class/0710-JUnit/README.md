# JUnit
### �ʿ� ���̺귯�� �ٿ�ε�
- Spring-boot ����
```
		<!-- Test --> 			
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<scope>test</scope>
		</dependency>
		
		<dependency>
	    	<groupId>org.mockito</groupId>
	        <artifactId>mockito-all</artifactId>
	        <version>1.10.19</version>
	        <scope>test</scope>
	    </dependency>
		
		<dependency>
		    <groupId>org.hamcrest</groupId>
		    <artifactId>hamcrest-all</artifactId>
		    <version>1.3</version>
		    <scope>test</scope>
		</dependency>
		
		<dependency>
		    <groupId>com.jayway.jsonpath</groupId>
		    <artifactId>json-path</artifactId>
		    <scope>test</scope>
		</dependency>
		
		<!-- gson -->
		<dependency>
		    <groupId>com.google.code.gson</groupId>
		    <artifactId>gson</artifactId>
		    <scope>test</scope>
		</dependency>
```
### �׽�Ʈ Ŭ���� �ۼ�
- src/test/java �� src/test/resources�� �̿��Ѵ�
  - static���� ����ϴ� �޼���
```java
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
```
- Ŭ���� �⺻ ����
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MainControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void Test_1_MainPageConnect() throws Exception {
	}
}
```
- parameter�� �Բ� �⺻���� get ��û ���
```java
	@Test
	public void Test_3_MemberIdCheck() throws Exception {
		ResultActions resultActions = 
				mockMvc
					.perform(get("/api/user/checkId")
					.param("userid", "user1")
					.contentType(MediaType.APPLICATION_JSON));
		
		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.result", is("success or fail")));
	}
```
- Gson�� �̿��� ��ü�� post�� �����ϴ� ���
```java
	@Test
	public void Test_4_MemberJoinRequest() throws Exception {
		MemberVo memberVo = new MemberVo();

		ResultActions resultActions = 
			mockMvc
			.perform(post("/api/user/join")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(memberVo)));
		
		resultActions
			.andExpect(status().isOk())
			.andDo(print());
	}
```