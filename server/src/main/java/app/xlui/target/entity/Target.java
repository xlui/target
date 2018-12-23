package app.xlui.target.entity;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Target entity
 */
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
	// continuous
	private int continuous = 0;
	private int maxContinuous = 0;
	// created
	private LocalDateTime created;

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

	public int getContinuous() {
		return continuous;
	}

	public Target setContinuous(int continuous) {
		this.continuous = continuous;
		return this;
	}

	public int getMaxContinuous() {
		return maxContinuous;
	}

	public Target setMaxContinuous(int maxContinuous) {
		this.maxContinuous = maxContinuous;
		return this;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public Target setCreated(LocalDateTime created) {
		this.created = created;
		return this;
	}
}
