package syncMonitor.query.oracle;

import syncMonitor.query.Dblink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbLinkOracleToTibero implements Dblink {

    private String sourceName;
    private String targetName;
    private String result;
    private String DB_LINK;
    private Connection connection = null;



    public DbLinkOracleToTibero(String oracleDbLinkName, String sourceName, String targetName , Connection connection) {
        this.DB_LINK = oracleDbLinkName;
        this.connection = connection;
        this.sourceName = sourceName;
        this.targetName = targetName;
    }

    @Override
    public String doGetTime() {
        String rlt = "";
        String strSql = "select time from " + this.targetName + ".prs_lct";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = this.connection.prepareStatement(strSql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rlt = rs.getString(1);
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("[ERROR] Failed to close resources: " + e.getMessage());
            }
        }


        return rlt;
    }

    @Override
    public String doGetTsn() {

        String strSql = "select a.tsn ||'  /  '||b.tsn ||'  /  '|| (a.tsn-b.tsn) from (select to_number(current_scn) as tsn from v$database@" + DB_LINK + ") a , (select to_number(tsn) as tsn from " + this.targetName + ".prs_lct) b";
        String resultStr = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = this.connection.prepareStatement(strSql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                resultStr = rs.getString(1);
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("[ERROR] Failed to close resources: " + e.getMessage());
            }
        }

        return resultStr;
    }


}
