ECHO off
title fyzg-wyy-app-service
ECHO "checking config.ini..."

SET ROOT=%~dp0
SET CONFIG_FILE=%ROOT%config.ini
REM **从配置文件中读取内容**
FOR /F "tokens=1,2 delims==" %%i IN (%CONFIG_FILE%) DO (
 SET %%i=%%j
)
SET APP_NAME=%ARTIFACTID%-%VERSION%

IF "%APP_NAME%" == "" (
    ECHO "this config.ini is not exist，please check this config file."
    GOTO End
) ELSE (
    ECHO "checking JAVA_HOME config from checking config.ini..."
    GOTO Runserver
)

:Runserver
SET JAR_NAME=%APP_NAME%.jar
SET APP_CONFIG=-Dloader.path=.,resources,lib

ECHO "%RUN_JAVA%"
ECHO "Starting the %JAR_NAME%"

ECHO "java -Xss256k -Xmx256m -Dspring.cloud.config.profile=prod -jar %APP_CONFIG% %JAR_NAME%"
CD ..
start javaw -Xss256k -Xmx256m -Dspring.cloud.config.profile=prod -jar %APP_CONFIG% %JAR_NAME%
GOTO End

:End