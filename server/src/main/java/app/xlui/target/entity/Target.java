package app.xlui.target.entity;

import app.xlui.target.entity.enums.Week;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Target implements Serializable {
	private static final long serialVersionUID = 3986690845367985790L;

	private long tid;				// target id
	private long uid;				// user id
//	private String avatar;			// target avatar
	@NotBlank(message = "Target title is invalid!")
	private String title;			// target title
	private String description = "";// target description
	private LocalDate startDate;	// target start date
	private LocalDate endDate;		// target end date
	private LocalTime checkinStart;	// target checkin start time
	private LocalTime checkinEnd;	// target checkin end time
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
	@Min(1)@Max(127)
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

	public LocalTime getCheckinStart() {
		return checkinStart;
	}

	public Target setCheckinStart(LocalTime checkinStart) {
		this.checkinStart = checkinStart;
		return this;
	}

	public LocalTime getCheckinEnd() {
		return checkinEnd;
	}

	public Target setCheckinEnd(LocalTime checkinEnd) {
		this.checkinEnd = checkinEnd;
		return this;
	}

	public byte getRepeat() {
		return repeat;
	}

	public Target setRepeat(byte repeat) {
		this.repeat = repeat;
		return this;
	}

	public Target setRepeatWithWeek(Week week) {
		this.repeat = Week.toByte(week);
		return this;
	}

	public Target setRepeatWithMultiWeek(Week ...weeks) {
		this.repeat = 0;
		for (Week week : weeks) {
			this.repeat |= Week.toByte(week);
		}
		return this;
	}
}
