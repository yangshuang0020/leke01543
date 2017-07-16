rem -Xms64m -Xmx2048m

@echo off
setlocal & pushd
set APP_ENTRY=org.tio.http.server.demo1.HttpServerDemo1Starter
set BASE=%~dp0
set CP=%BASE%\config;%BASE%\lib\*
java -Xverify:none -Xrunjdwp:transport=dt_socket,address=8888,suspend=n,server=y -XX:+HeapDumpOnOutOfMemoryError -Dtio.default.read.buffer.size=512 -XX:HeapDumpPath=c:/java-t-io-im-server-pid.hprof -cp "%CP%" %APP_ENTRY%
endlocal & popd
