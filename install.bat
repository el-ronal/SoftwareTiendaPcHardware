@echo off
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo.

REM Paso 1: Eliminar carpeta local de dependencias
echo Eliminando carpeta .m2 ...
rmdir /s /q %USERPROFILE%\.m2

REM Paso 2: Eliminar carpetas target de los proyectos
echo Eliminando carpetas target ...
rmdir /s /q C:\SoftwareTiendaPcHardware\eureka\target
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-usuarios\target 
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-catalogo\target
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-stock\target
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-pedido\target
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-pagos\target
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-envios\target
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-tasacion\target
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-garantias\target
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-devoluciones\target
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-soporte\target
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-armado\target
rmdir /s /q C:\SoftwareTiendaPcHardware\ms-notificaciones\target

REM Paso 3: Instalar todas las dependencias forzadamente
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -DskipTests

echo.
echo === PROCESO COMPLETADO ===
pause
