-- 1. Conexión a la base de datos del microservicio
\c soporte

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS encuesta_satisfaccion;
DROP TABLE IF EXISTS mensaje_ticket;
DROP TABLE IF EXISTS ticket_soporte;

-- 3. Creación de tablas, relaciones, restricciones e índices

CREATE TABLE ticket_soporte (
    id_ticket SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL, -- Referencia externa al microservicio 'usuarios'
    categoria VARCHAR(30) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'ABIERTO',
    fecha_apertura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_categoria_ticket CHECK (categoria IN ('PREVENTA', 'LOGISTICA', 'SOPORTE_TECNICO', 'RECLAMO')),
    CONSTRAINT chk_estado_ticket CHECK (estado IN ('ABIERTO', 'EN_PROGRESO', 'ESPERA_CLIENTE', 'CERRADO'))
);

-- Índices para que los agentes filtren rápidamente su bandeja de entrada por estado o tipo
CREATE INDEX idx_ticket_estado ON ticket_soporte(estado);
CREATE INDEX idx_ticket_usuario ON ticket_soporte(id_usuario);

CREATE TABLE mensaje_ticket (
    id_mensaje SERIAL PRIMARY KEY,
    id_ticket INTEGER NOT NULL,
    remitente VARCHAR(20) NOT NULL,
    contenido VARCHAR(500) NOT NULL,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mensaje_ticket FOREIGN KEY (id_ticket) REFERENCES ticket_soporte (id_ticket) ON DELETE CASCADE,
    CONSTRAINT chk_remitente CHECK (remitente IN ('CLIENTE', 'AGENTE', 'SISTEMA'))
);

-- Índice para cargar el hilo conversacional de un ticket específico de forma óptima
CREATE INDEX idx_mensaje_ticket ON mensaje_ticket(id_ticket);

CREATE TABLE encuesta_satisfaccion (
    id_encuesta SERIAL PRIMARY KEY,
    id_ticket INTEGER UNIQUE NOT NULL,
    estrellas INTEGER NOT NULL,
    comentario VARCHAR(255),
    fecha_respuesta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_encuesta_ticket FOREIGN KEY (id_ticket) REFERENCES ticket_soporte (id_ticket) ON DELETE CASCADE,
    CONSTRAINT chk_estrellas_validas CHECK (estrellas BETWEEN 1 AND 5)
);

-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)

-- TICKET_SOPORTE: Diferentes motivos de contacto y etapas de atención
INSERT INTO ticket_soporte (id_usuario, categoria, estado) VALUES
(2, 'PREVENTA', 'CERRADO'),           -- Caso base: Duda resuelta antes de comprar
(4, 'SOPORTE_TECNICO', 'EN_PROGRESO'),-- Caso base: Ayuda con instalación de drivers, en atención
(3, 'RECLAMO', 'ABIERTO'),            -- Caso borde: Cliente molesto, requiere primera respuesta urgente
(5, 'LOGISTICA', 'ESPERA_CLIENTE'),   -- Caso base: Agente pidió datos de dirección, esperando respuesta
(2, 'SOPORTE_TECNICO', 'CERRADO');    -- Caso base: Problema técnico solucionado satisfactoriamente

-- MENSAJE_TICKET: Hilos conversacionales de los tickets
INSERT INTO mensaje_ticket (id_ticket, remitente, contenido) VALUES
(1, 'CLIENTE', '¿Esta placa B550M es compatible con el Ryzen 7 5800X3D sin actualizar BIOS?'),
(1, 'AGENTE', 'Hola. Sí, las placas de nuestro lote actual ya vienen con BIOS actualizada. Saludos.'),
(1, 'SISTEMA', 'El ticket ha sido marcado como CERRADO por inactividad del cliente.'), -- Caso borde: Auto-cierre del sistema
(2, 'CLIENTE', 'Mi PC no reconoce la tarjeta de video nueva, da error código 43.'),
(2, 'AGENTE', 'Por favor, intenta desinstalar los drivers con DDU en modo seguro y reinstalar.'),
(3, 'CLIENTE', '¡Llevo 3 días esperando mi pedido y nadie me responde en redes sociales!'), -- Caso borde: Reclamo sin respuesta aún
(4, 'AGENTE', 'Para gestionar el desvío con el courier, necesitamos que nos confirme el número de departamento.');

-- ENCUESTA_SATISFACCION: Feedback de los clientes solo para tickets en estado CERRADO
INSERT INTO encuesta_satisfaccion (id_ticket, estrellas, comentario) VALUES
(1, 5, 'Respuesta rápida y precisa, me ayudó a decidir mi compra. ¡Gracias!'), -- Caso base: Cliente muy satisfecho
(5, 1, 'Se demoraron mucho en responder y el técnico fue poco amable.');       -- Caso borde: Pésima calificación (gatilla auditoría interna)
