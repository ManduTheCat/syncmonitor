package syncMonitor.topology;

import lombok.Getter;

// 설정 할때(최초생성) 빼고는 변경되서는 안되는 정보들
@Getter
public class Topology {
    private final DbDto source;
    private final DbDto target;

    //팩토리에서만 생성할수 있다 접근 패키지로 관리
    Topology(DbDto source, DbDto target) {
        this.source = source;
        this.target = target;
    }

    public String runQuery(){
        return null;
    }
}
