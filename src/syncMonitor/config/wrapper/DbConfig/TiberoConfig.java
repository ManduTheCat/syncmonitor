package syncMonitor.config.wrapper.DbConfig;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TiberoConfig implements DbConfig {
    private String ip;
    private String port;
    private String id;
    private String pwd; // YAML에서 매핑되는 필드
    private String dbLink;
    private String user1;
    private String user2;
    private String dbSid;

    // Getter and Setter for all fields

    @Override
    public String getDbLink() {
        return dbLink;
    }

    @Override
    public String getUser() {
        return user1;
    }
    @Override
    public String getDblinkUser()  {
        return dbLink;
    }


    @Override
    public String toString() {
        return "TiberoConfig {" +
                "\nip="+ ip +
                "\nport=" + port+
                "\nid='" + id+
                "\npwd='" + pwd+
                "\ndbLink='" + dbLink+
                "\nuser1='" + user1+
                "\nuser2='" + user2+
                "\ndbSid='" + dbSid+
                "\n}";
    }
}
