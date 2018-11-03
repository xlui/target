package app.xlui.target.controller;

import app.xlui.target.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserController {
	private MockMvc mockMvc;
	private ObjectMapper objectMapper = new ObjectMapper();
	private MediaType json = MediaType.APPLICATION_JSON_UTF8;
	@Autowired
	private WebApplicationContext context;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testInvalidUsername() throws Exception {
		User user = new User(null, "");
		mockMvc.perform(MockMvcRequestBuilders.post("/register")
						.contentType(json)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(json))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Username must be not empty!"));
	}

	@Test
	public void testInvalidPassword() throws Exception {
		User user = new User("", null);
		mockMvc.perform(MockMvcRequestBuilders.post("/register")
						.contentType(json)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Password must be not empty!"));
	}

	@Test
	public void testSuccesfullyRegister() throws Exception {
		User user = new User("", "");
		mockMvc.perform(MockMvcRequestBuilders.post("/register")
						.contentType(json)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Successfully register! Welcome!"));
	}
}
