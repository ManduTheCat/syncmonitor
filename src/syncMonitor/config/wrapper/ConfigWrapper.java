package syncMonitor.config.wrapper;

import lombok.Getter;
import lombok.Setter;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;

import java.util.List;

public class ConfigWrapper {

    public MonitorConfig monitor;
    public List<TopologyConfig> topology;

    @Override
    public String toString() {
        return "ConfigWrapper{monitor='" + monitor + "', topology=" + topology + "}";
    }

    public MonitorConfig getMonitor() {
        return monitor;
    }

    public List<TopologyConfig> getTopology() {
        return topology;
    }
}
