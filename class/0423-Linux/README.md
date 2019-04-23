# VirtualBox CentOS6 ȯ��

## NAT
- ������� ���� ���ҷ� IP�� ����
- ���ͳ��� ���� ������, ȣ��Ʈ�� ���� ȯ�波���� ����� �Ұ���

## Bridge
- ȣ��Ʈ�� �Ȱ��� �����⿡�� IP�� �ο�����
- ȣ��Ʈ�� ���� ��Ʈ��ũ �����̱� ������ ���� ����� �����ϸ�, �����⸦ ���� �����ε� ��� ����
  - �ش� ȯ�� ���

## ��Ƽ��
- ����� ���̾ƿ�
```
/boot : 256
swap : ����ƮX, ������ swap : 4096
/ : ������
```

## ��Ʈ��ũ ����
- /etc/sysconfig/network-scripts
- ifcfg-eth0 ���� ONBOOT=yes
- service network restart

## ��Ű�� ��ġ �� ������Ʈ
- yum repolist
- yum update
- �ʿ� ��Ű��
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

## ȯ�溯�� ����
- vi /etc/profile �� ��
```
export PATH=$PATH:���ο� path
```
- source /etc/profile �� �ٷ� ����
- �ش� ȯ�� ���� �Ʒ��� ���� ������ �����ϸ� ���� ������ ��� ��ο����� ������ �� �ִ�.
