package app.xlui.target.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Record implements Serializable {
	private static final long serialVersionUID = 1L;

	private long rid;
	private long uid;
	@Positive(message = "record related tid is invalid!")
	private long tid;
	@NotNull(message = "Missing required argument: checkin date time")
	private LocalDateTime checkinDateTime;
	private boolean reCheckIn = false;
	private String reason;

	public Record() {
	}

	public long getRid() {
		return rid;
	}

	public Record setRid(long rid) {
		this.rid = rid;
		return this;
	}

	public long getUid() {
		return uid;
	}

	public Record setUid(long uid) {
		this.uid = uid;
		return this;
	}

	public long getTid() {
		return tid;
	}

	public Record setTid(long tid) {
		this.tid = tid;
		return this;
	}

	public LocalDateTime getCheckinDateTime() {
		return checkinDateTime;
	}

	public Record setCheckinDateTime(LocalDateTime checkinDateTime) {
		this.checkinDateTime = checkinDateTime;
		return this;
	}

	public boolean isReCheckIn() {
		return reCheckIn;
	}

	public Record setReCheckIn(boolean reCheckIn) {
		this.reCheckIn = reCheckIn;
		return this;
	}

	public String getReason() {
		return reason;
	}

	public Record setReason(String reason) {
		this.reason = reason;
		return this;
	}
}
