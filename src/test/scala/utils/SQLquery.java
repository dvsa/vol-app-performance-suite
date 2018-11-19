package utils;

public class SQLquery {

    public static String getUsersSql(String users) {
        return "SELECT\n" +
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
}