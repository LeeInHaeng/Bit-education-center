# VirtualBox CentOS6 환경

## NAT
- 공유기와 같은 역할로 IP를 변경
- 인터넷은 가능 하지만, 호스트와 가상 환경끼리의 통신이 불가능

## Bridge
- 호스트와 똑같이 공유기에서 IP를 부여받음
- 호스트와 같은 네트워크 영역이기 때문에 서로 통신이 가능하며, 공유기를 통해 밖으로도 통신 가능
  - 해당 환경 사용

## 파티션
- 사용자 레이아웃
```
/boot : 256
swap : 마운트X, 유형을 swap : 4096
/ : 나머지
```

## 네트워크 설정
- /etc/sysconfig/network-scripts
- ifcfg-eth0 에서 ONBOOT=yes
- service network restart

## 패키지 설치 및 업데이트
- yum repolist
- yum update
- 필요 패키지
```
yum install rdate -y
yum install gcc -y
yum install make -y
yum install wget -y
yum install gcc-c++ -y
yum install cmake -y
yum install net-tools -y
yum install bind-utils -y
yum install psmisc -y
```

## 환경변수 설정
- vi /etc/profile 맨 끝
```
export PATH=$PATH:새로운 path
```
- source /etc/profile 로 바로 적용
- 해당 환경 변수 아래에 실행 파일이 존재하면 실행 파일을 어느 경로에서든 실행할 수 있다.
