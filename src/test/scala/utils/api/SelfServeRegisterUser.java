package utils.api;

import activesupport.aws.s3.S3;
import activesupport.database.DBUnit;
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


public class SelfServeRegisterUser {

    static String LOGIN_CSV_FILE = "src/test/resources/loginId.csv";
    static String CSV_HEADERS = "Username,Password,Forename";
    static String users = System.getProperty("users");


    @Test
    public void mainTest() throws Exception {
        if(Integer.parseInt(users) > 20){
            getUsersFromTable();
        }else{
            registerUser();
        }
    }

    @Test
    public void cancelApplication(){
        SQLquery.cancelApplications(users);
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

    private void registerUser() throws Exception {
        CreateLicenceAPI registerUser = new CreateLicenceAPI();
        String applicationNumber = registerUser.getApplicationNumber();
        String password;

        for (int i = 0; i < Integer.parseInt(users); i++) {
            if (applicationNumber == null) {
                registerUser.registerUser();
                registerUser.getUserDetails();
                String email = registerUser.getEmailAddress();
                password = S3.getTempPassword(email);
                writeToFile(CSV_HEADERS, registerUser.getLoginId(), password, registerUser.getForeName());
            }
        }
    }

    private void getUsersFromTable() throws Exception {
        String tempUser = "mij7tFV75idfD4642dfUIdjs";
        ResultSet set = DBUnit.checkResult(SQLquery.getUsersSql(users));

        while (set.next()) {
            String username = set.getString("Username");
            String familyName = set.getString("Forename");
            writeToFile(CSV_HEADERS, username, tempUser, familyName);
        }
        set.close();
    }

    private void writeToFile(String header, String userId, String password, String forename) throws Exception {
        FileWriter fileWriter = new FileWriter(LOGIN_CSV_FILE, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

        if (!searchForString(LOGIN_CSV_FILE, CSV_HEADERS)) {
            csvPrinter.printRecord((Object[]) header.split(","));
            csvPrinter.printRecord(Arrays.asList(userId, password, forename));
            csvPrinter.flush();
        } else {
            csvPrinter.printRecord(Arrays.asList(userId, password, forename));
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
}