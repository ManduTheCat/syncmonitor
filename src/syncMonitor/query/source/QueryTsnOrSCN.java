package syncMonitor.query.source;

import syncMonitor.config.wrapper.DbConfig.DbConfig;

// 디비타입 가져와서 TSN 또는  SCN 조회하는 쿼리 생성 source 정보
public class QueryTsnOrSCN {

    public static String getOracle(DbConfig config){
        return "select current_scn as scn from v$database";
    }

    public static String getTibero(DbConfig config){
        return "select current_tsn as tsn from v$database";
    }

}
