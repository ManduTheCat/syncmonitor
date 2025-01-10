package syncMonitor.config.wrapper.DbConfig;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TargetConfig {
    // Getters and Setters
    private String db;
    private String ip;
    private int port;
    private String id;
    private String pwd;
    private String user;
    private String dbSid;


    @Override
    public String toString() {
        return "TargetConfig{db='" + db + "', ip='" + ip + "', port=" + port +
                ", id='" + id + "', pwd='" + pwd + "', user='" + user + "', dbSid='" + dbSid + "'}";
    }
}
