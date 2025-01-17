package syncMonitor.topology;

import lombok.Getter;
import syncMonitor.session.SyncMonitorSession;

@Getter
public class DbDto {
    private final SyncMonitorSession session;
    private final String query; // 만들어지면서 팩토리가 할당

    // 팩토리만 사용하기 위해 접근 디폴트로
    DbDto(SyncMonitorSession sourceSession, String sourceQuery) {
        this.session = sourceSession;
        this.query = sourceQuery;
    }

}
