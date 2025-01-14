package syncMonitor.config.wrapper.DbConfig;

import lombok.Getter;
import lombok.Setter;


public class TopologyConfig {
    private String name;
    private DbConfig source;
    private DbConfig target;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DbConfig getSource() {
        return source;
    }

    public void setSource(DbConfig source) {
        this.source = source;
    }

    public DbConfig getTarget() {
        return target;
    }

    public void setTarget(DbConfig target) {
        this.target = target;
    }
}
