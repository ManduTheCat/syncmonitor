package syncMonitor.topology;

import lombok.Getter;
import syncMonitor.session.SyncMonitorSession;

@Getter

public class DbDto {
    private SyncMonitorSession session;
    private String query;

    public DbDto(SyncMonitorSession sourceSession, String sourceQuery) {
        this.session = sourceSession;
        this.query = sourceQuery;
    }

    public void setQuery(String query) {
        if(!query.isEmpty()){
            this.query = query;
        }
        else {
            System.out.println("쿼리 생성 실패");
        }

        // 니증에 에러코드 추가
    }
}
