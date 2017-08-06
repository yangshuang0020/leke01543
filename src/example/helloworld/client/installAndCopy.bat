call mvn clean install

call rd ..\..\..\..\dist\examples\helloworld\client /s /q
call xcopy target\dist\tio-examples-helloworld-client-1.7.3.v20170806-RELEASE ..\..\..\..\dist\examples\helloworld\client\ /s /e /q /y

