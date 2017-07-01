call mvn clean install

call rd ..\..\..\..\dist\examples\helloworld\server /s /q
call xcopy target\dist\tio-examples-helloworld-server-1.7.2.v20170701-RELEASE ..\..\..\..\dist\examples\helloworld\server\ /s /e /q /y

