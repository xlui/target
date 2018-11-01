package app.xlui.target.entity;

import app.xlui.target.entity.enums.Week;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Target implements Serializable {
	private static final long serialVersionUID = 1L;

	private long tid;				// target id
	private long uid;				// user id
	private String avatar;			// target avatar
	private String title;			// target title
	private String description;		// target description
	private LocalDate startDate;	// target start date
	private LocalDate endDate;		// target end date
	private LocalTime punchStart;	// target punch start time
	private LocalTime punchEnd;		// target punch end time
	private Week repeat;			// target repeat rule

	public Target() {
	}

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getPunchStart() {
		return punchStart;
	}

	public void setPunchStart(LocalTime punchStart) {
		this.punchStart = punchStart;
	}

	public LocalTime getPunchEnd() {
		return punchEnd;
	}

	public void setPunchEnd(LocalTime punchEnd) {
		this.punchEnd = punchEnd;
	}

	public Week getRepeat() {
		return repeat;
	}

	public void setRepeat(Week repeat) {
		this.repeat = repeat;
	}
}
