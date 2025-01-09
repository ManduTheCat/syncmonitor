# Prosync Monitor
프로싱크 모니터링을 위한 jdbc 기반 조회 프로그램입니다 
oracle 과 tibero 데이터 베이스 세션을 통해 prs_lct, scn, tsn 비교 및 모니터링 수행 

## 사용법 
```
$  sh run_monitor.sh 
```
### 출력 내용
- TOPOLOGY: 프로싱크 연결된 `DB_SID` 이름 결함 (프로싱크 토플로지 이름이 아닙니다. 출력내용 추후 적절한 이름으로 수정예정)
- SYNC WAY: 프로싱크 동기화 방향 ex) A->B : A 변경사항 을 B 에 적용
- SOURCE TSN : 변경사항 발생한 DB 의 TSN(SCN) 값
  - 티베로 에 보네는 쿼리 : `select current_tsn as tsn from v$database`
  - 오라클 에 보네는 쿼리 : `select current_scn as tsn from v$database`
- TARGET TSN : 변경사항 반영될 target DB 의 마지막 동기화 된 TSN(SCN) 값
  - 티베로 에 보네는 쿼리: `select to_number(tsn) as tsn from [티베로 user: prs_lct 테이블 소유 스키마].prs_lct`
  - 오라클 에 보네는 쿼리: `select to_number(tsn) as tsn from [오라클 user: prs_lct 테이블 소유 스키마].prs_lct `
- TSN_GAP : SOURCE TSN , TARGET TSN 간 차이, 값 클수록 SOURCE, TARGET 간 동기화 느림 의미
- curr time : 현제 시간
- oracle last commit : oracel 마지막 동기화 시간
  - 티베로 에 보네는 쿼리: `select time from [티베로 user: prs_lct 테이블 소유 스키마].prs_lct`
- tibero last commit : tibero 마지막 동기화 시간
  - 티베로 에 보네는 쿼리:`select time from  [오라클 user: prs_lct 테이블 소유 스키마].prs_lct`


 ### 동작 화면
![결과 화면](./doc/res.png)

## 설정방법
config.yml 수정하여 설정 변경가능
```
monitor:
  mode: 모니터 크기 wide, narraw 중입력 
topology:
  - name: sync1
    mode: normal
    target1:
      db: tibero, oracle 중 입력
      ip: 아이피
      port: 리스너포트
      id: jdbc 접속 계정
      pwd: 접속 암호
      user: prs_lct 테이블 소유한 스키마
      dbSid: db sid
    target2:
      db: oracle
      ip: 192.168.1.188
      port: 1525
      id: JSH_OTT
      pwd: jsh
      user: jsh_tto
      dbSid: oraclesb

```
여러 토플로지 등록 예시
```
monitor:
  mode: wide
topology:
  - name: sync1
    mode: normal
    target1:
      db: tibero
      ip: 192.168.1.187
      port: 4628
      id: tibero
      pwd: tmax
      user: jsh_ott
      dbSid: tibero7_2
    target2:
      db: oracle
      ip: 192.168.1.188
      port: 1525
      id: JSH_OTT
      pwd: jsh
      user: jsh_tto
      dbSid: oraclesb
  - name: sync2
    mode: normal
    target1:
      db: tibero
      ip: 192.168.1.187
      port: 4628
      id: tibero
      pwd: tmax
      user: jsh_ott
      dbSid: tibero7_2
    target2:
      db: oracle
      ip: 192.168.1.188
      port: 1525
      id: JSH_OTT
      pwd: jsh
      user: jsh_tto
      dbSid: oraclesb

```
## 기능 
* oracle 과 tibero 데이터 베이스 세션을 통해 prs_lct, scn, tsn 비교
* oracle tibero topology 설정 추가해 수행시 동적 화면 변화
* (tibero to tibero 는 개발중인상태)
