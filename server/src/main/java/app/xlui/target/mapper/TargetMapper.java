package app.xlui.target.mapper;

import app.xlui.target.entity.Target;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TargetMapper {
	@Select("SELECT * FROM t_target WHERE tid = #{tid}")
	Target findByTID(@Param("tid") long tid);

	@Select("SELECT * FROM t_target WHERE uid = #{uid}")
	List<Target> findByUID(@Param("uid") long uid);

	@Select("SELECT * FROM t_target WHERE uid = #{uid} AND tid = #{tid}")
	Target findByUIDAndTID(@Param("uid") long uid, @Param("tid") long tid);

	@Select("SELECT tid FROM t_target")
	List<Long> findTIDs();

	@Update("UPDATE t_target SET " +
			"title=#{title},description=#{description},startDate=#{startDate},endDate=#{endDate},checkinStart=#{checkinStart},checkinEnd=#{checkinEnd},`repeat`=#{repeat} " +
			"WHERE tid = #{tid}")
	int update(Target target);

	@Insert("INSERT INTO t_target(uid, title, description, startDate, endDate, checkinStart, checkinEnd, `repeat`)" +
			"VALUES(#{uid}, #{title}, #{description}, #{startDate}, #{endDate}, #{checkinStart}, #{checkinEnd}, #{repeat})")
	int save(Target target);

	@Delete("DELETE FROM t_target WHERE tid = #{tid}")
	int delete(@Param("tid") long tid);

	@Delete("TRUNCATE TABLE t_target")
	void clear();
}
