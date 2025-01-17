package syncMonitor.config.wrapper.DbConfig;



public class TopologyConfig {
    public String name;
    public SourceConfig source;
    public TargetConfig target;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DbConfig getSource() {
        return source;
    }

    public void setSource(SourceConfig source) {
        this.source = source;
    }

    public DbConfig getTarget() {
        return target;
    }

    public void setTarget(TargetConfig target) {
        this.target = target;
    }
}
