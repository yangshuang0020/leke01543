call mvn clean install

call rd ..\..\..\..\dist\examples\im\server /s /q
call xcopy target\dist\tio-examples-im-server-1.6.8.v20170329-RELEASE ..\..\..\..\dist\examples\im\server\ /s /e /q /y

