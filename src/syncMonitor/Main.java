package syncMonitor;

import syncMonitor.session.SyncMonitorSessionTibero;
import syncMonitor.view.View;

public class Main {

    public static void main(String[] args) {
        String tiberoIp = "192.168.1.188";
        String tiberoPort = "4628";
        String tiberoId = "tibero";
        String tiberoPwd = "tmax";
        String tiberoDB_LINK = "jsh_tto";
        String tiberoUser1 = "jsh_tto"; //tiberoUser1
        String tiberoUser2 = "jsh_ott"; //tiberoUser2
        String tiberoDB_SID = "tibero7_2";


        SyncMonitorSessionTibero session = SyncMonitorSessionTibero.getSyncMonitorSession(
                tiberoIp, tiberoPort, tiberoId, tiberoPwd, tiberoDB_SID
        );
        View print = new View(session, tiberoDB_LINK, tiberoUser1, tiberoUser2);
        print.doPrint();
        session.disconnect();

    }

}
