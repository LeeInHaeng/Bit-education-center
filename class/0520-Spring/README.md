# 메시지 컨버터
- XML 이나 JSON을 이용한 AJAX 기능이나 웹서비스 개발에 이용
- HTTP 요청 메세지 본문( Request Body ), HTTP 응답 메세지 본문( Response Body )을 통째로 메세지로 다루는 방식 
- 파라미터의 @RequestBody, 메소드에 @ResponseBody를 이용   
- 메세지 컨버터는 AnnotationMethodHandlerAdapter를 통해 하나 이상의 컨버터가 등록, 선택 동작하게 된다.  
- 응답(Response)의 경우 해당 핸들러 메소드에 @ResponseBody 와 함께 반환되는 객체의 종류에 따라 메세지 컨버터가 선택되고 응답바디 내용이 채워져 브라우저로 전달된다.  

### 메시지 컨버터 등록하기
- <mvc:annotation-driven /> 에  <mvc:message-conveters>를  통해 여러 메시지 컨버터를  등록 할 수 있다.
  - spring-servlet.xml에서
```xml
	<!-- Validator, Conversion Service, Formatter Message Converter 설정 -->
	<mvc:annotation-driven>
		<mvc:message-converters>
			<bean class="org.springframework.http.converter.StringHttpMessageConverter">
			    <property name="supportedMediaTypes">
			        <list>
			             <value>text/html; charset=UTF-8</value>
			        </list>
			    </property>
			</bean>
		</mvc:message-converters>
	</mvc:annotation-driven>
```

### Ajax 사용하기
- pom.xml에 라이브러리 등록
```xml
		<!-- jackson -->
		<dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-databind</artifactId>
		    <version>2.9.8</version>
		</dependency>
```
- 메시지 컨버터 등록
```xml
<!-- Validator, Conversion Service, Formatter Message Converter 설정 -->
<mvc:annotation-driven>
	<mvc:message-converters>
		<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
			<property name="supportedMediaTypes">
				<list>
					<value>application/json; charset=UTF-8</value>
				</list>
			</property>
		</bean>
	</mvc:message-converters>
</mvc:annotation-driven>
```
- jquery를 사용해 Ajax 통신
```js
$("#check-button").click( function(){
	var email = $("#email").val();
	if(email == ''){
		return;
	}
	$.ajax( {
		url : "${pageContext.servletContext.contextPath }/user/api/checkEmail?email="+email,
		type: "get",
		dataType: "json",
		data: "",
		//  contentType: "application/json",
		success: function( response ){
			console.log( response );
		},
		error: function( jqXHR, status, error ){
			console.error( status + " : " + error );
		}

	});

});	
```
- Controller에서 ResponseBody를 사용해 메시지 컨버터 사용
  - View를 return하는것이 아닌 데이터 자체를 return
```java
	@ResponseBody
	@RequestMapping("/checkEmail")
	public Map<String, Object> checkEmail(
			@RequestParam(value="email", required=true, defaultValue="") String email) {
		
		Boolean exist = userService.existEmail(email);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", exist);
		
		return map;
	}
```
- JSON 에러로 응답하기
```java
		if(accept.matches(".*application/json.*")) {
			// JSON 응답
			response.setStatus(HttpServletResponse.SC_OK);
			
			JSONResult jsonResult = JSONResult.fail(errors.toString());
			String result = new ObjectMapper().writeValueAsString(jsonResult); // jackson 사용
			//System.out.println(result);
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes("utf-8"));
			os.close();
		}else {
			// 2. 안내페이지 가기 + 정상종료
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}
```