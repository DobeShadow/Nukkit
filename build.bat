@echo off
echo ���� target Ŀ¼
rd /q /s "target\"
echo ������...
mkdir target
javadoc -locale zh_CN -charset utf-8 -encoding utf-8 -docencoding utf-8 -sourcepath "src/main/java" -d "target" -overview "src/main/javadoc/overview.html" -docfilessubdirs -use -doctitle "Nukkit �����ĵ�" -linkoffline -windowtitle "Nukkit �����ĵ�" -subpackages cn.nukkit
echo ���ɽ���
pause
