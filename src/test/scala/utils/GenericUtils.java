package utils;

import activesupport.config.Configuration;
import com.typesafe.config.Config;

import java.util.Random;

public class GenericUtils {

    private static final Random RANDOM = new Random();
    private static final Config CONFIG = new Configuration().getConfig(); // Ensure Configuration class exists

    public static int orderRef() {
        return RANDOM.nextInt(Integer.MAX_VALUE);
    }

    public static int randomInt() {
        return RANDOM.nextInt(10) + 1; // Generates between 1 and 10 (inclusive)
    }

    public static String password(String env) {
        if ("prep".equals(env)) {
            return CONFIG.getString("intPassword");
        } else {
            return "${Password}"; // Returns as-is if not "int"
        }
    }
}
