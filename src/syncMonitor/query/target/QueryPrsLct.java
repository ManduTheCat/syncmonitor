package syncMonitor.query.target;

import syncMonitor.config.wrapper.DbConfig.DbConfig;

// 프로싱크 아이디 받아서 prsLCT 조회하는 쿼리 반납 해당 토플로지 타겟의 정보를 가져오는 쿼리
public class QueryPrsLct {

    public static String getOracle(DbConfig config){
        // 오라클 정보가 온다
        return "select TSN from " + config.getProSyncUser() + ".prs_lct";
    }
    public static String getTibero(DbConfig config){
        return "select TSN from " + config.getProSyncUser() + ".prs_lct";
    }
    public static String getLastCommitTime(DbConfig config){
        return "select time from " + config.getProSyncUser() + ".prs_lct";
    }

}
