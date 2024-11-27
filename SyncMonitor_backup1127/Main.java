package SyncMonitor_backup1127;

import java.text.SimpleDateFormat;

public class Main{
    public static void main(String[] args) {

        SyncMonitor sMon = new SyncMonitor();
        sMon.setConnStr("192.168.1.188", "4628", "tibero", "tmax"); // 수정 필요
        sMon.connect();
        try {
            for (int i = 0; i < 100000000; i++) {
                java.util.Date d = new java.util.Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

                System.out.println("=============================");
                System.out.println("Prosync Monitor for 4");
                System.out.println("=============================");
                System.out.println("====================================================================================================");
                System.out.println("Current Time: " + sdf.format(d).toString());
                System.out.println(" ");
                System.out.println("====================================================================================================");
                System.out.println("Sync way" + " SOURCE TSN   :   TARGET TSN    : TSN_GAP ");
                System.out.println("====================================================================================================");
                //System.out.println("U3->AH" +"  :  "+ sMon.gather("p_u3","p_ah"));// 수정 필요
                System.out.println("T2O" + "  :  " + sMon.gatherT("jsh_tto", "jsh_tto"));// 수정 필요
                System.out.println("O2T" + "  :  " + sMon.gatherO("jsh_ott", "jsh_ott"));// 수정 필요
                //System.out.println("AH->U3" +"  :  "+ sMon.gather("p_ah","p_u3"));// 수정 필요
                System.out.println("====================================================================================================");
                System.out.println(" ");
                System.out.println("CURENT_TIME         :       LAST COMMIT TIME");
                System.out.println("--<T20>---------------------------------------------------------------------------------- ");
                System.out.println(sdf.format(d).toString() + " / " + sMon.getTXtimeT("jsh_tto", "jsh_tto"));// 수정 필요
                //System.out.println(sdf.format(d).toString() +" / "+ sMon.getTXtime("tlink","tlink"));// 수정 필요
                System.out.println("--<O2T>---------------------------------------------------------------------------------- ");
                System.out.println(sdf.format(d).toString() + " / " + sMon.getTXtimeO("jsh_ott", "jsh_ott"));// 수정 필요
                //System.out.println(sdf.format(d).toString() +" / "+ sMon.getTXtime("p_ah","p_u3"));// 수정 필요
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        sMon.disconnect();

    }

}
