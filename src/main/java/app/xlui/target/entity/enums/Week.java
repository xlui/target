package app.xlui.target.entity.enums;

import java.util.Arrays;

public enum Week {
	Monday(1),
	Tuesday(1 << 1),
	Wednesday(1 << 2),
	Thursday(1 << 3),
	Friday(1 << 4),
	Saturday(1 << 5),
	Sunday(1 << 6);

	private int value;

	Week(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

	public static Week parse(int value) {
		return Arrays.stream(values())
				.filter(v -> v.value == value)
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Invalid value for Week!"));
	}
}
