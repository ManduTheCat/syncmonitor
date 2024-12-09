package syncMonitor.query.tibero;

import syncMonitor.config.wrapper.DbConfig.TiberoConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TiberoOnly {

    private String targetName;
    private String result;
    private Connection connection = null;



    public TiberoOnly(TiberoConfig tiberoConfig, Connection connection) {
        this.connection = connection;
        this.targetName = tiberoConfig.getUser2();
    }

    public String doGetTime() {
        String rlt = "";
        String strSql = "select time from " + this.targetName + ".prs_lct";

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


    public String doGetPrs_lct() {

        String strSql = "select to_number(tsn) as tsn from " + this.targetName + ".prs_lct";
        String resultStr = "";
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(strSql);
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

    public String doGetTsn() {

        String strSql = "select current_tsn as tsn from v$database";
        String resultStr = "";
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(strSql);
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
