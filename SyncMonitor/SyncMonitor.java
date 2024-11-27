package SyncMonitor;

import java.sql.*;

public class SyncMonitor {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String strSql = null;

    private  String DB_ID = "";
    private  String DB_PWD = "";
    private  String DB_URL = "";
    private  String DB_LINK = "";


    public SyncMonitor(String ip, String port, String id, String pwd) {
        setConnStr(ip, port, id, pwd);
    }

    public void setConnStr(String ip, String port, String id, String pwd) {
        String DB_SID = "tibero7_2";
        DB_ID = id;
        DB_PWD = pwd;
        DB_LINK = "jsh_tto";
        DB_URL = "jdbc:tibero:thin:@" + ip + ":" + port + ":" + DB_SID;
    }
    // 로그로 뺴야할듯
    public void connect() {
        try {
            String DB_DRV = "com.tmax.tibero.jdbc.TbDriver";
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

        strSql = "select a.tsn ||'  /  '||b.tsn ||'   /   '|| (a.tsn-b.tsn) from (select to_number(current_tsn) as tsn from v$database) a , (select to_number(tsn) as tsn from " + target + ".prs_lct@" + DB_LINK + ") b";

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
