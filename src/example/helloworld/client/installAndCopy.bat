call mvn clean install

call rd ..\..\..\..\dist\examples\helloworld\client /s /q
call xcopy target\dist\tio-examples-helloworld-client-1.7.2.v20170705-RELEASE ..\..\..\..\dist\examples\helloworld\client\ /s /e /q /y

