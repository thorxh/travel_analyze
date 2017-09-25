@echo off
set JAVA="%JAVA_HOME%"\bin\java
set HOME=%~dp0%..
set LIBS_HOME=%HOME%\libs
set CFG_HOME=%HOME%\config
set RESULT_HOME=%HOME%\result
set LOG_HOME=%HOME%\logs
echo on
@echo program is running, please wait
@call %JAVA% -jar %LIBS_HOME%\travel-analyze-0.0.1-SNAPSHOT-jar-with-dependencies.jar %RESULT_HOME% %CFG_HOME% >> %LOG_HOME%\travel.log 2>&1
@echo program done, you can read the logs at %LOG_HOME%\travel.log
@echo off
pause