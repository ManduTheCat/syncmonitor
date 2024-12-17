package syncMonitor;

import syncMonitor.config.YmlConfigWrapper;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;
import syncMonitor.config.wrapper.MonitorConfig;
import syncMonitor.mode.Normal;
import syncMonitor.view.View;
import syncMonitor.view.ViewOracleTibero;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        YmlConfigWrapper config = new YmlConfigWrapper();
        List<TopologyConfig> topologyConfigList = config.getTiberoConfig();
        MonitorConfig monitorConfig = config.getMonitorConfig();
        List<View> viewList = new ArrayList<View>();
        for(TopologyConfig topologyConfig: topologyConfigList){
            if("normal".equalsIgnoreCase(topologyConfig.getMode())){
                System.out.println("topology name : " + topologyConfig.getName());
                System.out.println("mode: normal");
                Normal normalModeHandler = new Normal(topologyConfig);
                ViewOracleTibero viewOracleTibero = normalModeHandler.getViewOracleTibero();
                viewList.add(viewOracleTibero);

            }
        }
        //todo 무한루프형태로 뷰리스트 돌면서 출력
        //BothTiberoOracle bothTiberoOracle = new BothTiberoOracle(oracleConfig, tiberoConfig);
       // View viewBoth = bothTiberoOracle.getViewOracleTibero();
        //viewBoth.doPrint();

//        OnlyTibero onlyTibero = new OnlyTibero(tiberoConfig);
//        View viewTibero = onlyTibero.getViewTibero();
//        viewTibero.doPrint();
    }

}
