package syncMonitor.config.wrapper.DbConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TopologyConfig {
    private String name;
    private String mode;

    private TargetConfig target1; // TiberoConfig 또는 OracleConfig
    private TargetConfig target2; // TiberoConfig 또는 OracleConfig


    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public TargetConfig getTarget1() {
        return target1;
    }

    public DbConfig getTiberoConfig() {
        HashMap<String, Object> map = DbConfigConverter.ConverObjectToMap(target1);
        assert map != null;
        return DbConfigConverter.convertToDbConfig(map);
    }

    public DbConfig getOracleConfig() {
        HashMap<String, Object> map = DbConfigConverter.ConverObjectToMap(target2);
        assert map != null;
        return DbConfigConverter.convertToDbConfig(map);
    }

    public void setTarget1(TargetConfig target1) {
        this.target1 = target1;
    }

    public TargetConfig getTarget2() {
        return target2;
    }

    public void setTarget2(TargetConfig target2) {
        this.target2 = target2;

    }

    @Override
    public String toString() {
        return "TopologyConfig{name='" + name + "', target1=" + target1 + ", target2=" + target2 + "}";
    }
}
