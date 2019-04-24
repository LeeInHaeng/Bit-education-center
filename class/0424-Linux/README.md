# CentOS6 �⺻ ����

### ���� �ð� ����ȭ
```
# time_sync.sh

#!/bin/bash
rdate -s time.bora.net && date && clock -r && clock -w > /dev/null 2>&1
```
- chmod 700 time_sync.sh
- /etc/cron.hourly �� ������ �����ν� �� �ð����� �ڵ����� ���� �ǵ��� �Ѵ�.

### ���� �߰�
- useradd -g wheel -d /home/webmaster webmaster
- passwd webmaster ---> ��ȣ ����
- ���� ������ �⺻������ /etc/skel �Ʒ��� �ִ� ���ϵ��� �ڵ����� �����ȴ�.
- useradd �� M �ɼ��� �ָ� Ȩ ���͸� ���� ���� ������ �����ȴ�.
  - useradd -M tomcat
  - passwd ������ �ϸ� �ȵȴ�.
  - �ƿ� �α����� �Ұ��� �ϵ��� �Ѵ�.
  - Ư�� ����Ʈ��� ����ϴ� �������� �ַ� ����Ѵ�.
```
vi /etc/passwd

������ ��������
/sbin/nologin
```

### ���� ����
- /root ���丮 ���� ����
  - vi /etc/ssh/sshd_config
```
PermitRootLogin no
```
- /etc/init.d/sshd restart
  - �ٸ� �������� /root�� ���� �Ұ���

### �ڵ� �α׾ƿ�
- ���� ȯ�漳������ �д� ����
  - /etc/profile --> ~/.bash_profile --> ~/.bashrc --> /etc/bashrc
- vi /etc/profile
```
# 300 sec
export TMOUT=300
```

### history ���� ����
- vi /etc/profile
```
HISTTIMEFORMAT="%Y-%m-%d_%H:%M:%S [CMD]:"
```

### group �߰�
- groupadd �׷��
  - g �ɼ� : Ư�� GID ��ȣ�� ����
  - r �ɼ� : 0~1000�� ���̷� GID�� �ڵ����� ����
- ������ �ϰ� ������ groupdel
- ���� �׷��� ���� ������ groups ����ڸ�

