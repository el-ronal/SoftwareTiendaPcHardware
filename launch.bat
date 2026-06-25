@echo off
setlocal

:MENU
cls
echo.
echo ============================================
echo   Tienda - MENU PRINCIPAL
echo ============================================
echo.
echo   [1] Iniciar todos los servicios (dev)
echo   [2] Iniciar todos los servicios (test)
echo   [3] Compilar microservicios
echo   [4] Reinstalar dependencias Maven
echo.
echo   --- Servicios individuales ---
echo   [5] Iniciar Eureka
echo   [6] Iniciar ms-usuarios

echo   [7] Iniciar ms-catalogo

echo   [8] Iniciar ms-stock

echo   [9] Iniciar ms-pedidos

echo   [10] Iniciar ms-pagos

echo   [11] Iniciar ms-envios

echo   [12] Iniciar ms-tasacion

echo   [13] Iniciar ms-garantias

echo   [14] Iniciar ms-devoluciones

echo   [15] Iniciar ms-soporte

echo   [16] Iniciar ms-armado

echo   [17] Iniciar ms-notificaciones

echo   [18] Iniciar API Gateway
echo.
echo   [0] Salir
echo.
echo ============================================
set /p opcion="  Selecciona una opcion: "

if "%opcion%"=="1" goto RUN_ALL
if "%opcion%"=="2" goto RUN_TEST
if "%opcion%"=="3" goto COMPILE
if "%opcion%"=="4" goto INSTALL
if "%opcion%"=="5" goto RUN_EUREKA
if "%opcion%"=="6" goto RUN_USUARIOS

if "%opcion%"=="7" goto RUN_CATALOGO

if "%opcion%"=="8" goto RUN_STOCK

if "%opcion%"=="9" goto RUN_PEDIDOS

if "%opcion%"=="10" goto RUN_PAGOS

if "%opcion%"=="11" goto RUN_ENVIOS

if "%opcion%"=="12" goto RUN_TASACION

if "%opcion%"=="13" goto RUN_GARANTIAS

if "%opcion%"=="14" goto RUN_DEVOLUCIONES

if "%opcion%"=="15" goto RUN_SOPORTE

if "%opcion%"=="16" goto RUN_ARMADO

if "%opcion%"=="17" goto RUN_NOTIFICACIONES
if "%opcion%"=="18" goto RUN_GATEWAY
if "%opcion%"=="0" goto SALIR

echo.
echo   Opcion invalida. Intenta de nuevo.
timeout /t 2 /nobreak > nul
goto MENU

REM ============================================

:RUN_ALL
cls
echo.
echo ===== Iniciando Eureka Server =====
start "EUREKA" mvn -f eureka spring-boot:run
timeout /t 5 /nobreak > nul
echo ===== Iniciando Microservicios =====
start "MS-USUARIOS" mvn -f ms-usuarios spring-boot:run

start "MS-CATALOGO" mvn -f ms-catalogo spring-boot:run

start "MS-STOCK" mvn -f ms-stock spring-boot:run

start "MS-PEDIDOS" mvn -f ms-pedidos spring-boot:run

start "MS-PAGOS" mvn -f ms-pagos spring-boot:run

start "MS-ENVIOS" mvn -f ms-envios spring-boot:run

start "MS-TASACION" mvn -f ms-tasacion spring-boot:run

start "MS-GARANTIAS" mvn -f ms-garantias spring-boot:run

start "MS-DEVOLUCIONES" mvn -f ms-devoluciones spring-boot:run

start "MS-SOPORTE" mvn -f ms-soporte spring-boot:run

start "MS-ARMADO" mvn -f ms-armado spring-boot:run

start "MS-NOTIFICACIONES" mvn -f ms-notificaciones spring-boot:run

rem [GATEWAY-INI] Se inicia el API Gateway en modo test
timeout /t 5 /nobreak > nul
echo ===== Iniciando API Gateway (test) =====
start "API-GATEWAY" java -jar api-gateway\\target\\cl-pchardware-gateway-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
rem [GATEWAY-FIN]
echo Todos los servicios han sido lanzados.
pause
goto MENU

:RUN_TEST
cls
echo.
echo ===== Iniciando Eureka Server (test) =====
start "EUREKA" java -jar eureka\target\cl-pchardware-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=test
timeout /t 5 /nobreak > nul
echo ===== Iniciando Microservicios (test) =====
start "MS-USUARIOS" java -jar ms-usuarios\\target\\cl-pchardware-usuarios-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-CATALOGO" java -jar ms-catalogo\\target\\cl-pchardware-catalogo-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-STOCK" java -jar ms-stock\\target\\cl-pchardware-stock-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-PEDIDOS" java -jar ms-pedidos\\target\\cl-pchardware-pedidos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-PAGOS" java -jar ms-pagos\\target\\cl-pchardware-pagos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-ENVIOS" java -jar ms-envios\\target\\cl-pchardware-envios-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-TASACION" java -jar ms-tasacion\\target\\cl-pchardware-tasacion-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-GARANTIAS" java -jar ms-garantias\\target\\cl-pchardware-garantias-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-DEVOLUCIONES" java -jar ms-devoluciones\\target\\cl-pchardware-devoluciones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-SOPORTE" java -jar ms-soporte\\target\\cl-pchardware-soporte-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-ARMADO" java -jar ms-armado\\target\\cl-pchardware-armado-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-NOTIFICACIONES" java -jar ms-notificaciones\\target\\cl-pchardware-notificaciones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
echo Todos los servicios han sido lanzados en modo test.
pause
goto MENU

