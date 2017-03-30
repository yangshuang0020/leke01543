call mvn clean install

call rd ..\..\..\..\dist\examples\showcase\client /s /q
call xcopy target\dist\tio-examples-showcase-client-1.6.8.v20170329-RELEASE ..\..\..\..\dist\examples\showcase\client\ /s /e /q /y

