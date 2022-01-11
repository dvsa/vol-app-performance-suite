package utils.api;

import activesupport.aws.s3.*;
import activesupport.database.DBUnit;
import activesupport.database.url.DbURL;
import activesupport.ssh.SSH;
import apiCalls.actions.RegisterUser;
import com.jcraft.jsch.Session;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.junit.Test;
import utils.SQLquery;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Optional;


public class SelfServeRegisterUser {

    private static final Logger LOGGER = LogManager.getLogger(SelfServeRegisterUser.class);

    private static final String LOGIN_CSV_FILE = "src/test/resources/loginId.csv";
    private static final String CSV_HEADERS = "Username,Forename,Password";

    private String users = System.getProperty("users");


    public static void getExternalUsersFromTable(){
        try{
            createTunnel();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://olcsdb-rds.int.olcs.dvsacloud.uk:3306/OLCS_RDS_OLCSDB?user={user:}&password={password:}?useSSL=FALSE");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(SQLquery.getUsersSql("10"));
            while(rs.next())
                System.out.println(rs.getString("Username")+"  "+rs.getString("Forename"));
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    public String htmlTableRowCounter(String table){
        Document document = Jsoup.parse(table, "",  Parser.xmlParser());
        Elements element = document.select("tr.row");
        return String.valueOf(element.size());
    }

    @Test
    public void mainTest() throws Exception {
        users = String.valueOf(Integer.valueOf(System.getProperty("users")));
        String env = System.getProperty("env").toLowerCase();
        if (env.equals("qa") || env.equals("da")) {
            registerUser();
        } else {
//            getExternalUsersFromTable();
        }
    }

    @Test
    public void deleteFile() {
        File loginDetails = new File(LOGIN_CSV_FILE);
        if (loginDetails.delete()) {
            LOGGER.info(loginDetails + " has been deleted");
        } else {
            LOGGER.info(loginDetails + " doesn't exist");
        }
    }

    @Test
    public void registerUser() throws Exception {
        String password;
        for (int i = 0; i < Integer.parseInt(users); i++) {
            RegisterUser registerUser = new RegisterUser();
            registerUser.registerUser();
            String email = registerUser.getEmailAddress();
            password = S3.getTempPassword(email, "devapp-olcs-pri-olcs-autotest-s3");
            writeToFile(CSV_HEADERS, registerUser.getUserName(), registerUser.getForeName(),password);
        }
    }

    private static void createTunnel() throws Exception {
        DbURL dbURL = new DbURL();

        Optional<String> ldapUsername = Optional.ofNullable(System.getProperty("ldapUser"));
        Optional<String> sshPrivateKeyPath = Optional.ofNullable(System.getProperty("sshPrivateKeyPath"));

        System.setProperty("dbUsername", "dbUsername");
        System.setProperty("dbPassword","dbPassword");

        if (ldapUsername.isPresent()) {
            dbURL.setPortNumber(createSSHsession(ldapUsername, "dbam.olcs.int.prod.dvsa.aws", sshPrivateKeyPath, "localhost"));
        }
    }

    private void writeToFile(String header, String userId, String forename, String password) throws Exception {
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
            LOGGER.info("File not found or text not found");
            foundIt = false;
        }
        return foundIt;
    }

    private static int createSSHsession(Optional<String> username, String remoteHost, Optional<String> pathToSSHKey, String destinationHost) throws Exception {
        Session session = SSH.openTunnel(String.valueOf(username), remoteHost, String.valueOf(pathToSSHKey));
        return SSH.portForwarding(3309, destinationHost, 3306, session);
    }
}