:COMPILE
cls
echo.
echo ===== Compilando microservicios =====
cd /d C:\tienda\ms-usuarios

call mvn clean install -U

cd /d C:\tienda\ms-catalogo

call mvn clean install -U

cd /d C:\tienda\ms-stock

call mvn clean install -U

cd /d C:\tienda\ms-pedidos

call mvn clean install -U

cd /d C:\tienda\ms-pagos

call mvn clean install -U

cd /d C:\tienda\ms-envios

call mvn clean install -U

cd /d C:\tienda\ms-tasacion

call mvn clean install -U

cd /d C:\tienda\ms-garantias

call mvn clean install -U

cd /d C:\tienda\ms-devoluciones

call mvn clean install -U

cd /d C:\tienda\ms-soporte

call mvn clean install -U

cd /d C:\tienda\ms-armado

call mvn clean install -U

cd /d C:\tienda\ms-notificaciones

call mvn clean install -U
echo Compilacion completada.
pause
goto MENU

:INSTALL
cls
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo.
echo Eliminando carpeta .m2 ...
rmdir /s /q %USERPROFILE%\.m2
echo Eliminando carpetas target ...
rmdir /s /q C:\tienda\eureka\target

rmdir /s /q C:\tienda\ms-usuarios\target

rmdir /s /q C:\tienda\ms-catalogo\target

rmdir /s /q C:\tienda\ms-stock\target

rmdir /s /q C:\tienda\ms-pedidos\target

rmdir /s /q C:\tienda\ms-pagos\target

rmdir /s /q C:\tienda\ms-envios\target

rmdir /s /q C:\tienda\ms-tasacion\target

rmdir /s /q C:\tienda\ms-garantias\target

rmdir /s /q C:\tienda\ms-devoluciones\target

rmdir /s /q C:\tienda\ms-soporte\target

rmdir /s /q C:\tienda\ms-armado\target

rmdir /s /q C:\tienda\ms-notificaciones\target
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -DskipTests
echo.
echo === PROCESO COMPLETADO ===
pause
goto MENU

:RUN_EUREKA
cls
echo.
echo ===== Iniciando Eureka =====
start "EUREKA" mvn -f eureka spring-boot:run
echo Eureka iniciado.
pause
goto MENU

:RUN_USUARIOS

cls

echo.

echo ===== Iniciando ms-usuarios =====

start "MS-USUARIOS" mvn -f ms-usuarios spring-boot:run

echo ms-usuarios iniciado.

pause

goto MENU



:RUN_CATALOGO

cls

echo.

echo ===== Iniciando ms-catalogo =====

start "MS-CATALOGO" mvn -f ms-catalogo spring-boot:run

echo ms-catalogo iniciado.

pause

goto MENU



:RUN_STOCK

cls

echo.

echo ===== Iniciando ms-stock =====

start "MS-STOCK" mvn -f ms-stock spring-boot:run

echo ms-stock iniciado.

pause

goto MENU



:RUN_PEDIDOS

cls

echo.

echo ===== Iniciando ms-pedidos =====

start "MS-PEDIDOS" mvn -f ms-pedidos spring-boot:run

echo ms-pedidos iniciado.

pause

goto MENU



:RUN_PAGOS

cls

echo.

echo ===== Iniciando ms-pagos =====

start "MS-PAGOS" mvn -f ms-pagos spring-boot:run

echo ms-pagos iniciado.

pause

goto MENU



:RUN_ENVIOS

cls

echo.

echo ===== Iniciando ms-envios =====

start "MS-ENVIOS" mvn -f ms-envios spring-boot:run

echo ms-envios iniciado.

pause

goto MENU



:RUN_TASACION

cls

echo.

echo ===== Iniciando ms-tasacion =====

start "MS-TASACION" mvn -f ms-tasacion spring-boot:run

echo ms-tasacion iniciado.

pause

goto MENU



:RUN_GARANTIAS

cls

echo.

echo ===== Iniciando ms-garantias =====

start "MS-GARANTIAS" mvn -f ms-garantias spring-boot:run

echo ms-garantias iniciado.

pause

goto MENU



:RUN_DEVOLUCIONES

cls

echo.

echo ===== Iniciando ms-devoluciones =====

start "MS-DEVOLUCIONES" mvn -f ms-devoluciones spring-boot:run

echo ms-devoluciones iniciado.

pause

goto MENU



:RUN_SOPORTE

cls

echo.

echo ===== Iniciando ms-soporte =====

start "MS-SOPORTE" mvn -f ms-soporte spring-boot:run

echo ms-soporte iniciado.

pause

goto MENU



:RUN_ARMADO

cls

echo.

echo ===== Iniciando ms-armado =====

start "MS-ARMADO" mvn -f ms-armado spring-boot:run

echo ms-armado iniciado.

pause

goto MENU



:RUN_NOTIFICACIONES

cls

echo.

echo ===== Iniciando ms-notificaciones =====

start "MS-NOTIFICACIONES" mvn -f ms-notificaciones spring-boot:run

echo ms-notificaciones iniciado.

pause

rem [GATEWAY-INI] Seccion para iniciar el API Gateway de forma individual
:RUN_GATEWAY
cls
echo.
echo ===== Iniciando API Gateway =====
start "API-GATEWAY" mvn -f api-gateway spring-boot:run
echo API Gateway iniciado en puerto 9000.
pause
goto MENU
rem [GATEWAY-FIN]

:SALIR
cls
echo.
echo   Hasta luego.
echo.
endlocal
exit /b
