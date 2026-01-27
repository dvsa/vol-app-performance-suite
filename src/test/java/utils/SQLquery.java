package utils;

public class SQLquery {

    public static String getUsersSql(String users) {
        return "SELECT DISTINCT\n" +
                "  u.login_id as Username, p.family_name as Forename\n" +
                "FROM\n" +
                "   OLCS_RDS_OLCSDB.licence l\n" +
                "       JOIN\n" +
                "   OLCS_RDS_OLCSDB.organisation_user ou ON ou.organisation_id = l.organisation_id\n" +
                "       JOIN\n" +
                "   OLCS_RDS_OLCSDB.user u ON u.id = ou.user_id\n" +
                "       JOIN\n" +
                "   OLCS_RDS_OLCSDB.user_role ur ON u.id = ur.user_id\n" +
                "       JOIN\n" +
                "   OLCS_RDS_OLCSDB.contact_details AS con ON u.contact_details_id = con.id\n" +
                "       JOIN\n" +
                "   OLCS_RDS_OLCSDB.person AS p ON con.person_id = p.id\n" +
                "WHERE\n" +
                "       u.login_id NOT REGEXP '_[0-9]'\n" +
                "       AND u.login_id NOT LIKE '% %'\n" +
                "       AND u.account_disabled = '0'\n" +
                "       AND l.status IN ('lsts_valid', 'lsts_curtailed', 'lsts_suspended')\n" +
                "       AND l.goods_or_psv IN ('lcat_gv','lcat_psv')\n" +
                "       AND l.licence_type IN ('ltyp_sn')\n" +
                "       AND ur.role_id IN (25, 26)\n" +
                "       AND u.login_id REGEXP '^[A-Za-z0-9]+$'\n" +
                String.format("LIMIT %s;", users);
    }

    public static String cancelApplications(String application) {
        return "UPDATE OLCS_RDS_OLCSDB.application \n " +
                "SET application.status = 'apsts_cancelled' \n " +
                "WHERE\n" +
                "  licence_id is not null\n" +
                "  AND created_on >= cast(sysdate() AS Date)\n" +
                "  order by last_modified_on\n" +
                String.format("DESC limit %s;", application);
    }

    public static String createTempPasswordTable() {
        return "CREATE TABLE IF NOT EXISTS OLCS_RDS_OLCSDB.temp_user_passwords (\n" +
                "    user_id VARCHAR(255) PRIMARY KEY,\n" +
                "    temp_password VARCHAR(255) NOT NULL,\n" +
                "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                "    INDEX idx_created_at (created_at)\n" +
                ");";
    }

    public static String insertTempPassword() {
        return "INSERT INTO OLCS_RDS_OLCSDB.temp_user_passwords (user_id, temp_password) " +
                "VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE temp_password = VALUES(temp_password), created_at = CURRENT_TIMESTAMP;";
    }

    public static String getUsersWithTempPasswords() {
        return "SELECT u.login_id as Username, p.forename as Forename, u.id as userId,\n" +
                "       cd.email_address as emailAddress, p.family_name as familyName,\n" +
                "       tmp.temp_password as Password\n" +
                "FROM OLCS_RDS_OLCSDB.user u\n" +
                "JOIN OLCS_RDS_OLCSDB.contact_details cd ON u.contact_details_id = cd.id\n" +
                "JOIN OLCS_RDS_OLCSDB.person p ON cd.person_id = p.id\n" +
                "JOIN OLCS_RDS_OLCSDB.temp_user_passwords tmp ON u.login_id = tmp.user_id\n" +
                "WHERE u.account_disabled = 0\n" +
                "ORDER BY tmp.created_at DESC;";
    }

    public static String clearTempPasswords() {
        return "DELETE FROM OLCS_RDS_OLCSDB.temp_user_passwords " +
                "WHERE created_at <= NOW();";
    }

    public static String getTradingNames() {
        return """
        SELECT name 
        FROM trading_name 
        WHERE licence_id IN (
            SELECT id 
            FROM licence 
            WHERE status = 'lsts_valid'
        )
        AND deleted_date IS NULL
        ORDER BY RAND() 
        LIMIT 100
        """;
    }

    public static String getInternalUsers() {
        return """
        SELECT login_id 
        FROM user 
        WHERE id IN (
            SELECT user_id 
            FROM user_role 
            WHERE role_id IN (23, 24, 33)
        )
        AND deleted_date IS NULL 
        AND created_on < 20161201
        ORDER BY RAND()
        LIMIT 100
        """;
    }

    public static String dropTempPasswordTable() {
        return "DROP TABLE IF EXISTS OLCS_RDS_OLCSDB.temp_user_passwords;";
    }
}