package com.example.pet.provider.utils;

public final class NumberParseUtils {

    static final String NUMERIC_STRING_REGEX = "\\d+";
    static final String SUB_GIGO = "G";
    static final String SUB_MEGA = "M";
    static final String SUB_KILO = "K";
    static final String EMPTY_VALUE = "-";

    private NumberParseUtils() {
    }

    public static Integer toInt(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }

        if (value.matches(NUMERIC_STRING_REGEX)) {
            return Integer.parseInt(value);
        }

        throw new IllegalArgumentException(String.format("Cannot be converted to Integer: %s", value));
    }

    public static Double toDouble(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }

        if (value.matches(NUMERIC_STRING_REGEX) || value.contains(".")) {
            return Double.parseDouble(value);
        }

        throw new IllegalArgumentException(String.format("Cannot be converted to Double: %s", value));
    }

    public static Long toLong(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }
        if (value.contains(SUB_GIGO)) {
            return (long) (Double.parseDouble(value.replace(SUB_GIGO, "")) * 1_000_000_000);
        }
        if (value.contains(SUB_MEGA)) {
            return (long) (Double.parseDouble(value.replace(SUB_MEGA, "")) * 1_000_000);
        }
        if (value.contains(SUB_KILO)) {
            return (long) (Float.parseFloat(value.replace(SUB_KILO, "")) * 1_000);
        }
        if (value.matches(NUMERIC_STRING_REGEX)) {
            return Long.parseLong(value);
        }
        throw new IllegalArgumentException(String.format("Cannot be converted to Long: %s", value));
    }

    public static String checkString(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }
        return value;
    }
}
