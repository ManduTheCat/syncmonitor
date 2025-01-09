package syncMonitor;

import syncMonitor.config.YmlConfigWrapper;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;
import syncMonitor.config.wrapper.MonitorConfig;
import syncMonitor.mode.Normal;
import syncMonitor.mode.OnlyTibero;
import syncMonitor.session.SyncMonitorSession;
import syncMonitor.view.TopologyDto;
import syncMonitor.view.View;
import syncMonitor.view.ViewOracleTibero;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        YmlConfigWrapper config = new YmlConfigWrapper();
        List<TopologyConfig> topologyConfigList = config.getTiberoConfig();
        MonitorConfig monitorConfig = config.getMonitorConfig();
        ArrayList<TopologyConfig> normalList = new ArrayList<>();
        for (TopologyConfig topologyConfig : topologyConfigList) {
            if ("normal".equalsIgnoreCase(topologyConfig.getMode())) {
                System.out.println("topology name : " + topologyConfig.getName());
                System.out.println("mode: normal");
                normalList.add(topologyConfig);
            }
        }
        Normal normalModeHandler = new Normal(normalList);
        ViewOracleTibero viewOracleTibero = normalModeHandler.getViewOracleTibero();

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
                assert viewOracleTibero != null;
                viewOracleTibero.genView();
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
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            for( TopologyDto topologyDto :viewOracleTibero.getTopologyDtos()){
                topologyDto.getOracleSession().disconnect();
                topologyDto.getTiberoSession().disconnect();

            }
        }
    }

}
