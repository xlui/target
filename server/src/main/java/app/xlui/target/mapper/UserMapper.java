package app.xlui.target.mapper;

import app.xlui.target.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mybatis mapper.
 */
@Mapper
@Repository
public interface UserMapper {
	@Select("SELECT * FROM t_user WHERE uid = #{uid}")
	User findByUID(@Param("uid") long uid);

	@Select("SELECT * FROM t_user WHERE username = #{username}")
	User findByUsername(@Param("username") String username);

	@Select("SELECT uid FROM t_user")
	List<Long> findUIDs();

	@Update("UPDATE t_user SET password = #{password} WHERE username = #{username}")
	int updatePassword(@Param("username") String username, @Param("password") String password);

	@Insert("INSERT INTO t_user(nickname, gender, birthday, username, password, registered) " +
			"VALUES(#{nickname}, #{gender}, #{birthday}, #{username}, #{password}, #{registered})")
	int save(User user);

	@Delete("DELETE FROM t_user WHERE uid = #{uid}")
	void delete(@Param("uid") long uid);

}
