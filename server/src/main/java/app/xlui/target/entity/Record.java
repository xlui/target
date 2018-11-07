package app.xlui.target.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Record implements Serializable {
	private static final long serialVersionUID = 1L;

	private long rid;
	private long uid;
	private long tid;
	private LocalDateTime punchDateTime;
	private boolean repunch = false;
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

	public LocalDateTime getPunchDateTime() {
		return punchDateTime;
	}

	public Record setPunchDateTime(LocalDateTime punchDateTime) {
		this.punchDateTime = punchDateTime;
		return this;
	}

	public boolean isRepunch() {
		return repunch;
	}

	public Record setRepunch(boolean repunch) {
		this.repunch = repunch;
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
