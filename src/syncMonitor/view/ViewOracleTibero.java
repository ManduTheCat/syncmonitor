package syncMonitor.view;


import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import syncMonitor.config.wrapper.DbConfig.OracleConfig;
import syncMonitor.config.wrapper.DbConfig.TiberoConfig;
import syncMonitor.query.oracle.OracleOnly;
import syncMonitor.query.tibero.TiberoOnly;
import syncMonitor.session.SyncMonitorSessionOracle;
import syncMonitor.session.SyncMonitorSessionTibero;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewOracleTibero implements View {

    private SyncMonitorSessionOracle oracelSession = null;
    private SyncMonitorSessionTibero tiberoSesseion = null;
    private OracleOnly oracleOnly = null;
    private TiberoOnly tiberoOnly = null;

    public ViewOracleTibero(SyncMonitorSessionOracle oracelSession, OracleConfig oracleConfig, SyncMonitorSessionTibero tiberoSesseion, TiberoConfig tiberoConfig) {
        this.oracelSession = oracelSession;
        this.tiberoSesseion = tiberoSesseion;
        tiberoOnly = new TiberoOnly(tiberoConfig, tiberoSesseion.getConn());
        oracleOnly = new OracleOnly(oracleConfig, oracelSession.getConn());
    }


    @Override
    public void genView() {
        try {
            System.out.print("\u001B[H"); // 커서를 화면 맨 위로 이동
            System.out.flush();
            Integer tiberoPrsLct = Integer.parseInt(tiberoOnly.doGetPrs_lct());
            Integer tiberoTsn = Integer.parseInt(tiberoOnly.doGetTsn());
            Integer oraclePrsLct = Integer.parseInt(oracleOnly.doGetPrsLct());
            Integer oracleSCN = Integer.parseInt(oracleOnly.doGetSCN());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Clean up the input string to remove .0 or any trailing fractional seconds
            String tiberoTime = tiberoOnly.doGetTime().split("\\.")[0];
            String oracleTime = oracleOnly.doGetTime().split("\\.")[0];

            LocalDateTime tiberoLastCommitTime = LocalDateTime.parse(tiberoTime, formatter);
            LocalDateTime oracleLastCommitTime = LocalDateTime.parse(oracleTime, formatter);

            AsciiTable asciiTitle = new AsciiTable();
            asciiTitle.addRow("Prosync Monitor - Tibero & Oracle Sync Status").setTextAlignment(TextAlignment.CENTER);
            asciiTitle.getContext().setWidth(50);
            String titleRender = asciiTitle.render(50);
            System.out.println(titleRender);

            AsciiTable asciiTableTSn= new AsciiTable();
            asciiTableTSn.addRule();
            asciiTableTSn.addRow("Sync way", "SOURCE TSN ", "TARGET TSN", "TSN_GAP");
            asciiTableTSn.addRule();
            asciiTableTSn.addRow("T->O", tiberoTsn, oraclePrsLct, (tiberoPrsLct - oraclePrsLct));
            asciiTableTSn.addRule();
            asciiTableTSn.addRow("O->T", oracleSCN, tiberoPrsLct, (oracleSCN - tiberoPrsLct));
            asciiTableTSn.addRule();
            asciiTableTSn.setTextAlignment(TextAlignment.CENTER);
            String tsnRender = asciiTableTSn.render(50);
            System.out.println(tsnRender);

            AsciiTable asciiTableTime= new AsciiTable();
            asciiTableTime.addRule();
            asciiTableTime.addRow("curr time" , LocalDateTime.now().format(formatter));
            asciiTableTime.addRule();
            asciiTableTime.addRow("oracle last commit" , oracleLastCommitTime);
            asciiTableTime.addRule();
            asciiTableTime.addRow("tibero last commit" , tiberoLastCommitTime);
            asciiTableTime.addRule();
            asciiTableTime.setTextAlignment(TextAlignment.CENTER);
            String timeRender = asciiTableTime.render(50);
            System.out.println(timeRender);
            System.out.print("\u001B[H"); // 커서를 화면 맨 위로 이동
            System.out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
