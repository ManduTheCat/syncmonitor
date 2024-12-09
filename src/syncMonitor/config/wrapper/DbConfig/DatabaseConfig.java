package syncMonitor.config.wrapper.DbConfig;

public class DatabaseConfig {
    private TiberoConfig tibero;
    private OracleConfig oracle;

    public TiberoConfig getTibero() {
        return tibero;
    }

    public void setTibero(TiberoConfig tibero) {
        this.tibero = tibero;
    }

    public OracleConfig getOracle() {
        return oracle;
    }

    public void setOracle(OracleConfig oracle) {
        this.oracle = oracle;
    }
}
