package SyncMonitor;

import print.Print;

import java.text.SimpleDateFormat;

public class Main{
    public static void main(String[] args) {

        SyncMonitor sMon = new SyncMonitor("192.168.1.188", "4628", "tibero", "tmax");
        sMon.connect();
        Print print = new Print();
        print.do_print(sMon);
        sMon.disconnect();

    }

}
