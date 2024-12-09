package syncMonitor;

import syncMonitor.config.DbConfig;
import syncMonitor.config.MonitorConfig;
import syncMonitor.session.SyncMonitorSession;
import syncMonitor.session.SyncMonitorSessionOracle;
import syncMonitor.session.SyncMonitorSessionTibero;
import syncMonitor.view.ViewOracle;
import syncMonitor.view.ViewTibero;

public class Main {

    public static void main(String[] args) {
        MonitorConfig config = new MonitorConfig();
        DbConfig tiberoConfig = config.getTiberoConfig();
        DbConfig oracleConfig = config.getOracleConfig();
        System.out.println("Tibero Configuration:");
        System.out.println(tiberoConfig);

        System.out.println("\nOracle Configuration:");
        System.out.println(oracleConfig);

        // connection
        SyncMonitorSession sessionTibero = SyncMonitorSessionTibero.getSyncMonitorSession(
                tiberoConfig.getIp(),
                tiberoConfig.getPort(),
                tiberoConfig.getId(),
                tiberoConfig.getPwd(),
                tiberoConfig.getDbSid()
        );


        ViewTibero viewTibero = new ViewTibero(sessionTibero,
                tiberoConfig.getDbLink(),
                tiberoConfig.getUser1(),
                tiberoConfig.getUser2()
        );
        viewTibero.doPrint();

    }

}
