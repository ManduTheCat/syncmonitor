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

    private static final String SOURCE_KEY = "source";
    private static final String TARGET_KEY = "target";

    public List<Topology> genToplogy(List<TopologyConfig> topologyConfigs, Map<String,SyncMonitorSession> connectionMap){
        List<Topology> generatedTopologyList = new ArrayList<>();;

        for(TopologyConfig topologyConfig: topologyConfigs){
            Map<String, String> query = findQuery(topologyConfig);
            Map<String, SyncMonitorSession> session = findSession(topologyConfig, connectionMap);
            DbDto source = new DbDto(session.get(SOURCE_KEY), query.get(SOURCE_KEY));// 쿼리와 세션 할당
            DbDto target = new DbDto(session.get(TARGET_KEY), query.get(TARGET_KEY));
            generatedTopologyList.add(new Topology(source, target));
        }

        // 쿼리 판단해서 할당, 세션 검사해서 할당

        return generatedTopologyList;
    }
    private Map<String, String> findQuery(TopologyConfig topologyConfig){
        // 디비 종류 마다, 소스 타겟 따라 쿼리가 다르다

        String sourceQuery="";
        String targetQuery="";
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

        Map<String, String> res = new HashMap<>();
        if(!sourceQuery.isEmpty()){
            res.put(SOURCE_KEY, sourceQuery);
        }
        if(!targetQuery.isEmpty()){
            res.put(TARGET_KEY,targetQuery);
        }
        return res;

    }

    // 커넥션 맵 의 키는 source/target_dbtype_sid
    private Map<String, SyncMonitorSession> findSession(TopologyConfig topologyConfig, Map<String, SyncMonitorSession> connectionMap){
        // 싱글톤이니 소스 타겟 별로 무조건 하나씩을 가지게 하자.. 재활용 하면 영향도가 어떻게 되지..
        SyncMonitorSession sourceSession = null;
        SyncMonitorSession targetSession = null;
        if("ORACLE".equalsIgnoreCase(topologyConfig.getSource().getDbType())){
            sourceSession= connectionMap.get("SOURCE_ORACLE_"+topologyConfig.getSource().getDbSid());
        } else if ("TIBERO".equalsIgnoreCase(topologyConfig.getSource().getDbType())) {
            sourceSession= connectionMap.get("SOURCE_TIBERO_"+topologyConfig.getSource().getDbSid());
        }
        if("ORACLE".equalsIgnoreCase(topologyConfig.getTarget().getDbType())){
            sourceSession= connectionMap.get("TARGET_ORACLE_"+topologyConfig.getSource().getDbSid());
        } else if ("TIBERO".equalsIgnoreCase(topologyConfig.getTarget().getDbType())) {
            sourceSession= connectionMap.get("TARGET_TIBERO_"+topologyConfig.getSource().getDbSid());
        }

        Map<String, SyncMonitorSession> res = new HashMap<>();

        res.put(SOURCE_KEY, sourceSession);
        res.put(TARGET_KEY, targetSession);

        return res;
    }

}
