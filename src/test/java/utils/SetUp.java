package utils;

import java.util.Optional;
import org.dvsa.testing.lib.url.webapp.webAppURL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;

public class SetUp {

    public static final String typeofTest = Optional.ofNullable(System.getProperty("typeOfTest")).orElse("load");
    public static final String site = System.getProperty("site");
    public static final String applicationType = System.getProperty("applicationType");
    public static final String env = System.getProperty("env").toLowerCase();
    public static final int users = Optional.ofNullable(Integer.getInteger("users")).orElse(0);
    public static final int rampUp = Optional.ofNullable(Integer.getInteger("rampUp")).orElse(0);
    public static final int rampDurationInMin = Optional.ofNullable(Integer.getInteger("duration")).orElse(0);
    public static final String externalURL = webAppURL.build(ApplicationType.EXTERNAL, env).toString();
    public static final String internalURL = webAppURL.build(ApplicationType.INTERNAL, env).toString();

    public static final String baseURL;
    public static final String securityTokenPattern = "id=\"security\" value=\"([^\"]*)&*";
    public static final String location = "name=\"change-password-form\"";
    public static final String cpmsRedirectURL = "action=\"https://sbsctest.e-paycapita.com:443/scp/scpcli?(.*)\"";

    static {
        switch (site) {
            case "ss":
                baseURL = Optional.ofNullable(System.getenv("baseURL")).orElse(externalURL);
                break;
            case "internal":
                baseURL = Optional.ofNullable(System.getenv("baseURL")).orElse(internalURL);
                break;
            default:
                baseURL = externalURL;
                break;
        }
    }
}