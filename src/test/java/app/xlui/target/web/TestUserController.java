package app.xlui.target.web;

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
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
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
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	// token
	@Test
	public void testTokenLogin() throws Exception {
		User user = new User("xlui", "pass");
		String result = mockMvc.perform(post("/login").contentType(json).content(mapper.writeValueAsString(user)))
				.andDo(r -> System.out.println(r.getResponse().getContentAsString()))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		ApiResponse response = mapper.readValue(result, ApiResponse.class);
		mockMvc.perform(get("/token").header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andDo(r -> System.out.println(r.getResponse().getContentAsString()))
				.andExpect(status().isOk())
				.andExpect(content().string("pass token auth: xlui"));
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
