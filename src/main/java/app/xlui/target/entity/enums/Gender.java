package app.xlui.target.entity.enums;

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
		for (Gender gender : values()) {
			if (gender.value == value) {
				return gender;
			}
		}
		throw new IllegalArgumentException("Invalid value of gender");
	}
}
