package syncMonitor.topology;

import lombok.Getter;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;
import syncMonitor.query.target.QueryPrsLct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 설정 할때(최초생성) 빼고는 변경되서는 안되는 정보들

@Getter
public class Topology {
    private final DbDto source;
    private final DbDto target;
    private final String lastCommitQuery;

    //팩토리에서만 생성할수 있다 접근 패키지로 관리
    Topology(DbDto source, DbDto target, TopologyConfig topologyConfig) {
        this.source = source;
        this.target = target;
        this.lastCommitQuery = QueryPrsLct.getLastCommitTime(topologyConfig.getTarget());
    }

    public Integer runSourceChangeNumberQuery(){
        Integer response = -1;

        //세션을 해제 하지 않는지 확인 해야한다
        try{
            Connection conn = this.source.getSession().getConn();
            PreparedStatement pstmt = conn.prepareStatement(source.getQuery());
            ResultSet rSet = pstmt.executeQuery();
            while (rSet.next()) {
                response = rSet.getInt(1);
                Thread.sleep(100);
            }
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public Integer runTargetChangeNumberQuery(){

        Integer res = -1;
        //세션을 해제 하지 않는지 확인 해야한다
        try{
            System.out.println(this.target.getSession());
            Connection conn = this.target.getSession().getConn();
            PreparedStatement pstmt = conn.prepareStatement(target.getQuery());
            ResultSet rSet = pstmt.executeQuery();
            while (rSet.next()) {
                res = rSet.getInt(1);
                Thread.sleep(100);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }
    public String runTargetTimeQuery(){
        String response = "";
        //세션을 해제 하지 않는지 확인 해야한다
        try(Connection conn = this.target.getSession().getConn()){
            PreparedStatement pstmt = conn.prepareStatement(this.lastCommitQuery);
            ResultSet rSet = pstmt.executeQuery();
            while (rSet.next()) {
                response = rSet.getString(1);
                Thread.sleep(100);
            }
            return response;
        }catch (SQLException e){
            e.printStackTrace();
            return "getCommitTime Fail";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "getSQL Fail";
        }

    }
}
