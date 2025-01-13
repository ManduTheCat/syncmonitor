package syncMonitor.config.wrapper.DbConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TargetConfig implements DbConfig{
    private String dbType;
    private String ip;
    private int port;
    private String connId;
    private String connPwd;
    private String proSyncUser;
    private String dbSid;


}
