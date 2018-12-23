package app.xlui.target.util;

import app.xlui.target.entity.User;
import app.xlui.target.entity.common.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserUtils {
	public static final MediaType json = MediaType.APPLICATION_JSON_UTF8;
	public static final ObjectMapper mapper = new ObjectMapper();

	public static final String username = "i@xlui.me";
	public static final String testUsername = "example@example.com";
	public static final String testPassword = "password-for-test";

	static {
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	public static ApiResponse login(MockMvc mockMvc) throws Exception {
		User user = new User(username, "pass");
		System.out.println(mapper.writeValueAsString(user));
		String result = mockMvc.perform(post("/login").contentType(json).content(mapper.writeValueAsString(user)))
//				.andDo(r -> System.out.println(r.getResponse().getContentAsString()))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		return mapper.readValue(result, ApiResponse.class);
	}
}
