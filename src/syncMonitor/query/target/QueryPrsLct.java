package syncMonitor.query.target;

// 프로싱크 아이디 받아서 prsLCT 조회하는 쿼리 반납
public class QueryPrsLct {

    public String query = null;

    public QueryPrsLct() {
        //입력으로 타겟의 정보를 받아야한다 디비타입 정도면 될거 같은데
        if("디비타입".equals("oracle")){
            query=this.getOracle();
        }else if("디비타임".equals("tibero")){
            query=this.getTibero();
        }
    }

    public String getOracle(){
        return null;
    }
    public String getTibero(){
        return null;
    }
}
