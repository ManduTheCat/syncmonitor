package syncMonitor.session;

import syncMonitor.config.wrapper.DbConfig.TiberoConfig;

import java.sql.*;

public class SyncMonitorSessionTibero extends SyncMonitorSession{

    private static Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    // 나중에 클래스 역활 분리되면 넘겨줘야하다



    /*
    세션 변경 방지 및 단일세션 보장을 위한 싱글톤 패턴
    * */
    private static SyncMonitorSessionTibero syncMonitorSessionTibero = null;
    private SyncMonitorSessionTibero(){
    }


    public static SyncMonitorSessionTibero getSyncMonitorSession(TiberoConfig tiberoConfig){
       // if (syncMonitorSessionTibero == null || conn == null) {
            syncMonitorSessionTibero = new SyncMonitorSessionTibero();
       // }
        String DB_URL = "jdbc:tibero:thin:@" + tiberoConfig.getIp() + ":" + tiberoConfig.getPort() + ":" + tiberoConfig.getDbSid();
        String DB_DRV = "com.tmax.tibero.jdbc.TbDriver";
        try {
            System.out.println("Loading Tibero Driver...");
            Class.forName(DB_DRV);
            System.out.println("Driver loaded successfully.");

            System.out.println("Connecting to DB...");
            System.out.println("DB URL: " + DB_URL);
            System.out.println("DB ID: " + tiberoConfig.getId());

            conn = DriverManager.getConnection(DB_URL, tiberoConfig.getId(), tiberoConfig.getPwd());
            System.out.println("Tibero Connect Success");
        } catch (ClassNotFoundException ex) {
            System.out.println("Tibero Driver not found!");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Connection failed: " + ex.getMessage());
            ex.printStackTrace();
        }
        return syncMonitorSessionTibero;
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
        if (conn == null) {
            System.out.println("Connection is null. Ensure the database is accessible.");
        }
        return conn;
    }
}
