call mvn clean install

call rd ..\..\..\..\dist\examples\helloworld\client /s /q
call xcopy target\dist\tio-examples-helloworld-client-1.6.8.v20170329-RELEASE ..\..\..\..\dist\examples\helloworld\client\ /s /e /q /y

