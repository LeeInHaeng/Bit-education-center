# �޽��� ������
- XML �̳� JSON�� �̿��� AJAX ����̳� ������ ���߿� �̿�
- HTTP ��û �޼��� ����( Request Body ), HTTP ���� �޼��� ����( Response Body )�� ��°�� �޼����� �ٷ�� ��� 
- �Ķ������ @RequestBody, �޼ҵ忡 @ResponseBody�� �̿�   
- �޼��� �����ʹ� AnnotationMethodHandlerAdapter�� ���� �ϳ� �̻��� �����Ͱ� ���, ���� �����ϰ� �ȴ�.  
- ����(Response)�� ��� �ش� �ڵ鷯 �޼ҵ忡 @ResponseBody �� �Բ� ��ȯ�Ǵ� ��ü�� ������ ���� �޼��� �����Ͱ� ���õǰ� ����ٵ� ������ ä���� �������� ���޵ȴ�.  

### �޽��� ������ ����ϱ�
- <mvc:annotation-driven /> ��  <mvc:message-conveters>��  ���� ���� �޽��� �����͸�  ��� �� �� �ִ�.
  - spring-servlet.xml����
```xml
	<!-- Validator, Conversion Service, Formatter Message Converter ���� -->
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

### Ajax ����ϱ�
- pom.xml�� ���̺귯�� ���
```xml
		<!-- jackson -->
		<dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-databind</artifactId>
		    <version>2.9.8</version>
		</dependency>
```
- �޽��� ������ ���
```xml
<!-- Validator, Conversion Service, Formatter Message Converter ���� -->
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
- jquery�� ����� Ajax ���
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
- Controller���� ResponseBody�� ����� �޽��� ������ ���
  - View�� return�ϴ°��� �ƴ� ������ ��ü�� return
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
- JSON ������ �����ϱ�
```java
		if(accept.matches(".*application/json.*")) {
			// JSON ����
			response.setStatus(HttpServletResponse.SC_OK);
			
			JSONResult jsonResult = JSONResult.fail(errors.toString());
			String result = new ObjectMapper().writeValueAsString(jsonResult); // jackson ���
			//System.out.println(result);
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes("utf-8"));
			os.close();
		}else {
			// 2. �ȳ������� ���� + ��������
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}
```