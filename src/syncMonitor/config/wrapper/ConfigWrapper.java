package syncMonitor.config.wrapper;

import syncMonitor.config.wrapper.DbConfig.DatabaseConfig;
import syncMonitor.config.wrapper.DbConfig.OracleConfig;
import syncMonitor.config.wrapper.DbConfig.TiberoConfig;

public class ConfigWrapper {
    private MonitorConfig monitor;
    private DatabaseConfig database;


    public void setMonitor(MonitorConfig monitorConfig) {
        this.monitor = monitorConfig;
    }

    public MonitorConfig getMonitor() {
        return monitor;
    }

    public DatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }
    public TiberoConfig getTiberoConfig() {
        return getDatabase().getTibero();
    }

    public OracleConfig getOracleConfig() {
        return getDatabase().getOracle();
    }
}
