package app.xlui.target.mapper;

import app.xlui.target.entity.Record;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RecordMapper {
	@Select("SELECT * FROM t_record WHERE uid = #{uid} and tid = #{tid}")
	List<Record> findByUidAndTid(@Param("uid") long uid, @Param("tid") long tid);

	@Select("SELECT COUNT(*) FROM t_record WHERE tid = #{tid} and TO_DAYS(punchDateTime) = TO_DAYS(NOW())")
	int findRecordToday(@Param("tid") long tid);

	@Insert("INSERT INTO t_record(uid, tid, punchDateTime) VALUES(#{uid}, #{tid}, #{punchDateTime})")
	int save(Record record);

	@Delete("DELETE FROM t_record WHERE tid = #{tid}")
	void clear(@Param("tid") long tid);

	@Delete("TRUNCATE TABLE t_record")
	void clearAll();
}
