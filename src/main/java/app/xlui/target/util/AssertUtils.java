package app.xlui.target.util;

import app.xlui.target.exception.AssertException;

import java.util.function.Supplier;

public class AssertUtils {
	public static <T> T requireNotNull(T obj, Supplier<AssertException> supplier) throws AssertException {
		if (obj == null) {
			throw supplier.get();
		}
		return obj;
	}
}
