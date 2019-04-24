# CentOS6 기본 설정

### 서버 시간 동기화
```
# time_sync.sh

#!/bin/bash
rdate -s time.bora.net && date && clock -r && clock -w > /dev/null 2>&1
```
- chmod 700 time_sync.sh
- /etc/cron.hourly 에 파일을 둠으로써 매 시간마다 자동으로 수행 되도록 한다.

### 계정 추가
- useradd -g wheel -d /home/webmaster webmaster
- passwd webmaster ---> 암호 설정
- 계정 생성시 기본적으로 /etc/skel 아래에 있는 파일들이 자동으로 생성된다.
- useradd 시 M 옵션을 주면 홈 디렉터리 생성 없이 계정이 생성된다.
  - useradd -M tomcat
  - passwd 설정을 하면 안된다.
  - 아예 로그인이 불가능 하도록 한다.
  - 특정 소프트웨어만 사용하는 계정으로 주로 사용한다.
```
vi /etc/passwd

생성한 계정에서
/sbin/nologin
```

### 계정 보안
- /root 디렉토리 접근 방지
  - vi /etc/ssh/sshd_config
```
PermitRootLogin no
```
- /etc/init.d/sshd restart
  - 다른 계정들이 /root에 접근 불가능

### 자동 로그아웃
- 계정 환경설정파일 읽는 순서
  - /etc/profile --> ~/.bash_profile --> ~/.bashrc --> /etc/bashrc
- vi /etc/profile
```
# 300 sec
export TMOUT=300
```

### history 포맷 설정
- vi /etc/profile
```
HISTTIMEFORMAT="%Y-%m-%d_%H:%M:%S [CMD]:"
```

### group 추가
- groupadd 그룹명
  - g 옵션 : 특정 GID 번호로 설정
  - r 옵션 : 0~1000번 사이로 GID를 자동으로 설정
- 삭제를 하고 싶으면 groupdel
- 속한 그룹을 보고 싶으면 groups 사용자명

