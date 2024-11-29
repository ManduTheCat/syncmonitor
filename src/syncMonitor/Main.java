package syncMonitor;

import syncMonitor.session.SyncMonitorSession;
import syncMonitor.session.SyncMonitorSessionOracle;
import syncMonitor.session.SyncMonitorSessionTibero;
import syncMonitor.view.ViewOracle;
import syncMonitor.view.ViewTibero;

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



        // 만드는중
        String oracleIp = "192.168.1.213";
        String oraclePort = "1521";
        String oracleId = "JSH_OTT";
        String oraclePwd = "jsh";
        String oracleDB_LINK = "TLINK";  //?
        String oracleUser1 = "jsh_ott"; //oracleUser1 o -> t 기준
        String oracleUser2 = "jsh_tto"; //OracleUser2
        String oracleDB_SID = "KIW";


        // connection
        SyncMonitorSession sessionTibero = SyncMonitorSessionTibero.getSyncMonitorSession(
                tiberoIp, tiberoPort, tiberoId, tiberoPwd, tiberoDB_SID
        );

//        SyncMonitorSession sessionOracle = SyncMonitorSessionOracle.getSyncMonitorSession(
//                oracleIp, oraclePort, oracleId, oraclePwd, oracleDB_SID
//
//        );

//        System.out.println(sessionOracle.getConn().toString());

        ViewTibero viewTibero = new ViewTibero(sessionTibero, tiberoDB_LINK, tiberoUser1, tiberoUser2);
        viewTibero.doPrint();
//        ViewOracle viewOracle = new ViewOracle(sessionOracle, oracleDB_LINK, oracleUser1, oracleUser2);
//        viewOracle.doPrint();



        //end
        sessionTibero.disconnect();

    }

}
