package syncMonitor.view;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import syncMonitor.query.oracle.OracleOnly;
import syncMonitor.query.tibero.TiberoOnly;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// 노멀에서 전달받고 dto 만들면서 유일 새션 생성
public class ViewOracleTibero implements View {

    private final List<TopologyDto> topologyDtos;

    public ViewOracleTibero(List<TopologyDto> topologyDtos) {
        this.topologyDtos = topologyDtos;
    }

    @Override
    public void genView() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Clean up the input string to remove .0 or any trailing fractional seconds

            // 제목 출력
            AsciiTable asciiTitle = new AsciiTable();
            asciiTitle.addRule();
            asciiTitle.addRow("Prosync Monitor - Tibero & Oracle Sync Status").setTextAlignment(TextAlignment.CENTER);
            asciiTitle.addRule();
            System.out.println(asciiTitle.render(80));

            // 상태 테이블
            AsciiTable asciiTable = new AsciiTable();
            asciiTable.addRule();
            asciiTable.addRow("TOPOLOGY", "SYNC WAY", "SOURCE TSN", "TARGET TSN", "TSN_GAP");
            asciiTable.addRule();
            AsciiTable asciiTableTime= new AsciiTable();
            asciiTableTime.addRule();
            asciiTableTime.addRow("curr time" , LocalDateTime.now().format(formatter));
            asciiTableTime.addRule();

            for (TopologyDto topology : topologyDtos) {
                // DTO에서 객체 가져오기

                TiberoOnly tiberoOnly = topology.getTiberoOnly();
                OracleOnly oracleOnly = topology.getOracleOnly();

                String tiberoTime = tiberoOnly.doGetTime().split("\\.")[0];
                String oracleTime = oracleOnly.doGetTime().split("\\.")[0];

                LocalDateTime tiberoLastCommitTime = LocalDateTime.parse(tiberoTime, formatter);
                LocalDateTime oracleLastCommitTime = LocalDateTime.parse(oracleTime, formatter);
                // 데이터 조회
                Integer tiberoTsn = Integer.parseInt(tiberoOnly.doGetTsn());
                Integer oraclePrsLct = Integer.parseInt(oracleOnly.doGetPrsLct());
                Integer tiberoPrsLct = Integer.parseInt(tiberoOnly.doGetPrs_lct());
                Integer oracleSCN = Integer.parseInt(oracleOnly.doGetSCN());

                // 테이블 행 추가
                asciiTable.addRow(topology.getName(), "T->O", tiberoTsn, oraclePrsLct, (tiberoPrsLct - oraclePrsLct));
                asciiTable.addRule();
                asciiTable.addRow(topology.getName(), "O->T", oracleSCN, tiberoPrsLct, (oracleSCN - tiberoPrsLct));
                asciiTable.addRule();
                asciiTable.addRule();

                asciiTableTime.addRow("oracle last commit" , oracleLastCommitTime);
                asciiTableTime.addRule();
                asciiTableTime.addRow("tibero last commit" , tiberoLastCommitTime);
                asciiTableTime.addRule();
                asciiTableTime.addRule();
            }

            // 테이블 출력


            asciiTable.setTextAlignment(TextAlignment.CENTER);
            System.out.println(asciiTable.render(80));

            asciiTableTime.setTextAlignment(TextAlignment.CENTER);
            System.out.println(asciiTableTime.render(80));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<TopologyDto> getTopologyDtos() {
        return topologyDtos;
    }

}
