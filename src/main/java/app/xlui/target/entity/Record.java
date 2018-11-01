package app.xlui.target.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Record implements Serializable {
	private static final long serialVersionUID = 1L;

	private long rid;
	private long uid;
	private long tid;
	private LocalDateTime punchDateTime;

	public Record() {
	}

	public long getRid() {
		return rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public LocalDateTime getPunchDateTime() {
		return punchDateTime;
	}

	public void setPunchDateTime(LocalDateTime punchDateTime) {
		this.punchDateTime = punchDateTime;
	}
}
