package app.xlui.target.controller;

import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestUserController {
	private MockMvc mockMvc;
	private MediaType json = MediaType.APPLICATION_JSON_UTF8;
	private ObjectMapper mapper = new ObjectMapper();
	private String content = "$.content";
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	// token
	@Test
	public void testTokenLogin() throws Exception {
		User user = new User("xlui", "pass");
		String result = mockMvc.perform(post("/login").contentType(json).content(mapper.writeValueAsString(user)))
				.andReturn().getResponse().getContentAsString();
		ApiResponse response = mapper.readValue(result, ApiResponse.class);
		mockMvc.perform(get("/token").header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isOk())
				.andExpect(jsonPath(content).value("Successfully logged in through token."));
	}

	@Test
	public void testTokenExpire() throws Exception {
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDE0MDI1OTMsImNyZWF0ZUF0IjoiMjAxOC0xMS0wNSAxNToxODoxMyIsInVzZXJuYW1lIjoieGx1aSJ9.tF5vY8ZpQD1kw_iv9YyGSkD9gNpaEy28OJlv3dKL_bw";
		mockMvc.perform(get("/token").header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath(content).value("Token authentication failed!"));
	}

	@Test
	public void testTokenWithInvalidUsername() throws Exception {
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDE0MDM3MjQsImNyZWF0ZUF0IjoiMjAxOC0xMS0wNSAxNTozNzowNCIsInVzZXJuYW1lIjoidXNlcm5hbWUtZm9yLXRlc3QifQ.tF5vY8ZpQD1kw_iv9YyGSkD9gNpaEy28OJlv3dKL_bw";
		mockMvc.perform(get("/token").header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath(content).value("Invalid token!"));
	}

	@Test
	public void testTokenMissing() throws Exception {
		mockMvc.perform(get("/token"))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath(content).value("Missing request header 'Authorization' for method parameter of type String"));
	}

	// login
	@Test
	public void testLoginSuccess() throws Exception {
		User user = new User("xlui", "pass");
		mockMvc.perform(post("/login").contentType(json).content(mapper.writeValueAsString(user)))
				.andExpect(status().isOk());
	}

	@Test
	public void testLoginEmptyUsername() throws Exception {
		User user = new User(null, "");
		mockMvc.perform(post("/login").contentType(json).content(mapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath(content).value("Username must be not empty!"));
	}

	@Test
	public void testLoginInvalidUsername() throws Exception {
		User user = new User("", "");
		mockMvc.perform(post("/login").contentType(json).content(mapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath(content).value("Username must be not empty!"));
	}

	@Test
	public void testLoginWrongUsername() throws Exception {
		User user = new User("username-for-test", "password-for-test");
		mockMvc.perform(post("/login").contentType(json).content(mapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath(content).value("Username or password is wrong!"));
	}

	@Test
	public void testLoginEmptyPassword() throws Exception {
		User user = new User("username-for-test", null);
		mockMvc.perform(post("/login").contentType(json).content(mapper.writeValueAsString(user)))
				.andDo(result -> System.out.println(result.getResponse().getContentAsString()))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath(content).value("Password must be not empty!"));
	}

	@Test
	public void testLoginInvalidPassword() throws Exception {
		User user = new User("username-for-test", "");
		mockMvc.perform(post("/login").contentType(json).content(mapper.writeValueAsString(user)))
				.andDo(result -> System.out.println(result.getResponse().getContentAsString()))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath(content).value("Password must be not empty!"));
	}

	@Test
	public void testLoginWrongPassword() throws Exception {
		User user = new User("username-for-test", "password-for-test");
		mockMvc.perform(post("/login").contentType(json).content(mapper.writeValueAsString(user)))
				.andDo(result -> System.out.println(result.getResponse().getContentAsString()))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath(content).value("Username or password is wrong!"));
	}

	// register
	@Test
	public void testRegisterSuccess() throws Exception {
		User user = new User("t", "t");
		mockMvc.perform(post("/register")
				.contentType(json)
				.content(mapper.writeValueAsString(user)))
				.andExpect(status().isOk())
				.andExpect(jsonPath(content).value("Successfully register! Welcome!"));
	}


	@Test
	public void testRegisterEmptyUsername() throws Exception {
		User user = new User(null, "");
		mockMvc.perform(post("/register")
				.contentType(json)
				.content(mapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(json))
				.andExpect(jsonPath(content).value("Username must be not empty!"));
	}

	@Test
	public void testRegisterInvalidUsername() throws Exception {
		User user = new User("", "");
		mockMvc.perform(post("/register")
				.contentType(json)
				.content(mapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(json))
				.andExpect(jsonPath(content).value("Username must be not empty!"));
	}

	@Test
	public void testRegisterEmptyPassword() throws Exception {
		User user = new User("username", null);
		mockMvc.perform(post("/register")
				.contentType(json)
				.content(mapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath(content).value("Password must be not empty!"));
	}

	@Test
	public void testRegisterInvalidPassword() throws Exception {
		User user = new User("username", "");
		mockMvc.perform(post("/register")
				.contentType(json)
				.content(mapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath(content).value("Password must be not empty!"));
	}

}
