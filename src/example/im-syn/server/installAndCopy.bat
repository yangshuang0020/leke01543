call mvn clean install

call rd ..\..\..\..\dist\examples\im-syn\server /s /q
call xcopy target\dist\tio-examples-im-syn-server-1.7.0.v20170420-RELEASE ..\..\..\..\dist\examples\im-syn\server\ /s /e /q /y

