package app.xlui.target.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	public void set(String key, String value, Duration timeout) {
		stringRedisTemplate.opsForValue().set(key, value, timeout);
	}

	public String get(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	public Boolean delete(String key) {
		return stringRedisTemplate.delete(key);
	}
}
