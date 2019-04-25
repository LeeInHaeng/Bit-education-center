# CentOS6 ȯ�漳��

### ������ ���� ping ����
- vi /etc/sysctl.conf
```
# Do Not accept ICMP echo
net.ipv4.icmp_echo_ignore_all=1
```
- sysctl -p

### github ��ġ �� ����
- ������ ���̺귯�� ��ġ
```
yum install curl-devel expat-devel gettext-devel openssl-devel zlib-devel perl-devel
```
- github �ҽ� �ٿ�ε�
```
https://mirrors.edge.kernel.org/pub/software/scm/git/ ---> �ֽ� ���� ��ũ ������
wget https://mirrors.edge.kernel.org/pub/software/scm/git/git-2.9.5.tar.gz
```
- ������ �� ��ġ
```
tar xvfz git-2.9.5.tar.gz
cd git-2.9.5
make configure
# ��ġ ��� ����
./configure --prefix=/usr/local/cafe24/git2.9.5
make all doc info
make install install-doc install-html install-info
```
- cd /usr/local/cafe24
- ln -s git2.9.5 git

- ȯ�溯��(PATH) ����
  - vi /etc/profile
```
### git PATH
export PATH=$PATH:/usr/local/cafe24/git/bin
```
- git --version ���� Ȯ��
- git ���� ����
```
git config --global user.name "lih"
git config --global user.email "lih0420@naver.com"

git config --list
```
- git ����� �����
  - ��η� �̵� �� ����
  - /usr/local/cafe24/dowork
```
git init
```
- ���� Ŀ��
```
git add *.c
git commit -m 'init commit'
```

- ����Ʈ ����� �߰��ϱ�
```
git remote add origin https://github.com/LeeInHaeng/bitEdu-dowork.git
```
- push �ϱ�
```
git push origin master
```
- git ignore ���� �����
  - vi .gitignore
```
*.class
```
