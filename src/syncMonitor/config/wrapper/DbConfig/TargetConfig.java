package syncMonitor.config.wrapper.DbConfig;

public class TargetConfig {
    private String db;
    private String ip;
    private int port;
    private String id;
    private String pwd;
    private String user;
    private String dbSid;

    // Getters and Setters
    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }



    public void setIp(String ip) {
        this.ip = ip;
    }



    public void setPort(int port) {
        this.port = port;
    }





    public void setId(String id) {
        this.id = id;
    }



    public void setPwd(String pwd) {
        this.pwd = pwd;
    }





    public void setUser(String user) {
        this.user = user;
    }



    public void setDbSid(String dbSid) {
        this.dbSid = dbSid;
    }

    @Override
    public String toString() {
        return "TargetConfig{db='" + db + "', ip='" + ip + "', port=" + port +
                ", id='" + id + "', pwd='" + pwd + "', user='" + user + "', dbSid='" + dbSid + "'}";
    }
}
