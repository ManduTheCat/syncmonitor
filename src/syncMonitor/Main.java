package syncMonitor;

import syncMonitor.config.wrapper.DbConfig.OracleConfig;
import syncMonitor.config.wrapper.DbConfig.TiberoConfig;
import syncMonitor.config.YmlConfigWrapper;
import syncMonitor.config.wrapper.DbConfig.TopologyConfig;
import syncMonitor.config.wrapper.MonitorConfig;
import syncMonitor.mode.Normal;
import syncMonitor.mode.OnlyTibero;
import view.View;
import view.ViewOracleTibero;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        YmlConfigWrapper config = new YmlConfigWrapper();
//        TiberoConfig tiberoConfig = config.getTiberoConfig();
//        OracleConfig oracleConfig = config.getOracleConfig();
        List<TopologyConfig> topologyConfigList = config.getTiberoConfig();
        MonitorConfig monitorConfig = config.getMonitorConfig();
        for(TopologyConfig topologyConfig: topologyConfigList){
            if("normal".equalsIgnoreCase(topologyConfig.getMode())){
                System.out.println("is normal");
                Normal normalModeHandler = new Normal(topologyConfig);
                ViewOracleTibero viewOracleTibero = normalModeHandler.getViewOracleTibero();

            }
        }
        //BothTiberoOracle bothTiberoOracle = new BothTiberoOracle(oracleConfig, tiberoConfig);
       // View viewBoth = bothTiberoOracle.getViewOracleTibero();
        //viewBoth.doPrint();

//        OnlyTibero onlyTibero = new OnlyTibero(tiberoConfig);
//        View viewTibero = onlyTibero.getViewTibero();
//        viewTibero.doPrint();
    }

}
