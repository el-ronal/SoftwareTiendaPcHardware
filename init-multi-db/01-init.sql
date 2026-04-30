-- ES FUNDAMENTAL EJECUTAR ESTE SCRIPT QUE PERMITE ELIMINAR LAS BASES DE DATOS
-- SI ES QUE EXISTEN, PARA LUEGO CREARLAS LIMPIAS SIN TABLAS Y DESDE CERO

SELECT 'CREATE DATABASE usuarios'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'usuarios') \gexec

SELECT 'CREATE DATABASE catalogo'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'catalogo') \gexec

SELECT 'CREATE DATABASE stock'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stock') \gexec

SELECT 'CREATE DATABASE pedidos'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stock') \gexec

SELECT 'CREATE DATABASE pagos'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stock') \gexec

SELECT 'CREATE DATABASE envios'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stock') \gexec

SELECT 'CREATE DATABASE tasacion'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stock') \gexec

SELECT 'CREATE DATABASE garantias'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stock') \gexec

SELECT 'CREATE DATABASE devoluciones'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stock') \gexec

SELECT 'CREATE DATABASE soporte'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stock') \gexec

SELECT 'CREATE DATABASE armado'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stock') \gexec

SELECT 'CREATE DATABASE notificaciones'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stock') \gexec

/* 
stock
pedido
pagos
envios
tasación
garantías
devoluciones
soporte
armado
notificaciones
*/