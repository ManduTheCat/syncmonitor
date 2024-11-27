package SyncMonitor_backup1127;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SyncMonitor {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String strSql = null;

    String DB_DRV = "com.tmax.tibero.jdbc.TbDriver";
    String DB_IP = new String();
    String DB_PORT = new String();
    String DB_SID = new String();
    String DB_ID = new String();
    String DB_PWD = new String();
    String DB_URL = new String();
    String DB_LINK = new String();

    int grouCnt = 0;

    public SyncMonitor() {
    }

    public void setConnStr(String ip, String port, String id, String pwd) {
        DB_IP = ip;
        DB_PORT = port;
        DB_SID = "tibero7_2";
        DB_ID = id;
        DB_PWD = pwd;
        DB_LINK = "jsh_tto";
        DB_URL = "jdbc:tibero:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;
    }

    public void connect() {
        try {
            Class.forName(DB_DRV);
            conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PWD);

            System.out.println("Tibero Connect Success");
            System.out.println("");
        } catch (Exception ex) {
            System.out.println("fail");
            ex.printStackTrace();
        }

    }

    public String gatherT(String source, String target) {
        String strSql = new String();
        int idx = target.indexOf("_");
        //String Towner	=	source + target.substring(idx+1);
        String Towner = target;

        strSql = "select a.tsn ||'  /  '||b.tsn ||'   /   '|| (a.tsn-b.tsn) from (select to_number(current_tsn) as tsn from v$database) a , (select to_number(tsn) as tsn from " + Towner + ".prs_lct@" + DB_LINK + ") b";

        String resultStr = new String();

        try {
            pstmt = conn.prepareStatement(strSql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                resultStr = rs.getString(1);
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resultStr;
    }

    public String gatherO(String source, String target) {
        String strSql = new String();
        int idx = target.indexOf("_");
        //String Towner	=	source + target.substring(idx+1);
        String Towner = target;

        strSql = "select a.tsn ||'  /  '||b.tsn ||'   /   '|| (a.tsn-b.tsn) from (select to_number(current_scn) as tsn from v$database@" + DB_LINK + ") a , (select to_number(tsn) as tsn from " + Towner + ".prs_lct) b";

        String resultStr = new String();

        try {
            pstmt = conn.prepareStatement(strSql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                resultStr = rs.getString(1);
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resultStr;
    }

    public String getTXtimeT(String source, String target) {

        String rlt = new String();
        int idx = target.indexOf("_");
        //String Towner	=	source + target.substring(idx+1);
        //String Towner	=	"jsh_tto";
        String Towner = target;
        String strSql = "select time from " + Towner + ".prs_lct@" + DB_LINK;

        try {
            pstmt = conn.prepareStatement(strSql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rlt = rs.getString(1);
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rlt;
    }

    public String getTXtimeO(String source, String target) {

        String rlt = new String();
        int idx = target.indexOf("_");
        //String Towner	=	source + target.substring(idx+1);
        //String Towner	=	"jsh_tto";
        String Towner = target;
        String strSql = "select time from " + Towner + ".prs_lct";

        try {
            pstmt = conn.prepareStatement(strSql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rlt = rs.getString(1);
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rlt;
    }


    public void disconnect() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }

    }


}
