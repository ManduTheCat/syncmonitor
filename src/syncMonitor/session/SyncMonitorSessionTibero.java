package syncMonitor.session;

import lombok.Getter;
import syncMonitor.config.wrapper.DbConfig.DbConfig;

import java.sql.Connection;



//팩토리에서 이객체를 생성함으로서 새로운 세션을 만들수 있다
public class SyncMonitorSessionTibero implements SyncMonitorSession {
    private final Connection conn; // 해당 인스턴스의 conn 은 바뀌지 않음 보장

    @Getter
    private final String DbDRV = "com.tmax.tibero.jdbc.TbDriver";
    @Getter
    private final  String baseURL ="jdbc:tibero:thin:@";

    public SyncMonitorSessionTibero(DbConfig tiberoConfig) {
        this.conn = this.genSyncMonitorSessionConnection(tiberoConfig);
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
        if (conn == null) {
           log.error("Connection is null. Ensure the database is accessible.");
           log.error(new NullPointerException().getMessage());
        }
        return conn;
    }

}
