package syncMonitor.config.wrapper.DbConfig;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OracleConfig implements DbConfig  {
    private String ip;
    private String port;
    private String id;
    private String pwd;
    private String dbLink; // YAML의 dbLink
    private String user1;
    private String user2;
    private String dbSid; // YAML의 dbSid


    @Override
    public String getUser() {
        return user1;
    }

    @Override
    public String getDblinkUser() throws Exception {
        return dbLink;
    }


    @Override
    public String toString() {
        return "OracleConfig {" +
                "\nip='" + ip+
                "\nport=" + port+
                "\nid='" + id +
                "\npwd='" + pwd +
                "\ndbLink='" + dbLink+
                "\nuser1='" + user1 +
                "\nuser2='" + user2+
                "\ndbSid='" + dbSid+
                "\n}";
    }

}
