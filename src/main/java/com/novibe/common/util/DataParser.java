package com.novibe.common.util;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class DataParser {

    public static String removeWWW(String domain) {
        if (domain.startsWith("www.")) {
            return domain.substring("www.".length());
        }
        return domain;
    }

    public static Stream<String> splitByEol(String data) {
        return Pattern.compile("\\r?\\n").splitAsStream(data);
    }
}
