package syncMonitor.topology;

import syncMonitor.config.wrapper.DbConfig.DbConfig;
import syncMonitor.session.SyncMonitorSession;

public class Topology {
    SyncMonitorSession sourceSession;
    SyncMonitorSession targetSession;
    DbConfig SourceConfig;
    DbConfig TargetConfig;
    String sourceQuery;
    String targetQuery;


    public String getSourceTsnOrScn(){
        return null;
    }

}
