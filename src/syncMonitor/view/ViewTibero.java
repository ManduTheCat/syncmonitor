package syncMonitor.view;

import syncMonitor.config.wrapper.DbConfig.TiberoConfig;
import syncMonitor.query.Dblink;
import syncMonitor.query.oracle.DbLinkOracleToTibero;
import syncMonitor.query.tibero.DbLinkTiberoToOracle;
import syncMonitor.session.SyncMonitorSession;

import java.sql.Connection;
import java.text.SimpleDateFormat;

public class ViewTibero implements View{
    private Dblink tiberoToOracle = null;
    private Dblink oracleToTibero = null;

    public ViewTibero(SyncMonitorSession session, TiberoConfig tiberoConfig) {

        Connection conn = session.getConn();
        if (conn == null) {
            throw new IllegalStateException("Database connection is null. Unable to proceed.");
        }

        // user2 user1 이 모호함
        this.tiberoToOracle = new DbLinkTiberoToOracle(tiberoConfig.getDbLink(), tiberoConfig.getDblinkUser(), tiberoConfig.getUser(), conn);
        this.oracleToTibero = new DbLinkOracleToTibero(tiberoConfig.getDbLink(), tiberoConfig.getUser(), tiberoConfig.getDblinkUser(), conn);
    }

    @Override
    public void genView() {

        try {
            for (int i = 0; i < 100000000; i++) { // 제한두는 이유?
                java.util.Date d = new java.util.Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

                System.out.println("============================================");
                System.out.println("Prosync Monitor for 4");
                System.out.println("============================================");
                System.out.println("IN Tibero");
                System.out.println("============================================");
                System.out.println("Current Time: " + sdf.format(d).toString());
                System.out.println(" ");
                System.out.println("============================================");
                System.out.println("SyncWay : "+"SOURCE_TSN : TARGET_TSN : TSN_GAP ");
                System.out.println("============================================");
                //System.out.println("U3->AH" +"  :  "+ sMon.gather("p_u3","p_ah"));// 수정 필요
                System.out.println("T2O" + "     : " + tiberoToOracle.doGetTsn());
                System.out.println("O2T" + "     : " + oracleToTibero.doGetTsn());
                //System.out.println("AH->U3" +"  :  "+ sMon.gather("p_ah","p_u3"));// 수정 필요
                System.out.println("============================================");
                System.out.println(" ");
                System.out.println("CURENT_TIME : LAST COMMIT TIME");
                System.out.println("--<T20>------------------------------------");
                System.out.println(sdf.format(d).toString() + " / " + tiberoToOracle.doGetTime());// 수정 필요
                //System.out.println(sdf.format(d).toString() +" / "+ sMon.getTXtime("tlink","tlink"));// 수정 필요
                System.out.println("--<O2T>------------------------------------ ");
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
