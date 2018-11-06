package app.xlui.target.mapper;

import app.xlui.target.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
	@Select("SELECT * FROM t_user WHERE uid = #{uid}")
	User findByUID(@Param("uid") long uid);

	@Select("SELECT * FROM t_user WHERE username = #{username}")
	User findByUsername(@Param("username") String username);

	@Select("SELECT uid FROM t_user")
	List<Long> findUIDs();

	@Insert("INSERT INTO t_user(nickname, gender, birthday, username, password) VALUES(#{nickname}, #{gender}, #{birthday}, #{username}, #{password})")
	int save(User user);

	@Delete("DELETE FROM t_user WHERE uid = #{uid}")
	void delete(@Param("uid") long uid);

	@Update("UPDATE t_user SET nickname = #{nickname} WHERE uid = #{uid}")
	User update(User user);
}
