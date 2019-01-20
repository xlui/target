package app.xlui.target.mapper;

import app.xlui.target.entity.Record;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Mybatis mapper. Actually this is the final of our service.
 */
@Mapper
@Repository
public interface RecordMapper {
	@Select("SELECT * FROM t_record WHERE uid = #{uid} and tid = #{tid}")
	List<Record> findByUidAndTid(@Param("uid") long uid, @Param("tid") long tid);

	@Select("SELECT * FROM t_record WHERE uid = #{uid}")
	List<Record> findByUid(@Param("uid") long uid);

	@Select("SELECT * FROM t_record")
	List<Record> findAll();

	@Select("SELECT * FROM t_record AS r WHERE r.uid = #{uid} AND r.checkinDateTime BETWEEN #{start} AND #{end}")
	List<Record> findBetween(long uid, LocalDate start, LocalDate end);

	@Select("SELECT * FROM t_record AS r " +
			"WHERE DATE(r.checkinDateTime) BETWEEN #{start} AND #{end}")
	List<Record> findAllBetween(LocalDate start, LocalDate end);

	@Select("SELECT COUNT(*) FROM t_record AS r " +
			"WHERE r.uid = #{uid} AND " +
			"DATE(r.checkinDateTime) BETWEEN #{start} AND #{end}")
	int countBetween(long uid, LocalDate start, LocalDate end);

	@Select("SELECT * FROM t_record AS r " +
			"WHERE r.tid = #{tid} AND " +
			"DATE_FORMAT(r.checkinDateTime, '%Y%m') = DATE_FORMAT(#{date}, '%Y%m')")
	List<Record> findRecordSomeMonth(@Param("tid") long tid, @Param("date") LocalDate date);

	@Select("SELECT * FROM t_record WHERE tid = #{tid} AND TO_DAYS(checkinDateTime) = TO_DAYS(#{checkinDate})")
	Record findRecordSomeday(@Param("tid") long tid, @Param("checkinDate") LocalDate date);

	@Select("SELECT COUNT(*) FROM t_record WHERE tid = #{tid} AND TO_DAYS(checkinDateTime) = TO_DAYS(NOW())")
	int countRecordToday(@Param("tid") long tid);

	@Insert("INSERT INTO t_record(uid, tid, checkinDateTime) VALUES(#{uid}, #{tid}, #{checkinDateTime})")
	void save(@Param("uid") long uid, @Param("tid") long tid, @Param("checkinDateTime") LocalDateTime datetime);

	@Delete("DELETE FROM t_record WHERE tid = #{tid}")
	void clear(@Param("tid") long tid);

	@Delete("TRUNCATE TABLE t_record")
	void clearAll();

	@Select("SELECT COUNT(*) FROM t_record WHERE tid = #{tid}")
	int checkedDays(@Param("tid") long tid);

	@Insert("INSERT INTO t_record(uid, tid, checkinDateTime, reCheckIn, reason) " +
			"VALUES(#{uid}, #{tid}, #{checkinDateTime}, TRUE, #{reason})")
	void recheckin(@Param("uid") long uid, @Param("tid") long tid, @Param("checkinDateTime") LocalDateTime datetime, @Param("reason") String reason);
}
