package syncMonitor.query.oracle;

import syncMonitor.config.wrapper.DbConfig.OracleConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

        try {
            PreparedStatement pstmt = this.connection.prepareStatement(strSql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rlt = rs.getString(1);
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
