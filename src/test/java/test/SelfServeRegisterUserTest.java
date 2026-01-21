package test;

import activesupport.database.url.DbURL;
import activesupport.mailPit.MailPit;
import activesupport.ssh.SSH;
import apiCalls.actions.RegisterUser;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dvsa.testing.lib.url.utils.EnvironmentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.SQLquery;

import javax.naming.ConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Optional;


public class SelfServeRegisterUserTest {
    private static final Logger LOGGER = LogManager.getLogger(SelfServeRegisterUserTest.class);

    private static final String LOGIN_CSV_FILE = "src/test/resources/loginId.csv";
    private static final String CSV_HEADERS = "Username,Forename,Password";
    private static final String DB_URL = "jdbc:mysql://olcsdb-rds.int.olcs.dvsacloud.uk:3306/OLCS_RDS_OLCSDB?useSSL=FALSE";
    private static final String DB_USER = System.getProperty("dbUser");
    private static final String DB_PASSWORD = System.getProperty("dbPassword");

    private final String users = Optional.ofNullable(System.getProperty("users")).orElse("0");


    @BeforeAll
    public static void deleteFile() {
        File loginDetails = new File(LOGIN_CSV_FILE);
        if (loginDetails.exists() && loginDetails.delete()) {
            LOGGER.info("{} has been deleted", loginDetails);
        } else {
            LOGGER.warn("{} does not exist", loginDetails);
        }
    }

    public static void getExternalUsersFromTable() {
        try {
            createTunnel();
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(SQLquery.getUsersSql("10"))) {

                while (rs.next()) {
                    LOGGER.info("Username: {}, Forename: {}", rs.getString("Username"), rs.getString("Forename"));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error fetching external users", e);
        }
    }

    @Test
    public void registerUserTest() {
        String env = System.getProperty("env", "").toLowerCase();
        if (!"prep".equals(env)) {
            registerUser();
        }
    }

    public void registerUser() {
        MailPit mailPit = new MailPit();
        QuotedPrintableCodec codec = new QuotedPrintableCodec();
        int userCount = Integer.parseInt(users);

        for (int i = 0; i < userCount; i++) {
            try {
                RegisterUser registerUser = new RegisterUser();
                registerUser.registerUser();

                String emailAddress = registerUser.getEmailAddress();
                String password = codec.decode(mailPit.retrieveTempPassword(emailAddress));

                writeToFile(registerUser.getUserName(), registerUser.getForeName(), password);
            } catch (Exception e) {
                LOGGER.error("Error registering user", e);
            }
        }
    }

    private static void createTunnel() {
        DbURL dbURL = new DbURL();

        Optional<String> ldapUsername = Optional.ofNullable(System.getProperty("ldapUser"));
        Optional<String> sshPrivateKeyPath = Optional.ofNullable(System.getProperty("sshPrivateKeyPath"));

        System.setProperty("dbUsername", "dbUsername");
        System.setProperty("dbPassword", "dbPassword");

        ldapUsername.ifPresent(username -> {
            try {
                dbURL.setPortNumber(createSSHsession(username, sshPrivateKeyPath.orElseThrow(ConfigurationException::new)));
            } catch (Exception e) {
                LOGGER.error("Error creating SSH tunnel", e);
            }
        });
    }

    private void writeToFile(String userId, String forename, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_CSV_FILE, true));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            if (!searchForString()) {
                csvPrinter.printRecord((Object[]) CSV_HEADERS.split(","));
            }
            csvPrinter.printRecord(Arrays.asList(userId, forename, password));
        } catch (IOException e) {
            LOGGER.error("Error writing to file", e);
        }
    }

    private boolean searchForString() {
        try {
            File file = new File(LOGIN_CSV_FILE);
            if (file.exists()) {
                String content = FileUtils.readFileToString(file, "UTF-8");
                return content.contains(CSV_HEADERS);
            }
        } catch (IOException e) {
            LOGGER.error("Error reading file", e);
        }
        LOGGER.info("File not found or text not found");
        return false;
    }

    private static int createSSHsession(String username, String pathToSSHKey) throws Exception {
        com.jcraft.jsch.Session session = SSH.openTunnel(username, "dbam.olcs.int.prod.dvsa.aws", pathToSSHKey);
        return SSH.portForwarding(3309, "localhost", 3306, session);
    }
}