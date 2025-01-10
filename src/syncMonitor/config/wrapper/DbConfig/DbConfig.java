package syncMonitor.config.wrapper.DbConfig;



public interface DbConfig  {
    String getDbType();
    String getIp();
    String getPort();
    String getConnId();
    String getConnPwd();
    String getProSyncUser();
    String getDbSid();
}
