@echo off
echo Descargando microservicios Spring Boot...
echo.
echo Descargando eureka.zip...
curl -o eureka.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=eureka&groupId=cl.pchardware&artifactId=cl-pchardware-eureka&name=tienda-eureka&description=servicio-eureka&packageName=cl.pchardware.eureka&packaging=jar&javaVersion=21&dependencies=cloud-eureka-server,devtools"
echo.
echo Descargando ms-usuarios.zip...
curl -o ms-usuarios.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-usuarios&groupId=cl.pchardware&artifactId=cl-pchardware-usuarios&name=tienda-usuarios&description=servicio-usuarios&packageName=cl.pchardware.usuarios&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-catalogo.zip...
curl -o ms-catalogo.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-catalogo&groupId=cl.pchardware&artifactId=cl-pchardware-catalogo&name=tienda-catalogo&description=servicio-catalogo&packageName=cl.pchardware.catalogo&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-stock.zip...
curl -o ms-stock.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-stock&groupId=cl.pchardware&artifactId=cl-pchardware-stock&name=tienda-stock&description=servicio-stock&packageName=cl.pchardware.stock&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-pedidos.zip...
curl -o ms-pedidos.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-pedidos&groupId=cl.pchardware&artifactId=cl-pchardware-pedidos&name=tienda-pedidos&description=servicio-pedidos&packageName=cl.pchardware.pedidos&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-pagos.zip...
curl -o ms-pagos.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-pagos&groupId=cl.pchardware&artifactId=cl-pchardware-pagos&name=tienda-pagos&description=servicio-pagos&packageName=cl.pchardware.pagos&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-envios.zip...
curl -o ms-envios.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-envios&groupId=cl.pchardware&artifactId=cl-pchardware-envios&name=tienda-envios&description=servicio-envios&packageName=cl.pchardware.envios&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-tasacion.zip...
curl -o ms-tasacion.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-tasacion&groupId=cl.pchardware&artifactId=cl-pchardware-tasacion&name=tienda-tasacion&description=servicio-tasacion&packageName=cl.pchardware.tasacion&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-garantias.zip...
curl -o ms-garantias.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-garantias&groupId=cl.pchardware&artifactId=cl-pchardware-garantias&name=tienda-garantias&description=servicio-garantias&packageName=cl.pchardware.garantias&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-devoluciones.zip...
curl -o ms-devoluciones.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-devoluciones&groupId=cl.pchardware&artifactId=cl-pchardware-devoluciones&name=tienda-devoluciones&description=servicio-devoluciones&packageName=cl.pchardware.devoluciones&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-soporte.zip...
curl -o ms-soporte.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-soporte&groupId=cl.pchardware&artifactId=cl-pchardware-soporte&name=tienda-soporte&description=servicio-soporte&packageName=cl.pchardware.soporte&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-armado.zip...
curl -o ms-armado.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-armado&groupId=cl.pchardware&artifactId=cl-pchardware-armado&name=tienda-armado&description=servicio-armado&packageName=cl.pchardware.armado&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-notificaciones.zip...
curl -o ms-notificaciones.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.14&baseDir=ms-notificaciones&groupId=cl.pchardware&artifactId=cl-pchardware-notificaciones&name=tienda-notificaciones&description=servicio-notificaciones&packageName=cl.pchardware.notificaciones&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descarga completada.
pause
