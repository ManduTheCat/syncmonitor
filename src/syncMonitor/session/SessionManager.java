package syncMonitor.session;

import lombok.Getter;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String sourceKey = sourceKeySb.append("source")
                .append(topologyConfig.getSource().getDbType())
                .append("_")
                .append(topologyConfig.getSource().getDbSid()).toString();
        String targetKey = targetKeySb.append("target")
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
        if(sourceSession != null){
            this.connetcionMap.put(sourceKey, sourceSession);
        }
        if(targetSession != null){
            this.connetcionMap.put(targetKey, targetSession);
        }
    }

    public void addTopologySession(List<TopologyConfig> topologyConfigs){
        for(TopologyConfig topologyConfig :topologyConfigs){
            StringBuilder sourceKeySb = new StringBuilder();
            StringBuilder targetKeySb = new StringBuilder();
            String sourceKey = sourceKeySb.append("source")
                    .append(topologyConfig.getSource().getDbType())
                    .append("_")
                    .append(topologyConfig.getSource().getDbSid()).toString();
            String targetKey = targetKeySb.append("target")
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
            if(sourceSession != null){
                this.connetcionMap.put(sourceKey, sourceSession);
            }
            if(targetSession != null){
                this.connetcionMap.put(targetKey, targetSession);
            }
        }
    }

}
