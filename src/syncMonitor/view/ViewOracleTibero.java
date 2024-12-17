package syncMonitor.view;


import syncMonitor.config.wrapper.DbConfig.OracleConfig;
import syncMonitor.config.wrapper.DbConfig.TiberoConfig;
import syncMonitor.query.oracle.OracleOnly;
import syncMonitor.query.tibero.TiberoOnly;
import syncMonitor.session.SyncMonitorSessionOracle;
import syncMonitor.session.SyncMonitorSessionTibero;

import java.text.SimpleDateFormat;

public class ViewOracleTibero implements View {

    private SyncMonitorSessionOracle oracelSession = null;
    private SyncMonitorSessionTibero tiberoSesseion = null;
    private OracleOnly oracleOnly = null;
    private TiberoOnly tiberoOnly = null;

    public ViewOracleTibero(SyncMonitorSessionOracle oracelSession, OracleConfig oracleConfig, SyncMonitorSessionTibero tiberoSesseion, TiberoConfig tiberoConfig) {
        this.oracelSession = oracelSession;
        this.tiberoSesseion = tiberoSesseion;
        tiberoOnly = new TiberoOnly(tiberoConfig,tiberoSesseion.getConn());
        oracleOnly = new OracleOnly(oracleConfig, oracelSession.getConn());
    }


    @Override
    public void genView() {

        try {
                java.util.Date d = new java.util.Date();
                Integer tiberoPrsLct = Integer.parseInt(tiberoOnly.doGetPrs_lct());
                Integer tiberoTsn = Integer.parseInt(tiberoOnly.doGetTsn());
                Integer oraclePrsLct = Integer.parseInt(oracleOnly.doGetPrsLct());
                Integer oracleSCN = Integer.parseInt(oracleOnly.doGetSCN());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                System.out.println("======================================");
                System.out.println("Prosync Monitor for 4");
                System.out.println("======================================");
                System.out.println("IN Tibero and Oracle");
                System.out.println("====================================================================================================");
                System.out.println("Current Time: " + sdf.format(d).toString());
                System.out.println(" ");
                System.out.println("====================================================================================================");
                System.out.println("Sync way" + " SOURCE TSN   :   TARGET TSN    : TSN_GAP ");
                System.out.println("====================================================================================================");
                //System.out.println("U3->AH" +"  :  "+ sMon.gather("p_u3","p_ah"));// 수정 필요
                System.out.println("T->O " + "  :  " + tiberoTsn + " / " + oraclePrsLct + " / " + (tiberoPrsLct-tiberoTsn));
                System.out.println("O->T " + "  :  " + oracleSCN + " / " + tiberoPrsLct + " / " + (oraclePrsLct-oracleSCN));
                //System.out.println("AH->U3" +"  :  "+ sMon.gather("p_ah","p_u3"));// 수정 필요
                System.out.println("====================================================================================================");
                System.out.println(" ");
                System.out.println("CURENT_TIME         :       LAST COMMIT TIME");
                System.out.println("--<T20>---------------------------------------------------------------------------------- ");
                System.out.println(sdf.format(d).toString() + " / "  );// 수정 필요
                //System.out.println(sdf.format(d).toString() +" / "+ sMon.getTXtime("tlink","tlink"));// 수정 필요
                System.out.println("--<O2T>---------------------------------------------------------------------------------- ");
                System.out.println(sdf.format(d).toString() + " / " );// 수정 필요
                //System.out.println(sdf.format(d).toString() +" / "+ sMon.getTXtime("p_ah","p_u3"));// 수정 필요
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
