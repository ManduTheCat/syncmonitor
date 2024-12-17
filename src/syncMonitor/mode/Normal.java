package syncMonitor.mode;

import syncMonitor.config.wrapper.DbConfig.OracleConfig;
import syncMonitor.config.wrapper.DbConfig.TiberoConfig;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;
import syncMonitor.view.ViewOracleTibero;
import syncMonitor.session.SyncMonitorSessionOracle;
import syncMonitor.session.SyncMonitorSessionTibero;

public class Normal {
    private ViewOracleTibero viewOracleTibero;
    public Normal(TopologyConfig topologyConfig) {
        OracleConfig oracleConfig = (OracleConfig) topologyConfig.getOracleConfig();
        TiberoConfig tiberoConfig = (TiberoConfig) topologyConfig.getTiberoConfig();
        SyncMonitorSessionTibero sessionTibero = SyncMonitorSessionTibero.getSyncMonitorSession(tiberoConfig);
        SyncMonitorSessionOracle sessionOracle = SyncMonitorSessionOracle.getSyncMonitorSession(oracleConfig);
        this.viewOracleTibero = new ViewOracleTibero(sessionOracle, oracleConfig, sessionTibero, tiberoConfig);
    }

    public ViewOracleTibero getViewOracleTibero() {
        return viewOracleTibero;
    }


}
