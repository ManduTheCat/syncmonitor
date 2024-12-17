package syncMonitor.mode;

import syncMonitor.config.wrapper.DbConfig.OracleConfig;
import syncMonitor.config.wrapper.DbConfig.TiberoConfig;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;
import syncMonitor.session.SyncMonitorSessionOracle;
import syncMonitor.session.SyncMonitorSessionTibero;
import syncMonitor.view.TopologyDto;
import syncMonitor.view.ViewOracleTibero;

import java.util.ArrayList;
import java.util.List;

public class Normal {
    private ViewOracleTibero viewOracleTibero;

    public Normal(List<TopologyConfig> topologyConfigs) {
        // TopologyDto 리스트 준비
        List<TopologyDto> topologyDtos = new ArrayList<>();

        for (TopologyConfig config : topologyConfigs) {
            // 설정 가져오기
            TiberoConfig tiberoConfig = (TiberoConfig) config.getTiberoConfig();
            OracleConfig oracleConfig = (OracleConfig) config.getOracleConfig();

            // 세션 생성
            SyncMonitorSessionTibero tiberoSession = SyncMonitorSessionTibero.getSyncMonitorSession(tiberoConfig);
            SyncMonitorSessionOracle oracleSession = SyncMonitorSessionOracle.getSyncMonitorSession(oracleConfig);

            // DTO 생성 및 리스트에 추가
            TopologyDto dto = new TopologyDto(oracleSession, oracleConfig, tiberoSession, tiberoConfig);
            topologyDtos.add(dto);
        }

        // ViewOracleTibero 객체 생성 (DTO 리스트 전달)
        this.viewOracleTibero = new ViewOracleTibero(topologyDtos);
    }

    public ViewOracleTibero getViewOracleTibero() {
        return viewOracleTibero;
    }
}
