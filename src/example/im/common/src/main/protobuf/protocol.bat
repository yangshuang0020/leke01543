md .\src\c
md .\src\java

protoc.exe --cpp_out=.\src\c chat.proto

protoc.exe --java_out=.\src\java chat.proto