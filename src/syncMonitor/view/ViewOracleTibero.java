package syncMonitor.view;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import syncMonitor.query.oracle.OracleOnly;
import syncMonitor.query.tibero.TiberoOnly;

import java.time.format.DateTimeFormatter;
import java.util.List;

// 노멀에서 전달받고 dto 만들면서 유일 새션 생성
public class ViewOracleTibero implements View {
    private  final int GRAPH_WIDTH = 5;
    private final List<TopologyDto> topologyDtos;

    private  final String RESET = "\u001B[0m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String YELLOW = "\u001B[33m";
    private  final int WARNING_THRESHOLD = 10;
    private  final Double SHRINK_SIZE = 0.00000001;

    public ViewOracleTibero(List<TopologyDto> topologyDtos) {
        this.topologyDtos = topologyDtos;
    }
    private  String createGraphLine(int value, int maxValue) {
        StringBuilder graph = new StringBuilder();
        int graphLength = (int) ((value / (double) maxValue) * GRAPH_WIDTH * SHRINK_SIZE );
        System.out.println(graphLength);
        for (int i = 0; i < graphLength; i++) {
            graph.append("|");
        }
        graph.append(" ").append(value);
        System.out.println(graph.length());
        return graph.toString();
    }
    @Override
    public void genView() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // 제목 출력
            AsciiTable asciiTitle = new AsciiTable();
            asciiTitle.addRule();
            asciiTitle.addRow("Prosync Monitor - Tibero & Oracle Sync Status").setTextAlignment(TextAlignment.CENTER);
            asciiTitle.addRule();
            System.out.println(asciiTitle.render(80));

            // 상태 테이블
            AsciiTable asciiTable = new AsciiTable();
            asciiTable.addRule();
            asciiTable.addRow("TOPOLOGY", "Sync way", "SOURCE TSN", "TARGET TSN", "TSN_GAP");
            asciiTable.addRule();

            for (TopologyDto topology : topologyDtos) {
                // DTO에서 객체 가져오기
;
                TiberoOnly tiberoOnly = topology.getTiberoOnly();
                OracleOnly oracleOnly = topology.getOracleOnly();

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

                System.out.println(topology.getName() + "delay : "+ createGraphLine((oracleSCN - tiberoPrsLct), 100000000));
                System.out.println(topology.getName()+": "+ createGraphLine((tiberoPrsLct - oraclePrsLct), 1));
            }

            // 테이블 출력
            asciiTable.setTextAlignment(TextAlignment.CENTER);
            System.out.println(asciiTable.render(80));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<TopologyDto> getTopologyDtos() {
        return topologyDtos;
    }

}
