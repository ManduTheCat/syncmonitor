package syncMonitor.config.wrapper;

import lombok.Getter;
import lombok.Setter;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;

import java.util.List;

@Setter
@Getter
public class ConfigWrapper {

    private MonitorConfig monitor;
    private List<TopologyConfig> topology;

    @Override
    public String toString() {
        return "ConfigWrapper{monitor='" + monitor + "', topology=" + topology + "}";
    }
}
