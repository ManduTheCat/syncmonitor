package syncMonitor;

import syncMonitor.config.YmlConfigWrapper;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;
import syncMonitor.config.wrapper.MonitorConfig;
import syncMonitor.session.SessionManager;
import syncMonitor.session.SyncMonitorSession;
import syncMonitor.topology.Topology;
import syncMonitor.topology.TopologyFactory;
import syncMonitor.view.View;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws SQLException {
        YmlConfigWrapper config = new YmlConfigWrapper();
        System.out.println(config);
        List<TopologyConfig> topologyConfigList = config.getConfigWrapper().getTopology();
        MonitorConfig monitorConfig = config.getConfigWrapper().getMonitor();
        TopologyFactory topologyFactory = new TopologyFactory();
        // 메니저로 커넥션 수행 및 map 으로 관리
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.addTopologySession(topologyConfigList);
        // 맵으로 만들어 토플러지 팩토리에 전달후 전체 토플러지 리스트 생성
        Map<String, SyncMonitorSession> sessionMap = sessionManager.getConnetcionMap();
        List<Topology> topologies = topologyFactory.genToplogy(topologyConfigList, sessionMap);
        // 커낵션 매니저가 관리하는 객체 조회 테스트
        for(SyncMonitorSession syncMonitorSession :sessionMap.values()){
            System.out.println(syncMonitorSession.getBaseURL() + "session dual test");
            ResultSet rs = syncMonitorSession.getConn().prepareStatement("select * from dual").executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1).equals("X") ? "ok":"fail");
            }
        }
//        for(Topology topology: topologies){
//            System.out.println();
//            System.out.println(topology.getSource().getQuery());
//            System.out.println(topology.getTarget().getQuery());
//        }
        View view = new View(topologies);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\u001B[H\u001B[2J"); // 화면 clear
            System.out.println("\n[INFO] Program terminated by user.");
        }));

        try {
            System.out.println("\u001B[H\u001B[2J"); // 화면 clear
            System.out.flush();

            while (true) {
                System.out.print("\u001B[H"); // 커서를 화면 맨 위로 이동
                System.out.flush();
                System.out.println("press 'ctl + c' to exit...");
                view.genView();
                System.out.print("\u001B[H"); // 커서를 화면 맨 위로 이동
                System.out.flush();

                if (System.in.available() > 0) {
                    char userInput = (char) System.in.read();
                    if (userInput == 'x' || userInput == 'X') {
                        System.out.println("[INFO] Exiting program...");

                        System.out.println("\u001B[H\u001B[2J");
                        System.out.flush();
                        return; // main 종료
                    }
                }
                // 짧은 대기 시간 추가 (0.5초)
                //todo 1초로 변경
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            for (Topology topologyDto :) {
//                //토플러지 객체에서 세션종료
//
//            }
        }
    }

}
