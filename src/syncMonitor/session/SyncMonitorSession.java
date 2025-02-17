package syncMonitor.session;


import syncMonitor.config.wrapper.DbConfig.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface SyncMonitorSession {
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SyncMonitorSession.class);
    String getDbDRV();
    String getBaseURL();

    //모든 세션의 공통된 부분임 으로 default 생성 구현체에서 사용가능
    // 모든 새션 이 생성될때 최초 사용 하는 함수
    default Connection genSyncMonitorSessionConnection(DbConfig config){
        Connection conn = null;
        String DB_DRV = getDbDRV();
        String baseURL = getBaseURL();
        String DB_URL =  baseURL+ config.getIp() + ":" + config.getPort() + ":" + config.getDbSid();
        try {
            log.info("Loading Driver...");
            Class.forName(DB_DRV);
            log.info("Driver loaded successfully." + ": "+DB_URL);
            log.info("Connecting to DB...");
            conn = DriverManager.getConnection(DB_URL, config.getConnId(), config.getConnPwd());
            log.info("Connect Success");
            log.info("Start test...");

            // dual 조회 테스트
            ResultSet rs = conn.prepareStatement("select * from dual").executeQuery();
            while(rs.next()){
                log.info( rs.getString(1).equals("X") ? "success!!":"fail");

            }
            rs.close();

        } catch (ClassNotFoundException e) {
            log.error(e.fillInStackTrace().toString());
            throw new RuntimeException(e);
        } catch (SQLException e) {
            log.error(e.fillInStackTrace().toString());
            throw new RuntimeException(e);
        }
        return conn;
    }
    Connection getConn();
    void disconnect();
}
