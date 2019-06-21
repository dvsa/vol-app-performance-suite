package utils.api;

import activesupport.aws.s3.*;
import activesupport.database.DBUnit;
import activesupport.database.url.DbURL;
import activesupport.ssh.SSH;
import com.jcraft.jsch.Session;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import utils.API_CreateAndGrantAPP.CreateLicenceAPI;
import utils.SQLquery;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Optional;


public class SelfServeRegisterUser {

    static String LOGIN_CSV_FILE = "src/test/resources/loginId.csv";
    static String CSV_HEADERS = "Username,Forename,Password";

    private String users = System.getProperty("users");

    @Test
    public void mainTest() throws Exception {
        users = String.valueOf(Integer.valueOf(System.getProperty("users")));
        String env = System.getProperty("env").toLowerCase();
        if (env.equals("qa")) {
            registerUser();
        } else {
            getExternalUsersFromTable();
        }
    }

    @Test
    public void deleteFile() {
        File loginDetails = new File(LOGIN_CSV_FILE);
        if (loginDetails.delete()) {
            System.out.println(loginDetails + " deleted");
        } else {
            System.out.println(loginDetails + " doesn't exist");
        }
    }

    @Test
    public void registerUser() throws Exception {
        CreateLicenceAPI registerUser = new CreateLicenceAPI();
        String applicationNumber = registerUser.getApplicationNumber();
        String password;

        for (int i = 0; i < Integer.parseInt(String.valueOf(users)); i++) {
            if (applicationNumber == null) {
                registerUser.registerUser();
                registerUser.getUserDetails();
                String email = registerUser.getEmailAddress();
                password = S3.getTempPassword(email);
                writeToFile(CSV_HEADERS, registerUser.getLoginId(), password, registerUser.getForeName());
            }
        }
    }

    private void getExternalUsersFromTable() throws Exception {
        Optional<String> ldapUsername = Optional.ofNullable(System.getProperty("ldapUser"));
        Optional<String> sshPrivateKeyPath = Optional.ofNullable(System.getProperty("sshPrivateKeyPath"));

        Optional<String> intSSPassword = Optional.ofNullable(S3SecretsManager.getSecretValue("intSS", S3SecretsManager.createSecretManagerClient("secretsmanager.eu-west-1.amazonaws.com", "eu-west-1")));
        Optional<String> intDBUser = Optional.ofNullable(S3SecretsManager.getSecretValue("intDBUser", S3SecretsManager.createSecretManagerClient("secretsmanager.eu-west-1.amazonaws.com", "eu-west-1")));
        Optional<String> intDBPassword = Optional.ofNullable(S3SecretsManager.getSecretValue("intDBPass", S3SecretsManager.createSecretManagerClient("secretsmanager.eu-west-1.amazonaws.com", "eu-west-1")));

        System.setProperty("dbUsername", String.valueOf(intDBUser));
        System.setProperty("dbPassword", String.valueOf(intDBPassword));

        if (ldapUsername != null) {
            DbURL.setPortNumber(createSSHsession(ldapUsername, "dbam.olcs.int.prod.dvsa.aws", sshPrivateKeyPath, "olcsreaddb-rds.olcs.int.prod.dvsa.aws"));
        }

        ResultSet set = DBUnit.checkResult(SQLquery.getUsersSql(String.valueOf(users)));
        while (set.next()) {
            String username = set.getString("Username");
            String familyName = set.getString("Forename");
            writeToFile(CSV_HEADERS, username, familyName, String.valueOf(intSSPassword));
        }
        set.close();
    }

    private void writeToFile(String header, String userId, String password, String forename) throws Exception {
        FileWriter fileWriter = new FileWriter(LOGIN_CSV_FILE, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

        if (!searchForString(LOGIN_CSV_FILE, CSV_HEADERS)) {
            csvPrinter.printRecord((Object[]) header.split(","));
            csvPrinter.printRecord(Arrays.asList(userId, forename, password));
            csvPrinter.flush();
        } else {
            csvPrinter.printRecord(Arrays.asList(userId, forename, password));
            csvPrinter.flush();
        }
    }

    private boolean searchForString(String file, String searchText) throws IOException {
        boolean foundIt;
        File f = new File(file);
        if (f.exists() && (FileUtils.readFileToString(new File(file), "UTF-8").contains(searchText)))
            foundIt = true;
        else {
            System.out.println("File not found or text not found");
            foundIt = false;
        }
        return foundIt;
    }

    private int createSSHsession(Optional<String> username, String remoteHost, Optional<String> pathToSSHKey, String destinationHost) throws Exception {
        Session session = SSH.openTunnel(String.valueOf(username), remoteHost, String.valueOf(pathToSSHKey));
        int port = SSH.portForwarding(3309, destinationHost, 3306, session);
        return port;
    }
}