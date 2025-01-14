package syncMonitor.session;

import syncMonitor.config.wrapper.DbConfig.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface SyncMonitorSession {
    String getDbDRV();
    String getBaseURL();

    //모든 세션의 공통된 부분임으로 default
    default Connection genSyncMonitorSessionConnection(DbConfig tiberoConfig){
        Connection conn = null;
        String DB_DRV = getDbDRV();
        String baseURL = getBaseURL();
        String DB_URL =  baseURL+ tiberoConfig.getIp() + ":" + tiberoConfig.getPort() + ":" + tiberoConfig.getDbSid();
        try {
            System.out.println("Loading Driver...");
            Class.forName(DB_DRV);
            System.out.println("Driver loaded successfully.");
            System.out.println("Connecting to DB...");
            conn = DriverManager.getConnection(DB_URL, tiberoConfig.getConnId(), tiberoConfig.getConnPwd());
            System.out.println("Connect Success");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Connection failed: " + ex.getMessage());
            ex.printStackTrace();
        }
        return conn;
    }
    Connection getConn();
    void disconnect();
}
