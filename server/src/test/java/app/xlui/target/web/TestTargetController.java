package app.xlui.target.web;

import app.xlui.target.entity.common.ApiResponse;
import app.xlui.target.entity.Target;
import app.xlui.target.util.FakeUtils;
import app.xlui.target.util.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestTargetController {
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

	// delete target
	@Test
	public void testTargetDelete() throws Exception {
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(delete("/target/1").header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isOk());
	}

	@Test
	public void testTargetDeleteWithErrorTid() throws Exception {
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(delete("/target/21135164").header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testTargetDeleteWithInvalidTid() throws Exception {
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(delete("/target/231esad").header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testTargetDeleteWithoutTid() throws Exception {
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(delete("/target").header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isMethodNotAllowed());
	}

	// update
	@Test
	public void testUpdateTarget() throws Exception {
		Target target = new Target()
				.setUid(1L)
				.setTitle("test title")
				.setRepeat((byte) 64);
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(put("/target/1").contentType(json).content(mapper.writeValueAsString(target)).header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isNoContent());
	}

	@Test
	public void testUpdateTargetWithNotTID() throws Exception {
		Target target = new Target()
				.setUid(1L)
				.setTitle("test title");
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(put("/target").contentType(json).content(mapper.writeValueAsString(target)).header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void testUpdateTargetWithoutTitle() throws Exception {
		Target target = new Target()
				.setUid(1L)
				.setRepeat((byte) 64);
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(put("/target/1").contentType(json).content(mapper.writeValueAsString(target)).header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath(content).value("Target title is invalid!"));
	}

	// get specify target
	@Test
	public void testGetTarget() throws Exception {
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(get("/target/1").header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetInexistentTarget() throws Exception {
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(get("/target/213312341").header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath(content).value("Tid is invalid!"));
	}

	// create target
	@Test
	public void testPostTarget() throws Exception {
		Faker faker = FakeUtils.faker();
		Target target = new Target()
				.setUid(1L)
				.setTitle(faker.book().title())
				.setDescription(faker.lorem().sentence())
				.setRepeat((byte) 64);
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(post("/target")
				.contentType(json)
				.content(mapper.writeValueAsString(target))
				.header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isCreated())
				.andExpect(jsonPath(content).value("Successfully add a new target!"));
	}

	@Test
	public void testPostTargetInvalidTitle() throws Exception {
		Target target = new Target()
				.setUid(1)
				.setTitle(null)
				.setRepeat((byte) 64);
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(post("/target")
				.contentType(json)
				.content(mapper.writeValueAsString(target))
				.header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath(content).value("Target title is invalid!"));
	}

	// get all targets
	@Test
	public void testGetTargets() throws Exception {
		ApiResponse response = UserUtils.login(mockMvc);
		mockMvc.perform(get("/target").header(HttpHeaders.AUTHORIZATION, response.getContent()))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetTargetsWithWrongToken() throws Exception {
		mockMvc.perform(get("/target").header(HttpHeaders.AUTHORIZATION, "sadasdas"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void testGetTargetsWithoutToken() throws Exception {
		mockMvc.perform(get("/target"))
				.andExpect(status().isForbidden());
	}
}
