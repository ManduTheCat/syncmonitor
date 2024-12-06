# Prosync Monitor
프로싱크 모니터링을 위한 jdbc 기반 조회 프로그램입니다 

## 사용법 
```
$  cd /sdiske/ps1/jsh/tibero7/7.2.2/work/build_test
$  sh run_monitor.sh 
```
## 설정방법
config.yml 수정하여 설정 변경가능
```
database:
    tibero:
      ip: 티베로 IP
      port: 티베로PORT
      id: 접근유저
      pwd: 유저암호
      dbLink: T to o 디비링크명
      user1: 유저1
      user2: 유저2
      dbSid: tibero SID
    oracle: (오라클 미구현 
      ip: 오라클 ip ( 오라클 은 구현 되있지 않습니다.)
      port: 오라클 PORT
      id: 오라클 접근유저
      pwd: 오라클 유저 암호
      dbLink: o to t 디비링크명
      user1: 유저1 
      user2: 유저2
      dbSid: 오라클 SID
```
예시
```
database:
    tibero:
      ip: 192.168.1.188
      port: 4628
      id: tibero
      pwd: tmax
      dbLink: jsh_tto
      user1: jsh_tto
      user2: jsh_ott
      dbSid: tibero7_2
    oracle:
      ip: 192.168.1.213
      port: 1521
      id: JSH_OTT
      pwd: jsh
      dbLink: TLINK
      user1: jsh_ott
      user2: jsh_tto
      dbSid: KIW

```
## 기능 
* tibero 에 설정된 T to O 디비링크 통해 TSN 과 시간 조회기능
* Oracle 연결 지원예정