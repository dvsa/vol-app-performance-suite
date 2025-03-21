package utils;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.*;

public class FeederConfig {

    public static FeederBuilder<String> getFeeder() {
        if ("prep".equalsIgnoreCase(utils.SetUp.env)) {
            return csv("loginId_int.csv").eager();
        } else {
            return csv("loginId.csv").circular();
        }
    }
}