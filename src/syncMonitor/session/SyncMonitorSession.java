package syncMonitor.session;

import java.sql.Connection;

public abstract class SyncMonitorSession {

    public void disconnect() {
    }

    public Connection getConn() {

        return null;
    }

}
