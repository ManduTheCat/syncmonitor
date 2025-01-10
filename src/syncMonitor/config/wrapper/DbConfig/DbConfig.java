package syncMonitor.config.wrapper.DbConfig;

import java.io.IOError;


public interface DbConfig  {
    String getIp();
    String getId();
    String getDbLink();
    String getDbSid();
    String getPwd();
    String getPort();
    String getUser();
    // 없을수도 있음
    String getDblinkUser() throws Exception;
}
