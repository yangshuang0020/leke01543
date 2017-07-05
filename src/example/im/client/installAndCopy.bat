call mvn clean install

call rd ..\..\..\..\dist\examples\im\client /s /q
call xcopy target\dist\tio-examples-im-client-1.7.2.v20170705-RELEASE ..\..\..\..\dist\examples\im\client\ /s /e /q /y

