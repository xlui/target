package app.xlui.target.util;

import app.xlui.target.exception.common.AssertException;

import java.util.List;
import java.util.function.Supplier;

/**
 * Assert utils. Using java 8 lambda api for easy asserts.
 */
@SuppressWarnings("UnusedReturnValue")
public class AssertUtils {
	public static <T> T requireNotNull(T obj, Supplier<AssertException> supplier) {
		if (obj == null) throw supplier.get();
		return obj;
	}

	public static String requireValid(String str, Supplier<AssertException> supplier) {
		if (str == null || str.equals("")) throw supplier.get();
		return str;
	}

	public static void requireTrue(boolean b, Supplier<AssertException> supplier) {
		if (!b) throw supplier.get();
	}

	public static void requireFalse(boolean b, Supplier<AssertException> supplier) {
		if (b) throw supplier.get();
	}

	public static <T> void requireEquals(T o1, T o2, Supplier<AssertException> supplier) {
		if (!o1.equals(o2)) throw supplier.get();
	}

	public static int requireNotZero(int i, Supplier<RuntimeException> supplier) {
		if (i == 0) throw supplier.get();
		return i;
	}

	public static int requireNotEmpty(List<?> list, Supplier<AssertException> supplier) {
		if (list.size() == 0) throw supplier.get();
		return list.size();
	}

	public static <T> T orElse(T t, T other) {
		return t != null ? t : other;
	}
}
