package app.xlui.target.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Target implements Serializable {
	private static final long serialVersionUID = 1L;

	private long tid;				// target id
	private long uid;				// user id
//	private String avatar;			// target avatar
	private String title;			// target title
	private String description = "";// target description
	private LocalDate startDate;	// target start date
	private LocalDate endDate;		// target end date
	private LocalTime punchStart;	// target punch start time
	private LocalTime punchEnd;		// target punch end time
	/**
	 * use 7 bit to represent Sunday to Saturday.
	 * For example:
	 * 		0b01000000 represent the Sunday
	 * 		0b00100000 represent the Monday
	 * 		0b00010000 represent the Tuesday
	 * 		0b00001000 represent the Wednesday
	 * 		0b00000100 represent the Thursday
	 * 		0b00000010 represent the Friday
	 * 		0b00000001 represent the Saturday
	 * And:
	 * 		0b01111111 represent all days
	 * 		0b01000001 represent only Saturday and Sunday
	 * 		0b00111110 represent only work days
	 */
	private byte repeat;			// target repeat rule

	public Target() {
	}

	public long getTid() {
		return tid;
	}

	public Target setTid(long tid) {
		this.tid = tid;
		return this;
	}

	public long getUid() {
		return uid;
	}

	public Target setUid(long uid) {
		this.uid = uid;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Target setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Target setDescription(String description) {
		this.description = description;
		return this;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public Target setStartDate(LocalDate startDate) {
		this.startDate = startDate;
		return this;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Target setEndDate(LocalDate endDate) {
		this.endDate = endDate;
		return this;
	}

	public LocalTime getPunchStart() {
		return punchStart;
	}

	public Target setPunchStart(LocalTime punchStart) {
		this.punchStart = punchStart;
		return this;
	}

	public LocalTime getPunchEnd() {
		return punchEnd;
	}

	public Target setPunchEnd(LocalTime punchEnd) {
		this.punchEnd = punchEnd;
		return this;
	}

	public byte getRepeat() {
		return repeat;
	}

	public Target setRepeat(byte repeat) {
		this.repeat = repeat;
		return this;
	}
}