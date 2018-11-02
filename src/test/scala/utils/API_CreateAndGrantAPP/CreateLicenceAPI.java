package utils.API_CreateAndGrantAPP;

import activesupport.http.RestUtils;
import activesupport.number.Int;
import activesupport.string.Str;
import activesupport.system.Properties;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.dvsa.testing.lib.url.api.URL;
import org.dvsa.testing.lib.url.utils.EnvironmentType;
import utils.API_Builders.ContactDetailsBuilder;
import utils.API_Builders.PersonBuilder;
import utils.API_Builders.SelfServeUserRegistrationDetailsBuilder;
import utils.API_Headers.Headers;

import javax.xml.ws.http.HTTPException;

import static utils.API_Headers.Headers.getHeaders;

public class CreateLicenceAPI {

    private static ValidatableResponse apiResponse;

    private String title;
    private String foreName;
    private String familyName;
    private String birthDate = String.valueOf(Int.random(1900, 2018) + "-" + Int.random(1, 12) + "-" + Int.random(1, 28));
    private String addressLine1 = "API House";
    private String town = "Nottingham";
    private String postcode = "NG23HX";
    private String countryCode = "GB";
    private String organisationName = Str.randomWord(10);
    private String emailAddress = Str.randomWord(6).concat(".tester@dvsa.com");
    private String transManEmailAddress = Str.randomWord(6).concat(".TM@dvsa.com");
    private String applicationNumber;
    private String userId;
    private String username;
    private String loginId;
    private String adminApiHeader = "e91f1a255e01e20021507465a845e7c24b3a1dc951a277b874c3bcd73dec97a1";


    private static int version = 1;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setForeName(String foreName) {
        this.foreName = foreName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }


    public void setTransManEmailAddress(String transManEmailAddress) {
        this.transManEmailAddress = transManEmailAddress;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getForeName() {
        return foreName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    private EnvironmentType env = EnvironmentType.getEnum(Properties.get("env", true));

    public void registerUser() {
        setTitle("title_mr");
        setForeName("Vol-API-".concat(Str.randomWord(3).toLowerCase()));
        setFamilyName("Ann");
        String registerResource = URL.build(env, "user/selfserve/register").toString();
        Headers.headers.put("api", "dvsa");
        setLoginId(Str.randomWord(8));

        PersonBuilder personBuilder = new PersonBuilder().withTitle(getTitle()).withForename(getForeName()).withFamilyName(getFamilyName()).withBirthDate(birthDate);
        ContactDetailsBuilder contactDetailsBuilder = new ContactDetailsBuilder().withEmailAddress(emailAddress).withPerson(personBuilder);

        SelfServeUserRegistrationDetailsBuilder selfServeUserRegistrationDetailsBuilder = new SelfServeUserRegistrationDetailsBuilder().withLoginId(getLoginId()).withContactDetails(contactDetailsBuilder)
                .withOrganisationName(organisationName).withBusinessType("org_t_rc");


        apiResponse = RestUtils.post(selfServeUserRegistrationDetailsBuilder, registerResource, getHeaders());
        userId = apiResponse.extract().jsonPath().getString("id.user");

        if (apiResponse.extract().statusCode() != HttpStatus.SC_CREATED) {
            System.out.println(apiResponse.extract().statusCode());
            System.out.println(apiResponse.extract().response().asString());
            throw new HTTPException(apiResponse.extract().statusCode());
        }
    }

    public void getUserDetails() {
        Headers.headers.put("x-pid", adminApiHeader);

        String userDetailsResource = URL.build(env, String.format("user/selfserve/%s", userId)).toString();
        apiResponse = RestUtils.get(userDetailsResource, getHeaders());
        apiResponse.statusCode(HttpStatus.SC_OK);

        if (apiResponse.extract().statusCode() != HttpStatus.SC_OK) {
            System.out.println(apiResponse.extract().statusCode());
            System.out.println(apiResponse.extract().response().asString());
            throw new HTTPException(apiResponse.extract().statusCode());
        }
    }
}