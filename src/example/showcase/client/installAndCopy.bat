call mvn clean install

call rd ..\..\..\..\dist\examples\showcase\client /s /q
call xcopy target\dist\tio-examples-showcase-client-1.7.2.v20170701-RELEASE ..\..\..\..\dist\examples\showcase\client\ /s /e /q /y

