package syncMonitor.session;

import java.sql.*;

public class SyncMonitorSession  {

    private static Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    // 나중에 클래스 역활 분리되면 넘겨줘야하다


    private static String DB_DRV = "com.tmax.tibero.jdbc.TbDriver";

    /*
    세션 변경 방지 및 단일세션 보장을 위한 싱글톤 패턴
    * */
    private static SyncMonitorSession syncMonitorSession = null;
    private SyncMonitorSession(){
    }
    public static SyncMonitorSession getSyncMonitorSession(String ip, String port, String id, String pwd, String DB_SID){
        if(syncMonitorSession != null) return syncMonitorSession;
        syncMonitorSession = new SyncMonitorSession();
        String DB_URL = "jdbc:tibero:thin:@" + ip + ":" + port + ":" + DB_SID;
        try {
            Class.forName(DB_DRV);
            conn = DriverManager.getConnection(DB_URL, id, pwd);
            System.out.println("Tibero Connect Success"); // todo log 파일 별도로 추출
            System.out.println();
        } catch (Exception ex) {
            System.out.println("fail");
            ex.printStackTrace();
        }
        return syncMonitorSession;
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
            } catch (Exception ignored) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }

    }

    public Connection getConn() {
        if(conn != null){
            return conn;
        }
        System.out.println("conn is null");
        return null;
    }
}
