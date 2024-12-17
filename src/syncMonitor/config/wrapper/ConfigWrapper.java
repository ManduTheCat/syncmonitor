package syncMonitor.config.wrapper;

import syncMonitor.config.wrapper.DbConfig.DatabaseConfig;
import syncMonitor.config.wrapper.DbConfig.OracleConfig;
import syncMonitor.config.wrapper.DbConfig.TiberoConfig;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;

import java.util.List;

public class ConfigWrapper {
    private MonitorConfig monitor;
    private List<TopologyConfig> topology;

    public void setMonitor(MonitorConfig monitorConfig) {
        this.monitor = monitorConfig;
    }

    public MonitorConfig getMonitor() {
        return monitor;
    }

     // 여러 topology 항목

    public List<TopologyConfig> getTopology() {
        return topology;
    }
    public void setTopology(List<TopologyConfig> topology) {
        this.topology = topology;
    }

    @Override
    public String toString() {
        return "ConfigWrapper{monitor='" + monitor + "', topology=" + topology + "}";
    }
}
