call mvn clean install

call rd ..\..\..\..\dist\examples\im-syn\client /s /q
call xcopy target\dist\tio-examples-im-syn-client-1.7.0.v20170420-RELEASE ..\..\..\..\dist\examples\im-syn\client\ /s /e /q /y

