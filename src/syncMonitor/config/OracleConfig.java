package syncMonitor.config;

public class OracleConfig implements DbConfig {
    private String ip;
    private String port;
    private String id;
    private String pwd;
    private String dbLink; // YAML의 dbLink
    private String user1;
    private String user2;
    private String dbSid; // YAML의 dbSid

    // Getter and Setter for all fields
    @Override
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String getDbLink() {
        return dbLink;
    }

    public void setDbLink(String dbLink) {
        this.dbLink = dbLink;
    }

    @Override
    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    @Override
    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    @Override
    public String getDbSid() {
        return dbSid;
    }

    public void setDbSid(String dbSid) {
        this.dbSid = dbSid;
    }

    @Override
    public String toString() {
        return "OracleConfig {" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", dbLink='" + dbLink + '\'' +
                ", user1='" + user1 + '\'' +
                ", user2='" + user2 + '\'' +
                ", dbSid='" + dbSid + '\'' +
                '}';
    }

}
