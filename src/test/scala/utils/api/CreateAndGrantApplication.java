package utils.api;

import activesupport.aws.s3.S3;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import utils.api.External.CreateInterimGoodsLicenceAPI;
import utils.api.Internal.GrantApplicationAPI;

import java.io.*;
import java.util.Arrays;


public class CreateAndGrantApplication {

    final String LOGIN_CSV_FILE = "./loginId.csv";
    final String CSV_HEADERS = "UserId,Password,Licence";
    String users = System.getProperty("users");

    @Test
    public void grantApp() throws Exception {
        GrantApplicationAPI grantApp = new GrantApplicationAPI();
        CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();
        String applicationNumber = goodsApp.getApplicationNumber();
        String password;

        for (int i = 0; i < 60; i++) {
            if (applicationNumber == null) {
                goodsApp.createGoodsApp();
                String email = goodsApp.getEmailAddress();
                password = S3.getTempPassword(email);
                writeToFile(CSV_HEADERS, goodsApp.getLoginId(), password, goodsApp.getLicenceNumber());
                grantApp.createOverview(goodsApp.getApplicationNumber());
                grantApp.getOutstandingFees(goodsApp.getApplicationNumber());
                grantApp.payOutstandingFees(goodsApp.getOrganisationId(), goodsApp.getApplicationNumber());
                grantApp.grant(goodsApp.getApplicationNumber());
                grantApp.payGrantFees(goodsApp.getOrganisationId(), goodsApp.getApplicationNumber());
            }

        }
    }

    public void writeToFile(String header, String userId, String password, String licenceNumber) throws Exception {
        String user = userId;
        String pass = password;
        String licence = licenceNumber;
        FileWriter fileWriter = new FileWriter(LOGIN_CSV_FILE, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);


        if (!searchForString(LOGIN_CSV_FILE, CSV_HEADERS)) {
            csvPrinter.printRecord(header.split(","));
            csvPrinter.printRecord(Arrays.asList(user, pass, licence));
            csvPrinter.flush();
        } else {
            csvPrinter.printRecord(Arrays.asList(user, pass, licence));
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

