package syncMonitor.topology;


import syncMonitor.config.wrapper.DbConfig.TopologyConfig;
import syncMonitor.query.source.QueryTsnOrSCN;
import syncMonitor.query.target.QueryPrsLct;
import syncMonitor.session.SyncMonitorSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 이미 연결된 맵 객체를 전달 받는다.
// 나중에 세션은 주입 받는 걸로 수정하면 좋을듯
public class TopologyFactory {
    // 여러개의 토플러지가 가능해야하니 리스트로 받는다.

    private static final String SOURCE_QUERY_KEY = "source";
    private static final String TARGET_QUERY_KEY = "target";
    private static final String LAS_COMMIT_KEY = "lastCommit";

    // 메니저로부터 세션 맵을 받고 토플로지 생성
    public List<Topology> genToplogy(List<TopologyConfig> topologyConfigs, Map<String,SyncMonitorSession> connectionMap){
        List<Topology> generatedTopologyList = new ArrayList<>();

        for(TopologyConfig topologyConfig: topologyConfigs){
            Map<String, String> query = findQuery(topologyConfig);
            String topologyName = topologyConfig.getName();
            String proSyncUser = topologyConfig.getSource().getProSyncUser();
            StringBuilder sourceSessionKey = new StringBuilder().append(SessionKeyPrefix.source_);
            sourceSessionKey.append(topologyConfig.getSource().getDbType().toLowerCase()).append("_");
            sourceSessionKey.append(topologyConfig.getSource().getDbSid().toLowerCase());
//            System.out.println("source key: "+sourceSessionKey);
//            System.out.println(connectionMap.get(sourceSessionKey.toString()));
            StringBuilder targetSessionKey = new StringBuilder().append(SessionKeyPrefix.target_);
            targetSessionKey.append(topologyConfig.getTarget().getDbType().toLowerCase()).append("_");
            targetSessionKey.append(topologyConfig.getTarget().getDbSid().toLowerCase());
//            System.out.println("target key: "+targetSessionKey);
//            System.out.println(connectionMap.get(targetSessionKey.toString()));
            Map<String, SyncMonitorSession> sessionMap = findSession(topologyConfig, connectionMap);
//            System.out.println("setting sessionMap key : "+ sessionMap.keySet());
            DbDto source = new DbDto(sessionMap.get(sourceSessionKey.toString()), query.get(SOURCE_QUERY_KEY));
            DbDto target = new DbDto(sessionMap.get(targetSessionKey.toString()), query.get(TARGET_QUERY_KEY));
            generatedTopologyList.add(new Topology(topologyName,proSyncUser, source, target, topologyConfig));
        }

        // 쿼리 판단해서 할당, 세션 검사해서 할당

        return generatedTopologyList;
    }
    private Map<String, String> findQuery(TopologyConfig topologyConfig){
        // 디비 종류 마다, 소스 타겟 따라 쿼리가 다르다

        String sourceQuery="";
        String targetQuery="";
        String lastCommitTimeQuery="";
        // check source
        if("ORACLE".equalsIgnoreCase(topologyConfig.getSource().getDbType())){
            sourceQuery = QueryTsnOrSCN.getOracle(topologyConfig.getSource());

        }else if ("TIBERO".equalsIgnoreCase(topologyConfig.getSource().getDbType())){
            sourceQuery = QueryTsnOrSCN.getTibero(topologyConfig.getSource());
        }

        // check target
        if("ORACLE".equalsIgnoreCase(topologyConfig.getTarget().getDbType())){
            targetQuery = QueryPrsLct.getOracle(topologyConfig.getTarget());

        }else if ("TIBERO".equalsIgnoreCase(topologyConfig.getTarget().getDbType())){
            targetQuery = QueryPrsLct.getTibero(topologyConfig.getTarget());
        }


        // time target 소스 또한 타겟의 마지막 커밋 타임 알고 있다.
        if("ORACLE".equalsIgnoreCase(topologyConfig.getTarget().getDbType())){
            lastCommitTimeQuery = QueryPrsLct.getLastCommitTime(topologyConfig.getTarget());

        }else if ("TIBERO".equalsIgnoreCase(topologyConfig.getTarget().getDbType())){
            lastCommitTimeQuery = QueryPrsLct.getLastCommitTime(topologyConfig.getTarget());
        }

        Map<String, String> res = new HashMap<>();

        //todo 해당 부분 널일 경우 처리 need
        if(!sourceQuery.isEmpty()){
            res.put(SOURCE_QUERY_KEY, sourceQuery);
        }
        if(!targetQuery.isEmpty()){
            res.put(TARGET_QUERY_KEY,targetQuery);
        }
        if(!lastCommitTimeQuery.isEmpty()){
            res.put(LAS_COMMIT_KEY,lastCommitTimeQuery);
        }
        return res;

    }

    // 커넥션 맵 의 키는 source/target_dbtype_sid
    private Map<String, SyncMonitorSession> findSession(TopologyConfig topologyConfig, Map<String, SyncMonitorSession> connectionMap){
        // 싱글톤이니 소스 타겟 별로 무조건 하나씩을 가지게 하자.. 재활용 하면 영향도가 어떻게 되지..
        SyncMonitorSession sourceSession = null;
        SyncMonitorSession targetSession = null;
        StringBuilder sourceKeyBase = new StringBuilder().append(SessionKeyPrefix.source_);
        StringBuilder targetKeyBase = new StringBuilder().append(SessionKeyPrefix.target_);
        String sourceKey = "";
        String targetKey = "";


        if("ORACLE".equalsIgnoreCase(topologyConfig.getSource().getDbType())){
            sourceKey = sourceKeyBase.append(topologyConfig.getSource().getDbType().toLowerCase()).append("_")
                    .append(topologyConfig.getSource().getDbSid().toLowerCase()).toString();
            sourceSession = connectionMap.get(sourceKey);
        } else if ("TIBERO".equalsIgnoreCase(topologyConfig.getSource().getDbType())) {
            sourceKey = sourceKeyBase.append(topologyConfig.getSource().getDbType().toLowerCase()).append("_")
                    .append(topologyConfig.getSource().getDbSid().toLowerCase()).toString();
            sourceSession = connectionMap.get(sourceKey);
        }
        if("ORACLE".equalsIgnoreCase(topologyConfig.getTarget().getDbType())){
            targetKey = targetKeyBase.append(topologyConfig.getTarget().getDbType().toLowerCase()).append("_")
                    .append(topologyConfig.getTarget().getDbSid().toLowerCase()).toString();
            targetSession = connectionMap.get(targetKey);
        } else if ("TIBERO".equalsIgnoreCase(topologyConfig.getTarget().getDbType())) {
            targetKey = targetKeyBase.append(topologyConfig.getTarget().getDbType().toLowerCase()).append("_")
                    .append(topologyConfig.getTarget().getDbSid().toLowerCase()).toString();
            targetSession = connectionMap.get(targetKey);
        }

        Map<String, SyncMonitorSession> sessionMap = new HashMap<>();

        // 키가 쿼릭키가 아닌 세션키가 들어가야함
        sessionMap.put(sourceKey, sourceSession);
        sessionMap.put(targetKey, targetSession);

        return sessionMap;
    }

}
