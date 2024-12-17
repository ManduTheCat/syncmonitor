package syncMonitor.query.tibero;

import syncMonitor.config.wrapper.DbConfig.TiberoConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TiberoOnly {

    private String targetName;
    private String result;
    private Connection connection = null;



    public TiberoOnly(TiberoConfig tiberoConfig, Connection connection) {
        this.connection = connection;
        this.targetName = tiberoConfig.getUser();
    }

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


    public String doGetPrs_lct() {

        String strSql = "select to_number(tsn) as tsn from " + this.targetName + ".prs_lct";
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

    public String doGetTsn() {

        String strSql = "select current_tsn as tsn from v$database";
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
