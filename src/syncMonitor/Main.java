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
        // sessionMap 으로 만들어 토플러지 팩토리에 전달후 전체 토플러지 모음 생성
        // sessionMap 은 일종의 커낵션 풀
        // 토플러지는 커넥션 풀에서 커낵션을 꺼내 쓰는 개념
        Map<String, SyncMonitorSession> sessionMap = sessionManager.getConnetcionMap(topologyConfigList);
        List<Topology> topologies = topologyFactory.genToplogy(topologyConfigList, sessionMap);

        View view = new View(topologies, monitorConfig);

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
                view.genView(); // 데이터 조회이벤트(run 으로 시작하는 메소드) 발생, 출력 테이블 생성
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
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("closed");
            topologies.stream().forEach(Topology::disconnectAll);
        }
    }

}
