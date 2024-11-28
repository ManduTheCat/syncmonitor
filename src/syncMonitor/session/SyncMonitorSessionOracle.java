package syncMonitor.session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SyncMonitorSessionOracle {

    private static Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private static String DB_DRV = "oracle.jdbc.driver.OracleDriver";
    private static String baseURL = "jdbc:oracle:thin:@";

    /*
    세션 변경 방지 및 단일세션 보장을 위한 싱글톤 패턴
    * */
    private static SyncMonitorSessionOracle syncMonitorSessionOracle = null;
    private SyncMonitorSessionOracle(){
    }
    public static SyncMonitorSessionOracle getSyncMonitorSession(String ip, String port, String id, String pwd, String DB_SID){
        if(syncMonitorSessionOracle != null) return syncMonitorSessionOracle;
        syncMonitorSessionOracle = new SyncMonitorSessionOracle();

        String DB_URL = baseURL + ip + ":" + port + ":" + DB_SID;
        try {
            Class.forName(DB_DRV);
            conn = DriverManager.getConnection(DB_URL, id, pwd);
            System.out.println("Oracle Connect Success"); // todo log 파일 별도로 추출
            System.out.println();
        } catch (Exception ex) {
            System.out.println("fail");
            ex.printStackTrace();
        }
        return syncMonitorSessionOracle;
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
