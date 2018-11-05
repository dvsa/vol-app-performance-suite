package utils.api;

import activesupport.aws.s3.S3;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import utils.API_CreateAndGrantAPP.CreateLicenceAPI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class SelfServeRegisterUser {

    final String LOGIN_CSV_FILE = "./loginId.csv";
    final String CSV_HEADERS = "Username,Password,Forename";
    String users = System.getProperty("users");

    @Test
    public void registerUser() throws Exception {
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

    private void writeToFile(String header, String userId, String password, String forename) throws Exception {
        String user = userId;
        String pass = password;
        String foreName = forename;
        FileWriter fileWriter = new FileWriter(LOGIN_CSV_FILE, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

        if (!searchForString(LOGIN_CSV_FILE, CSV_HEADERS)) {
            csvPrinter.printRecord((Object[]) header.split(","));
            csvPrinter.printRecord(Arrays.asList(user, pass, foreName));
            csvPrinter.flush();
        } else {
            csvPrinter.printRecord(Arrays.asList(user, pass, foreName));
            csvPrinter.flush();
        }
    }

    public boolean searchForString(String file, String searchText) throws IOException {
        boolean foundIt = true;
        File f = new File(file);
        if (f.exists() && (FileUtils.readFileToString(new File(file), "UTF-8").contains(searchText)))
            foundIt = true;
        else {
            System.out.println("File not found or text not found");
            foundIt = false;
        }
        return foundIt;
    }

    public void deleteFile() {
        File loginDetails = new File(LOGIN_CSV_FILE);
        if (loginDetails.exists()) {
            loginDetails.delete();
        } else {
            System.out.println(loginDetails + " doesn't exist");
        }
    }
}