package syncMonitor.config.wrapper.DbConfig;



public interface DbConfig  {
    String getDbType();
    String getIp();
    int getPort();
    String getConnId();
    String getConnPwd();
    String getProSyncUser();
    String getDbSid();
}
