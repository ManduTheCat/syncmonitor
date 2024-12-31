package syncMonitor.view;

import syncMonitor.config.wrapper.DbConfig.OracleConfig;
import syncMonitor.config.wrapper.DbConfig.TiberoConfig;
import syncMonitor.query.oracle.OracleOnly;
import syncMonitor.query.tibero.TiberoOnly;
import syncMonitor.session.SyncMonitorSessionOracle;
import syncMonitor.session.SyncMonitorSessionTibero;

public class TopologyDto {
    private final SyncMonitorSessionOracle oracleSession;
    private final OracleConfig oracleConfig;
    private final SyncMonitorSessionTibero tiberoSession;
    private final TiberoConfig tiberoConfig;

    private final TiberoOnly tiberoOnly;
    private final OracleOnly oracleOnly;

    public TopologyDto(SyncMonitorSessionOracle oracleSession, OracleConfig oracleConfig,
                       SyncMonitorSessionTibero tiberoSession, TiberoConfig tiberoConfig) {
        this.oracleSession = oracleSession;
        this.oracleConfig = oracleConfig;
        this.tiberoSession = tiberoSession;
        this.tiberoConfig = tiberoConfig;

        // TiberoOnly 및 OracleOnly 객체를 생성
        this.tiberoOnly = new TiberoOnly(tiberoConfig, tiberoSession.getConn());
        this.oracleOnly = new OracleOnly(oracleConfig, oracleSession.getConn());
    }

    public OracleOnly getOracleOnly() {
        return oracleOnly;
    }

    public TiberoOnly getTiberoOnly() {
        return tiberoOnly;
    }

    public String getName() {
        return oracleConfig.getDbSid() + " & " + tiberoConfig.getDbSid();
    }

    public SyncMonitorSessionTibero getTiberoSession() {
        return tiberoSession;
    }

    public SyncMonitorSessionOracle getOracleSession() {
        return oracleSession;
    }
}
