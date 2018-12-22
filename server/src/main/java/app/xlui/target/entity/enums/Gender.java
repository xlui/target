package app.xlui.target.entity.enums;

import java.util.Arrays;

/**
 * Gender of users.
 */
public enum Gender {
	MAN(1), WOMAN(-1), UNKNOWN(0);

	private int value;

	Gender(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

	public static Gender parse(int value) {
		return Arrays.stream(values())
				.filter(gender -> gender.value == value)
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Invalid value of gender"));
	}
}
