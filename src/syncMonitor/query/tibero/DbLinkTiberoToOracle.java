package syncMonitor.query.tibero;

import syncMonitor.query.Dblink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbLinkTiberoToOracle implements Dblink {
    private String sourceName;
    private String targetName;
    private String result;
    private String DB_LINK;
    private Connection conection = null;


    public DbLinkTiberoToOracle(String dblinkName, String sourceNawme, String targetName, Connection connection) {
        this.DB_LINK = dblinkName;
        this.conection = connection;
        this.sourceName = sourceNawme;
        this.targetName = targetName;
    }

    //a 는 티베로 b 는 오라클
    @Override
    public String doGetTsn() {
        String strSql = "select a.tsn ||'  /  '||b.tsn ||'  /  '|| (a.tsn-b.tsn) from (select to_number(current_tsn) as tsn from v$database) a , (select to_number(tsn) as tsn from " + targetName + ".prs_lct@" + DB_LINK + ") b";

        String resultStr = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = this.conection.prepareStatement(strSql);
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
        /*todo
         * 결과 검사 하는 로직 필요
         * */
        return resultStr;
    }

    @Override
    public String doGetTime() {
        String rlt = "";
        String strSql = "select time from " + targetName + ".prs_lct@" + DB_LINK;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = this.conection.prepareStatement(strSql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rlt = rs.getString(1);
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
}
