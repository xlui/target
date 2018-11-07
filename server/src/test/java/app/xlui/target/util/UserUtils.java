package app.xlui.target.util;

import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserUtils {
	private static final MediaType json = MediaType.APPLICATION_JSON_UTF8;
	private static final ObjectMapper mapper = new ObjectMapper();

	public static ApiResponse login(MockMvc mockMvc) throws Exception {
		User user = new User("xlui", "pass");
		String result = mockMvc.perform(post("/login").contentType(json).content(mapper.writeValueAsString(user)))
				.andDo(r -> System.out.println(r.getResponse().getContentAsString()))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		return mapper.readValue(result, ApiResponse.class);
	}
}