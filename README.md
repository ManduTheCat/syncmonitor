# Prosync Monitor
프로싱크 모니터링을 위한 jdbc 기반 조회 프로그램입니다 
oracle 과 tibero 데이터 베이스 세션을 통해 prs_lct, scn, tsn 비교 및 모니터링 수행 

## 사용법 
```
$  sh run_monitor.sh 
```
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
      id: 접속계정
      pwd: 접속 암호
      user: prs_lct 소유 스키마
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
예시
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
