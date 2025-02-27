package utils;

import java.util.HashMap;
import java.util.Map;

public class Header {

    private static final Map<String, String> acceptHeaders = new HashMap<>();

    static {
        acceptHeaders.put("Accept", "*/*");
        acceptHeaders.put("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
        acceptHeaders.put("Accept-Encoding", "gzip, deflate");
    }

    public static Map<String, String> getAcceptHeaders() {
        return acceptHeaders;
    }

    private static final Map<String, String> formHeaders = new HashMap<>();

    static {
        formHeaders.put("Content-Type", "application/x-www-form-urlencoded");
        formHeaders.put("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
        formHeaders.put("Accept-Encoding", "gzip, deflate");
        formHeaders.put("Accept", "*/*");
    }

    public static Map<String, String> getFormHeaders() {
        return formHeaders;
    }
}
