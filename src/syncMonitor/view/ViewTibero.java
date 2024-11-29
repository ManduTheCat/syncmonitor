package syncMonitor.view;

import syncMonitor.dbLink.Dblink;
import syncMonitor.dbLink.tibero.OracleToTibero;
import syncMonitor.dbLink.tibero.TiberoToOracle;
import syncMonitor.session.SyncMonitorSession;

import java.text.SimpleDateFormat;

public class ViewTibero {
    private SyncMonitorSession session = null;
    private Dblink tiberoToOracle = null;
    private Dblink oracleToTibero = null;

    public ViewTibero(SyncMonitorSession session, String dbLinkName, String source, String target) {
        this.session = session;
        this.oracleToTibero = new OracleToTibero(dbLinkName, source, target, session.getConn());
        this.tiberoToOracle = new TiberoToOracle(dbLinkName,target, source, session.getConn());
    }

    public void doPrint() {

        try {
            for (int i = 0; i < 100000000; i++) { // 제한두는 이유?
                java.util.Date d = new java.util.Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

                System.out.println("=============================");
                System.out.println("Prosync Monitor for 4");
                System.out.println("=============================");
                System.out.println("IN Tibero");
                System.out.println("====================================================================================================");
                System.out.println("Current Time: " + sdf.format(d).toString());
                System.out.println(" ");
                System.out.println("====================================================================================================");
                System.out.println("Sync way" + " SOURCE TSN   :   TARGET TSN    : TSN_GAP ");
                System.out.println("====================================================================================================");
                //System.out.println("U3->AH" +"  :  "+ sMon.gather("p_u3","p_ah"));// 수정 필요
                System.out.println("T2O" + "  :  " + tiberoToOracle.doGetTsn());
                System.out.println("O2T" + "  :  " + oracleToTibero.doGetTsn());
                //System.out.println("AH->U3" +"  :  "+ sMon.gather("p_ah","p_u3"));// 수정 필요
                System.out.println("====================================================================================================");
                System.out.println(" ");
                System.out.println("CURENT_TIME         :       LAST COMMIT TIME");
                System.out.println("--<T20>---------------------------------------------------------------------------------- ");
                System.out.println(sdf.format(d).toString() + " / " + tiberoToOracle.doGetTime());// 수정 필요
                //System.out.println(sdf.format(d).toString() +" / "+ sMon.getTXtime("tlink","tlink"));// 수정 필요
                System.out.println("--<O2T>---------------------------------------------------------------------------------- ");
                System.out.println(sdf.format(d).toString() + " / " + oracleToTibero.doGetTime());// 수정 필요
                //System.out.println(sdf.format(d).toString() +" / "+ sMon.getTXtime("p_ah","p_u3"));// 수정 필요
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
