package syncMonitor.config.wrapper;

import lombok.Getter;
import lombok.Setter;


public class MonitorConfig {
    private String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
