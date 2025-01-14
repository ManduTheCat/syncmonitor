package syncMonitor.config.wrapper.DbConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



public class TargetConfig implements DbConfig{
    private String dbType;
    private String ip;
    private int port;
    private String connId;
    private String connPwd;
    private String proSyncUser;
    private String dbSid;

    @Override
    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Override
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getConnId() {
        return connId;
    }

    public void setConnId(String connId) {
        this.connId = connId;
    }

    @Override
    public String getConnPwd() {
        return connPwd;
    }

    public void setConnPwd(String connPwd) {
        this.connPwd = connPwd;
    }

    @Override
    public String getProSyncUser() {
        return proSyncUser;
    }

    public void setProSyncUser(String proSyncUser) {
        this.proSyncUser = proSyncUser;
    }

    @Override
    public String getDbSid() {
        return dbSid;
    }

    public void setDbSid(String dbSid) {
        this.dbSid = dbSid;
    }
}
