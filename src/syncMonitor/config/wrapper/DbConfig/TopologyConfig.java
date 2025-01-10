package syncMonitor.config.wrapper.DbConfig;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopologyConfig {
    private String name;
    private SourceConfig source;
    private TargetConfig target;

}
