package app.xlui.target.entity.enums;

import java.time.DayOfWeek;

public enum Week {
	SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

	public static byte toByte(Week week) {
		switch (week) {
			case SUNDAY:
				return 0b01000000;
			case MONDAY:
				return 0b00100000;
			case TUESDAY:
				return 0b00010000;
			case WEDNESDAY:
				return 0b00001000;
			case THURSDAY:
				return 0b00000100;
			case FRIDAY:
				return 0b00000010;
			case SATURDAY:
				return 0b00000001;
			default:
				throw new RuntimeException("Unknown week value");
		}
	}

	public static byte toByte(DayOfWeek dayOfWeek) {
		return toByte(valueOf(dayOfWeek.toString()));
	}
}
