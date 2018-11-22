package app.xlui.target.util;

import app.xlui.target.exception.common.AssertException;

import java.util.function.Supplier;

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
}
