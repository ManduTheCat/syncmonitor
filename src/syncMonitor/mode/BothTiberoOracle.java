package syncMonitor.mode;

import syncMonitor.config.wrapper.DbConfig.OracleConfig;
import syncMonitor.config.wrapper.DbConfig.TiberoConfig;
import view.ViewOracleTibero;
import syncMonitor.session.SyncMonitorSessionOracle;
import syncMonitor.session.SyncMonitorSessionTibero;

public class BothTiberoOracle {
    private ViewOracleTibero viewOracleTibero;
    public BothTiberoOracle (OracleConfig oracleConfig, TiberoConfig tiberoConfig) {
        SyncMonitorSessionTibero sessionTibero = SyncMonitorSessionTibero.getSyncMonitorSession(tiberoConfig);
        SyncMonitorSessionOracle sessionOracle = SyncMonitorSessionOracle.getSyncMonitorSession(oracleConfig);
        this.viewOracleTibero = new ViewOracleTibero(sessionOracle, oracleConfig, sessionTibero, tiberoConfig);
    }

    public ViewOracleTibero getViewOracleTibero() {
        return viewOracleTibero;
    }


}
