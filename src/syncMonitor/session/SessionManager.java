package syncMonitor.session;

import lombok.Getter;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;
import syncMonitor.topology.SessionKeyPrefix;

import java.util.*;

// 세션 관리 객체 Map 으로 객체
// 단일성보장, 추후 멀티쓰래딩 고려해 synchronizedMap 사용
public class SessionManager {


    @Getter
    private final Map<String, SyncMonitorSession> connetcionMap;

    // static final getter
    @Getter
    private static final SessionManager instance = new SessionManager();

    private SessionManager() {
        this.connetcionMap = Collections.synchronizedMap(new HashMap<>());
    }


    // key 규칙 source/target_dbtype_sid
    //
    public void addTopologySession(TopologyConfig topologyConfig){
        StringBuilder sourceKeySb = new StringBuilder();
        StringBuilder targetKeySb = new StringBuilder();
        String sourceKey = sourceKeySb.append(SessionKeyPrefix.source_)
                .append(topologyConfig.getSource().getDbType())
                .append("_")
                .append(topologyConfig.getSource().getDbSid()).toString();
        String targetKey = targetKeySb.append(SessionKeyPrefix.target_)
                .append(topologyConfig.getTarget().getDbType())
                .append("_")
                .append(topologyConfig.getTarget().getDbSid()).toString();

        String sourceDbType = topologyConfig.getSource().getDbType();
        String targetDbType = topologyConfig.getTarget().getDbType();
        SyncMonitorSession sourceSession = null;
        SyncMonitorSession targetSession = null;

        //source session 할당
        if("TIBERO".equalsIgnoreCase(sourceDbType)){
            sourceSession = new SyncMonitorSessionTibero(topologyConfig.getSource());

        } else if ("ORACLE".equalsIgnoreCase(sourceDbType)) {
            sourceSession = new SyncMonitorSessionOracle(topologyConfig.getSource());
        }

        //target session 할당
        if("TIBERO".equalsIgnoreCase(targetDbType)){
            targetSession = new SyncMonitorSessionTibero(topologyConfig.getSource());

        } else if ("ORACLE".equalsIgnoreCase(targetDbType)) {
            targetSession = new SyncMonitorSessionOracle(topologyConfig.getSource());
        }
        if(sourceSession == null){
            throw new NullPointerException();
        }
        if(targetSession == null){
            throw new NullPointerException();
        }
        this.connetcionMap.put(sourceKey, sourceSession);
        this.connetcionMap.put(targetKey, targetSession);
    }

    //todo uuid 로 키 할당 고민
    public void addTopologySession(List<TopologyConfig> topologyConfigs){
        for(TopologyConfig topologyConfig :topologyConfigs){
            StringBuilder sourceKeySb = new StringBuilder();
            StringBuilder targetKeySb = new StringBuilder();
            String sourceKey = sourceKeySb.append(SessionKeyPrefix.source_)
                    .append(topologyConfig.getSource().getDbType().toLowerCase())
                    .append("_")
                    .append(topologyConfig.getSource().getDbSid().toLowerCase()).toString();
            String targetKey = targetKeySb.append(SessionKeyPrefix.target_)
                    .append(topologyConfig.getTarget().getDbType().toLowerCase())
                    .append("_")
                    .append(topologyConfig.getTarget().getDbSid().toLowerCase()).toString();

            String sourceDbType = topologyConfig.getSource().getDbType();
            String targetDbType = topologyConfig.getTarget().getDbType();
            SyncMonitorSession sourceSession = null;
            SyncMonitorSession targetSession = null;

            //source session 할당
            if("TIBERO".equalsIgnoreCase(sourceDbType)){
                sourceSession = new SyncMonitorSessionTibero(topologyConfig.getSource());

            } else if ("ORACLE".equalsIgnoreCase(sourceDbType)) {
                sourceSession = new SyncMonitorSessionOracle(topologyConfig.getSource());
            }

            //target session 할당
            if("TIBERO".equalsIgnoreCase(targetDbType)){
                targetSession = new SyncMonitorSessionTibero(topologyConfig.getTarget());

            } else if ("ORACLE".equalsIgnoreCase(targetDbType)) {
                targetSession = new SyncMonitorSessionOracle(topologyConfig.getTarget());
            }


            if(sourceSession != null){
                this.connetcionMap.put(sourceKey, sourceSession);
            }
            if(targetSession != null){
                this.connetcionMap.put(targetKey, targetSession);
            }

        }


    }

}
