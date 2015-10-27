package hu.zforgo.common.util;

public class StringUtil {

	public static final char DEFAULT_DELIMITER = ';';
	public static final String DEFAULT_DELIMITER_STRING = String.valueOf(DEFAULT_DELIMITER);

	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	public static boolean isEmpty(String input) {
		return input == null || input.length() == 0;
	}

	public static boolean isNotEmpty(String input) {
		return input != null && input.length() > 0;
	}

	public static boolean isWhite(String input) {
		return isEmpty(input) || input.trim().length() == 0;
	}
}
