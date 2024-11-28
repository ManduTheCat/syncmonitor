package backupCode;


import java.sql.*;
import java.text.*;

public class SyncMonitorBak {
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

    public void SyncMonitor() {
    }

    public void setConnStr(String ip, String port, String id, String pwd) {
        DB_IP = ip;
        DB_PORT = port;
        DB_SID = "tibero7_2";
        DB_ID = id;
        DB_PWD = pwd;
        DB_URL = "jdbc:tibero:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;
        DB_LINK = "jsh_tto";
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


    public String gatherT(String source, String target) {
        String strSql = new String();
        int idx = target.indexOf("_");
        //String Towner	=	source + target.substring(idx+1);
        String Towner = target;

        strSql = "select a.tsn ||'  /  '||b.tsn ||'   /   '|| (a.tsn-b.tsn) from (select to_number(current_tsn) as tsn from v$database) a , (select to_number(tsn) as tsn from " + Towner + ".prs_lct@" + target + ") b";
//	strSql	=	"select a.tsn ||'  /  '||b.tsn ||'   /   '|| (a.tsn-b.tsn) from (select to_number(current_tsn) as tsn from v$database@"+source+") a , (select to_number(tsn) as tsn from "+Towner+".prs_lct@"+target+") b";

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
        String strSql = "select time from " + Towner + ".prs_lct@" + target;

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

    public static void main_test(String[] args) {
        SyncMonitorBak sMon = new SyncMonitorBak();
        sMon.setConnStr("192.168.1.188", "4628", "tibero", "tmax"); // 수정 필요
        sMon.connect();
        try {
            for (int i = 0; i < 100000000; i++) {
                java.util.Date d = new java.util.Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

                System.out.println("=============================");
                System.out.println("Prosync Monitor for 4");
                System.out.println("=============================");
                System.out.println("====================================================================================================");
                System.out.println("Current Time: " + sdf.format(d).toString());
                System.out.println(" ");
                System.out.println("====================================================================================================");
                System.out.println("Sync way" + " SOURCE TSN   :   TARGET TSN    : TSN_GAP ");
                System.out.println("====================================================================================================");
                //System.out.println("U3->AH" +"  :  "+ sMon.gather("p_u3","p_ah"));// 수정 필요
                System.out.println("T2O" + "  :  " + sMon.gatherT("jsh_tto", "jsh_tto"));// 수정 필요
                System.out.println("O2T" + "  :  " + sMon.gatherO("jsh_ott", "jsh_ott"));// 수정 필요
                //System.out.println("AH->U3" +"  :  "+ sMon.gather("p_ah","p_u3"));// 수정 필요
                System.out.println("====================================================================================================");
                System.out.println(" ");
                System.out.println("CURENT_TIME         :       LAST COMMIT TIME");
                System.out.println("--<T20>---------------------------------------------------------------------------------- ");
                System.out.println(sdf.format(d).toString() + " / " + sMon.getTXtimeT("jsh_tto", "jsh_tto"));// 수정 필요
                //System.out.println(sdf.format(d).toString() +" / "+ sMon.getTXtime("tlink","tlink"));// 수정 필요
                System.out.println("--<O2T>---------------------------------------------------------------------------------- ");
                System.out.println(sdf.format(d).toString() + " / " + sMon.getTXtimeO("jsh_ott", "jsh_ott"));// 수정 필요
                //System.out.println(sdf.format(d).toString() +" / "+ sMon.getTXtime("p_ah","p_u3"));// 수정 필요
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        sMon.disconnect();

    }

}

