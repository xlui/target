package app.xlui.target.mapper;

import app.xlui.target.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalDateTypeHandler;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Mapper
@Repository
public interface UserMapper {
	@Select("SELECT * FROM t_user WHERE uid = #{uid}")
	@Results(
			@Result(property = "birthday", column = "birthday", javaType = LocalDate.class, jdbcType = JdbcType.DATE, typeHandler = LocalDateTypeHandler.class)
	)
	User findByUID(@Param("uid") long uid);

	@Insert("INSERT INTO t_user(nickname, gender, birthday, username, password) VALUES(#{nickname}, #{gender}, #{birthday}, #{username}, #{password})")
	int insert(User user);

	@Delete("DELETE FROM t_user WHERE uid = #{uid}")
	void delete(@Param("uid") long uid);

	@Update("UPDATE t_user SET nickname = #{nickname} WHERE uid = #{uid}")
	User update(User user);
}
