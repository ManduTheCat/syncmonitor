package syncMonitor.query.oracle;

import syncMonitor.config.wrapper.DbConfig.OracleConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleOnly {
    private String username;
    private String result;
    private Connection connection = null;



    public OracleOnly(OracleConfig oracleConfig, Connection connection) {
        this.connection = connection;
        this.username = oracleConfig.getUser();
    }

    public String doGetTime() {
        String rlt = "";
        String strSql = "select time from " + this.username + ".prs_lct";
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


    public String doGetPrsLct() {

        String getOracelLct = "select to_number(tsn) as tsn from " + this.username + ".prs_lct b";
        String resultStr = "";
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(getOracelLct);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                resultStr = rs.getString(1);
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resultStr;
    }
    public String doGetSCN(){
        String getOracelLct = "select current_scn as tsn from v$database";
        String resultStr = "";
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(getOracelLct);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                resultStr = rs.getString(1);
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resultStr;

    }
}
