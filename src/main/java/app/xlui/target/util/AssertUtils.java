package app.xlui.target.util;

import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.exception.specify.NullInputException;

import java.util.function.Supplier;

public class AssertUtils {
	public static <T> T requireNotNull(T obj, Supplier<NullInputException> supplier) {
		if (obj == null) {
			throw supplier.get();
		}
		return obj;
	}

	public static String requireValid(String str, Supplier<InvalidInputException> supplier) {
		if (str == null || str.equals("")) {
			throw supplier.get();
		}
		return str;
	}
}
