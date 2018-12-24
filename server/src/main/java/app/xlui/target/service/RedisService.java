package app.xlui.target.service;

import app.xlui.target.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

/**
 * Redis service. This server provides apis for operating redis. Actually is a
 * simple package of native apis.
 */
@Service
public class RedisService {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	public void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	public void set(String key, String value, Duration timeout) {
		stringRedisTemplate.opsForValue().set(key, value, timeout);
	}

	public String sGet(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	public Boolean sDelete(String key) {
		return stringRedisTemplate.delete(key);
	}

	public void zSet(String key, long uid, int count) {
		redisTemplate.opsForZSet().add(key, uid, count);
	}

	public Set<ZSetOperations.TypedTuple<Long>> zRange(String key, long start, long end) {
		return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
	}

	public Set<ZSetOperations.TypedTuple<Long>> zReverseRange(String key, long start, long end) {
		return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
	}

	public Set<ZSetOperations.TypedTuple<Long>> zRangeByScore(String key, double min, double max) {
		return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
	}

	public Long zGetRank(String key, long uid) {
		return redisTemplate.opsForZSet().rank(key, uid);
	}

	public Long zGetReverseRank(String key, long uid) {
		return AssertUtils.orElse(redisTemplate.opsForZSet().reverseRank(key, uid), -1L) + 1L;
	}

	public void zDelete(String key) {
		redisTemplate.delete(key);
	}
}
