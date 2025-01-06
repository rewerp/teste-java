package com.application.util;

import java.util.Optional;

public class NumberUtils {

    public static double parseDoubleOrDefault(String value, double defaultValue) {
        return Optional.ofNullable(value)
                .map(v -> {
                    try {
                        return Double.parseDouble(v);
                    } catch (NumberFormatException e) {
                        return defaultValue;
                    }
                })
                .orElse(defaultValue);
    }
    
    public static int parseIntOrDefault(String value, int defaultValue) {
        return Optional.ofNullable(value)
                .map(v -> {
                    try {
                        return Integer.parseInt(v);
                    } catch (NumberFormatException e) {
                        return defaultValue;
                    }
                })
                .orElse(defaultValue);
    }
	
}
