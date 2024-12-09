package syncMonitor;

import syncMonitor.config.wrapper.DbConfig.OracleConfig;
import syncMonitor.config.wrapper.DbConfig.TiberoConfig;
import syncMonitor.config.YmlConfigWrapper;
import syncMonitor.config.wrapper.MonitorConfig;
import syncMonitor.mode.OnlyTibero;
import view.View;

public class Main {

    public static void main(String[] args) {
        YmlConfigWrapper config = new YmlConfigWrapper();
        TiberoConfig tiberoConfig = config.getTiberoConfig();
        OracleConfig oracleConfig = config.getOracleConfig();
        MonitorConfig monitorConfig = config.getMonitorConfig();

        System.out.println("Tibero Configuration:");
        System.out.println(tiberoConfig);

        System.out.println("\nOracle Configuration:");
        System.out.println(oracleConfig);

        //BothTiberoOracle bothTiberoOracle = new BothTiberoOracle(oracleConfig, tiberoConfig);
       // View viewBoth = bothTiberoOracle.getViewOracleTibero();
        //viewBoth.doPrint();

        OnlyTibero onlyTibero = new OnlyTibero(tiberoConfig);
        View viewTibero = onlyTibero.getViewTibero();
        viewTibero.doPrint();
    }

}
