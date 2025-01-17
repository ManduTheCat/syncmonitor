package syncMonitor.session;

import lombok.Getter;
import syncMonitor.config.wrapper.DbConfig.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//팩토리에서 이객체를 생성함으로서 새로운 세션을 만들수 있다
public class SyncMonitorSessionOracle implements SyncMonitorSession {
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private final Connection conn;

    public SyncMonitorSessionOracle(DbConfig oracleConfig){
        this.conn = this.genSyncMonitorSessionConnection(oracleConfig);
    }


    @Override
    public void disconnect() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception ignored) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }

    }

    @Override
    public Connection getConn() {
        if(conn != null){
            return conn;
        }
        System.out.println("Oracle Connection is null. Ensure the database is accessible");
        return null;
    }

    @Override
    public String getBaseURL() {
        String baseURL = "jdbc:oracle:thin:@";
        return baseURL;
    }

    @Override
    public String getDbDRV() {
        String dbDRV = "oracle.jdbc.OracleDriver";
        return dbDRV;
    }
}
