package syncMonitor.config.wrapper;


import syncMonitor.config.wrapper.DbConfig.TopologyConfig;

import java.util.List;


public class ConfigWrapper {


    private MonitorConfig monitor;

    private List<TopologyConfig> topology;

    public MonitorConfig getMonitor() {
        return monitor;
    }

    public List<TopologyConfig> getTopology() {
        return topology;
    }

    public void setMonitor(MonitorConfig monitor) {
        this.monitor = monitor;
    }

    public void setTopology(List<TopologyConfig> topology) {
        this.topology = topology;
    }
}
