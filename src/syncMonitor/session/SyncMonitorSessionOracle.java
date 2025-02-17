package syncMonitor.session;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import syncMonitor.config.wrapper.DbConfig.DbConfig;

import java.sql.Connection;

//팩토리에서 이객체를 생성함으로서 새로운 세션을 만들수 있다
@Slf4j
public class SyncMonitorSessionOracle implements SyncMonitorSession {
    private final Connection conn;
    @Getter
    private final String baseURL = "jdbc:oracle:thin:@";
    @Getter
    private final String  dbDRV = "oracle.jdbc.OracleDriver";
    public SyncMonitorSessionOracle(DbConfig oracleConfig){
        this.conn = this.genSyncMonitorSessionConnection(oracleConfig);
    }


    @Override
    public void disconnect() {
        try {
            if (conn != null) conn.close();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

    }

    @Override
    public Connection getConn() {
        if(conn != null){
            return conn;
        }
       log.error("Oracle Connection is null. Ensure the database is accessible");
        log.error(new NullPointerException().getMessage());
        return null;
    }

}
