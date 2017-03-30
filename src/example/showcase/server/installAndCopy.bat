call mvn clean install

call rd ..\..\..\..\dist\examples\showcase\server /s /q
call xcopy target\dist\tio-examples-showcase-server-1.6.8.v20170329-RELEASE ..\..\..\..\dist\examples\showcase\server\ /s /e /q /y

