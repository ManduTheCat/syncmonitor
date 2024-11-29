package syncMonitor.session;

import java.sql.Connection;

public abstract class SyncMonitorSession {
    public static SyncMonitorSession getSyncMonitorSession(String ip, String port, String id, String pwd, String DB_SID) {
        return null;
    }

    public void disconnect() {
    }

    public Connection getConn() {

        return null;
    }

}
