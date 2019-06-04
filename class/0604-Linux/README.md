# 가상 파일 시스템 (VFS)
- 리눅스 커널의 특징 중 하나이며 유닉스에 비해 리눅스는 초창기부터 가상 파일 시스템을 지원했다.
  - 디스크에 있지 않은 가상으로 장치들에 접근하기 위한 경로
- 디스크, 터미널, 네트워크 카드 등 모든 주변 장치들을 파일로 취급한다. 
- 따라서 다양한 파일들을 관리하기 위해 다양한 파일 시스템이 존재하게 된다.
- 다양한 파일 시스템들을 하나의 파일 시스템 처럼 사용할 수 있도록 가상 파일 시스템이라는 구조를 사용한다.
- 모든 파일 시스템을 하나의 파일 시스템으로 보이게 하는 계층(layer)이라 할 수 있다.
- 파일, 디렉터리, 특수 파일 등을 파일 처리 시스템 호출(system call)을 통해 일관적으로 조작할 수 있다. 

### 주요 특수 파일 시스템
- procfs : 커널 및 커널 모듈(디바이스 드라이버) 정보를 참조하거나, 설정 변경을 위한 파일 시스템으로 /proc에 마운트
- sysfs : 시스템에 접속된 디바이스 정보를 참조하거나, 설정 변경을 위한 파일 시스템. /sys 에 마운트
- devfs : 물리 디바이스에 액세스하기 위한 디바이스 파일을 배치하는 파일 시스템 . /dev 에 마운트
  
### 리눅스 하드 디스크 설정
- 가상 머신에서 디스크 추가
- fdisk -l 로 파티션 확인 (기존의 sda에서 sdb가 새로 추가)
- 리눅스 명령어로 파티션 설정
  - fdisk /dev/sdb
  - Command : n ---> p
  - Partition number : 1
  - First cylinder (1-6266, default 1): 1 ---> 엔터
  - Command : w
- 포맷해서 하드 디스크를 사용할 수 있도록 한다.
  - mkfs -t ext4 /dev/sdb1
- 임시 마운트 : /usr/local/cafe24/mariadb/data 를 /dev/sdb1에 마운트
  - 기존의 data 디렉터리를 data2로 백업 후 mkdir data
  - mount /dev/sdb1 /usr/local/cafe24/mariadb/data
  - cp -R data2/* data
  - chown -R mysql:mysql data
  - 해당 마운트는 임시적 마운트
- 영구 마운트 : /etc/fstab 파일 편집
  - 파일 시스템 장치명 (file system device name)
  - 마운트 포인트 (mount point)
  - 파일 시스템 종류 (file system type) - ext, ext2, ext3, ext4, iso9660, hfs, nfs, ramdisk, swap
  - 마운트 옵션 (mount option) - auto(noauto), exec(noexec), suid(nosuid), rw(ro), nouser(user)  
  - 덤프 가능 여부 - 0(덤프 불가능), 1(덤프 가능)
  - 파일 시퀀스 체크 여부 - 0(무결성 검사를 하지 않음), 1(우선순위 1), 2(우선순위 2) 
  - 파일 시스템 장치의 uuid 확인 : ll /dev/disk/by-uuid
```
UUID=df36de50-7247-4b52-9f58-5e52c9996b9b /usr/local/cafe24/mariadb/data        ext4    defaults        1 2
```

