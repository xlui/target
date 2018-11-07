package app.xlui.target.util;

import app.xlui.target.exception.common.AssertException;

import java.util.function.Supplier;

public class AssertUtils {
	public static <T> T requireNotNull(T obj, Supplier<AssertException> supplier) {
		if (obj == null) {
			throw supplier.get();
		}
		return obj;
	}

	public static String requireValid(String str, Supplier<AssertException> supplier) {
		if (str == null || str.equals("")) {
			throw supplier.get();
		}
		return str;
	}

	public static <T> void assertEquals(T o1, T o2, Supplier<AssertException> supplier) {
		if (!o1.equals(o2)) {
			throw supplier.get();
		}
	}

	public static int assertNotZero(int i, Supplier<RuntimeException> supplier) {
		if (i == 0) throw supplier.get();
		return i;
	}
}
