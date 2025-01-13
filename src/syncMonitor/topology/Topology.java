package syncMonitor.topology;

import lombok.Getter;
import syncMonitor.config.wrapper.DbConfig.DbConfig;

// 설정 할때(최초생성) 빼고는 변경되서는 안되는 정보들
@Getter
public class Topology {
    private final DbDto source;
    private final DbDto target;

    public Topology(DbDto source, DbDto target) {
        this.source = source;
        this.target = target;
    }
}
