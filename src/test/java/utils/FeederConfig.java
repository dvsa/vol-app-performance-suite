package utils;

import io.gatling.javaapi.core.FeederBuilder;
import test.TestSetup;

/**
 * @deprecated Use TestSetup.getTempPasswordUserFeeder() directly
 */
@Deprecated
public class FeederConfig {
    
    public static FeederBuilder<Object> getFeeder() {
        return TestSetup.getTempPasswordUserFeeder();
    }
}