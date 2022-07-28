package com.example.pet.provider.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumberParseUtils {

    private static final String NUMERIC_STRING_REGEX = "\\d+";
    public static final String DOT = ".";
    private static final String SUB_GIGO = "G";
    private static final String SUB_MEGA = "M";
    private static final String SUB_KILO = "K";
    public static final String EMPTY_STRING = "";
    private static final String EMPTY_VALUE = "-";

    public static Integer toInt(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }

        if (value.matches(NUMERIC_STRING_REGEX)) {
            return Integer.parseInt(value);
        }

        throw new IllegalArgumentException(HttpStatus.NOT_FOUND +
                String.format("Cannot be converted to Integer: %s", value));
    }

    public static Double toDouble(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }
        return lettersToDigits(value);
    }

    public static Long toLong(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }
        return (long) lettersToDigits(value);
    }

    public static String checkString(final String value) {
        if (value.trim().equals(EMPTY_VALUE)) {
            return null;
        }
        return value;
    }

    public static boolean isNumber(final String state) {
        try {
            Double.parseDouble(state);
            return true;
        } catch (final NumberFormatException exception) {
            return false;
        }
    }

    private static double lettersToDigits(final String value) {
        if (value.contains(SUB_GIGO)) {
            return Double.parseDouble(value.replace(SUB_GIGO, EMPTY_STRING)) * 1_000_000_000;
        }
        if (value.contains(SUB_MEGA)) {
            return Double.parseDouble(value.replace(SUB_MEGA, EMPTY_STRING)) * 1_000_000;
        }
        if (value.contains(SUB_KILO)) {
            return Double.parseDouble(value.replace(SUB_KILO, EMPTY_STRING)) * 1_000;
        }
        if (value.contains(DOT) || value.matches(NUMERIC_STRING_REGEX)) {
            return Double.parseDouble(value);
        }
        throw new IllegalArgumentException(HttpStatus.NOT_FOUND +
                String.format("Cannot parse value: %s", value));
    }
}