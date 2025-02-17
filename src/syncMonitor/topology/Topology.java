package syncMonitor.topology;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;
import syncMonitor.query.target.QueryPrsLct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 설정 할때(최초생성) 빼고는 변경되서는 안되는 정보들

@Slf4j
@Getter
public class Topology {
    private final DbDto source;
    private final DbDto target;
    private final String lastCommitQuery;
    private final String topologyName;
    private final String proSyncUser;


    //팩토리에서만 생성할수 있다 접근 패키지로 관리
    Topology(String topologyName, String proSyncUser ,
             DbDto source, DbDto target, TopologyConfig topologyConfig) {
        this.source = source;
        this.target = target;
        this.lastCommitQuery = QueryPrsLct.getLastCommitTime(topologyConfig.getTarget());
        this.topologyName = topologyName;
        this.proSyncUser = proSyncUser;
    }
    public void disconnectAll(){
        try{
            this.source.getSession().getConn().close();
            this.target.getSession().getConn().close();

        }catch (SQLException e){
            log.error(e.fillInStackTrace().toString());
        }
    }

    public String runSourceChangeNumberQuery(){
        String response = null;

        //세션을 해제 하지 않는지 확인 해야한다
        try{
            Connection conn = this.source.getSession().getConn();
            PreparedStatement pstmt = conn.prepareStatement(source.getQuery());
            ResultSet rSet = pstmt.executeQuery();
            while (rSet.next()) {
                response = rSet.getString(1);
            }
            Thread.sleep(100);
            pstmt.close();
            return response;
        }catch (Exception e){
            log.error(e.fillInStackTrace().toString());
            return null;
        }
    }

    public String runTargetChangeNumberQuery(){

        String res = null;
        //세션을 해제 하지 않는지 확인 해야한다
        try{
            Connection conn = this.target.getSession().getConn();
            PreparedStatement pstmt = conn.prepareStatement(target.getQuery());
            ResultSet rSet = pstmt.executeQuery();
            while (rSet.next()) {
                res = rSet.getString(1);
            }
            Thread.sleep(100);
            pstmt.close();
            return res;

        } catch (Exception e) {
           log.error(e.fillInStackTrace().toString());
            return null;
        }

    }
    public String runTargetTimeQuery(){
        String response = "";
        try{
            Connection conn = this.target.getSession().getConn();
            PreparedStatement pstmt = conn.prepareStatement(this.lastCommitQuery);
            ResultSet rSet = pstmt.executeQuery();
            while (rSet.next()) {
                response = rSet.getString(1);
            }
            Thread.sleep(100);
            pstmt.close();
            return response;
        }catch (SQLException e){
            log.error(e.fillInStackTrace().toString());
            return "getCommitTime Fail";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

