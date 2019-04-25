# CentOS6 환경설정

### 서버에 오는 ping 막기
- vi /etc/sysctl.conf
```
# Do Not accept ICMP echo
net.ipv4.icmp_echo_ignore_all=1
```
- sysctl -p

### github 설치 및 설정
- 의존성 라이브러리 설치
```
yum install curl-devel expat-devel gettext-devel openssl-devel zlib-devel perl-devel
```
- github 소스 다운로드
```
https://mirrors.edge.kernel.org/pub/software/scm/git/ ---> 최신 버전 링크 따오기
wget https://mirrors.edge.kernel.org/pub/software/scm/git/git-2.9.5.tar.gz
```
- 컴파일 및 설치
```
tar xvfz git-2.9.5.tar.gz
cd git-2.9.5
make configure
# 설치 경로 지정
./configure --prefix=/usr/local/cafe24/git2.9.5
make all doc info
make install install-doc install-html install-info
```
- cd /usr/local/cafe24
- ln -s git2.9.5 git

- 환경변수(PATH) 설정
  - vi /etc/profile
```
### git PATH
export PATH=$PATH:/usr/local/cafe24/git/bin
```
- git --version 으로 확인
- git 최초 설정
```
git config --global user.name "lih"
git config --global user.email "lih0420@naver.com"

git config --list
```
- git 저장소 만들기
  - 경로로 이동 후 진행
  - /usr/local/cafe24/dowork
```
git init
```
- 최초 커밋
```
git add *.c
git commit -m 'init commit'
```

- 리모트 저장소 추가하기
```
git remote add origin https://github.com/LeeInHaeng/bitEdu-dowork.git
```
- push 하기
```
git push origin master
```
- git ignore 파일 만들기
  - vi .gitignore
```
*.class
```
