package syncMonitor.view;


import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import lombok.Getter;
import syncMonitor.topology.Topology;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class View{
    @Getter
    private final List<Topology> topologyList;

    public View(List<Topology> topologyList) {
        this.topologyList = topologyList;
    }
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

            AsciiTable asciiTable = new AsciiTable();
            asciiTable.addRule();
            asciiTable.addRow("TOPOLOGY", "PSYC ID", "SOURCE TSN", "TARGET TSN", "TSN_GAP");
            asciiTable.addRule();
            AsciiTable asciiTableTime= new AsciiTable();
            asciiTableTime.addRule();
            asciiTableTime.addRow("curr time" , LocalDateTime.now().format(formatter));
            asciiTableTime.addRule();
            for (Topology topology : topologyList) {
                // DTO에서 객체 가져오기
                //String LastCommitTimeRes = topology.runTargetTimeQuery().split("\\.")[0];
                //String LastCommitTimeRes = topology.runTargetTimeQuery();

                //LocalDateTime targetCommitTime = LocalDateTime.parse(LastCommitTimeRes, formatter);
                LocalDateTime targetCommitTime = LocalDateTime.now();
                // 데이터 조회
                Integer sourceCn = topology.runSourceChangeNumberQuery();
                Integer targetCn = topology.runTargetChangeNumberQuery();

//                System.out.println("sourceCn: " +sourceCn);
//                System.out.println("targetCn:" + targetCn);
                // 테이블 행 추가
                asciiTable.addRow(topology.getTopologyName() == null ? "null":topology.getTopologyName(),
                        topology.getProSyncUser(), sourceCn, targetCn, (sourceCn - targetCn));
                asciiTable.addRule();

                asciiTableTime.addRow(topology.getTopologyName()+" commited target" , "teset");
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

}
