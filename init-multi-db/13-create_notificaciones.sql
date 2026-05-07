-- 1. Conexión a la base de datos del microservicio
\c notificaciones


-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS registro_envio;
DROP TABLE IF EXISTS mensaje;
DROP TABLE IF EXISTS plantilla_correo;


-- 3. Creación de tablas, relaciones, restricciones e índices


CREATE TABLE plantilla_correo (
    id_plantilla SERIAL PRIMARY KEY,
    codigo_evento VARCHAR(30) UNIQUE NOT NULL,
    asunto VARCHAR(100) NOT NULL,
    cuerpo_html VARCHAR(255) NOT NULL
);


CREATE TABLE mensaje (
    id_mensaje SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL, -- Referencia externa al MS 'usuarios'
    id_plantilla INTEGER NOT NULL,
    estado_mensaje VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mensaje_plantilla FOREIGN KEY (id_plantilla) REFERENCES plantilla_correo (id_plantilla) ON DELETE RESTRICT,
    CONSTRAINT chk_estado_mensaje CHECK (estado_mensaje IN ('PENDIENTE', 'PROCESANDO', 'ENVIADO', 'FALLIDO'))
);


-- Índices para que el worker de envíos (cron/scheduler) encuentre rápido los pendientes
CREATE INDEX idx_mensaje_estado ON mensaje(estado_mensaje);
CREATE INDEX idx_mensaje_usuario ON mensaje(id_usuario);


CREATE TABLE registro_envio (
    id_registro SERIAL PRIMARY KEY,
    id_mensaje INTEGER NOT NULL,
    proveedor_smtp VARCHAR(30) NOT NULL,
    intentos INTEGER NOT NULL DEFAULT 1,
    fecha_ultimo_intento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_registro_mensaje FOREIGN KEY (id_mensaje) REFERENCES mensaje (id_mensaje) ON DELETE CASCADE,
    CONSTRAINT chk_intentos_validos CHECK (intentos BETWEEN 1 AND 3)
);


-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)


-- PLANTILLA_CORREO: Textos predefinidos para los eventos del sistema
INSERT INTO plantilla_correo (codigo_evento, asunto, cuerpo_html) VALUES
('BIENVENIDA', '¡Bienvenido a PCHardware!', '<h1>Hola, gracias por registrarte.</h1>'),
('PEDIDO_PAGADO', 'Tu pago ha sido aprobado', '<h1>Recibimos tu pago. Preparando orden...</h1>'),
('ENVIO_DESPACHADO', 'Tu PC va en camino', '<h1>Tu pedido ha sido entregado al courier.</h1>'),
('ALERTA_SEGURIDAD', 'Intento de inicio de sesión', '<h1>Detectamos un acceso inusual.</h1>');


-- MENSAJE: Cola de notificaciones gatilladas por otros microservicios vía Kafka/RabbitMQ
INSERT INTO mensaje (id_usuario, id_plantilla, estado_mensaje) VALUES
(2, 1, 'ENVIADO'),      -- Caso base: Correo de bienvenida histórico
(2, 2, 'ENVIADO'),      -- Caso base: Confirmación de compra enviada con éxito
(4, 3, 'PENDIENTE'),    -- Caso base: Recién encolado, esperando al worker de correos
(5, 4, 'FALLIDO'),      -- Caso borde: Correo rebotó (posiblemente la cuenta "correo.raro" dada de baja)
(3, 2, 'PROCESANDO');   -- Caso base: Worker actualmente intentando enviar el correo


-- REGISTRO_ENVIO: Trazabilidad de los intentos a través del proveedor (ej. SendGrid, Amazon SES)
INSERT INTO registro_envio (id_mensaje, proveedor_smtp, intentos) VALUES
(1, 'AMAZON_SES', 1),   -- Caso base: Salió al primer intento
(2, 'AMAZON_SES', 1),   -- Caso base: Salió al primer intento
(4, 'SENDGRID', 3),     -- Caso borde: Alcanzó el máximo de 3 reintentos permitidos por el CHECK y falló definitivamente
(5, 'AMAZON_SES', 2);   -- Caso borde: Falló el primer intento por timeout, en su segundo intento actual
