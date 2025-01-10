package syncMonitor.topology;

import lombok.Getter;
import syncMonitor.config.wrapper.DbConfig.DbConfig;
import syncMonitor.session.SyncMonitorSession;

@Getter
public class TopologyFactory {
    //토플러지를 만드려면, 소스, 타켓, session 이 필요하다
    private SyncMonitorSession sourceSession;
    private SyncMonitorSession targetSession;
    private DbConfig sourceConfig;
    private DbConfig targetConfig;

    public TopologyFactory(SyncMonitorSession sourceSession, SyncMonitorSession targetSession,
                           DbConfig sourceConfig, DbConfig targetConfig) {
        this.sourceSession = sourceSession;
        this.targetSession = targetSession;
        this.sourceConfig = sourceConfig;
        this.targetConfig = targetConfig;
    }
}
