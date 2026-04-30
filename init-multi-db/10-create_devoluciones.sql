-- 1. Conexión a la base de datos del microservicio
\c devoluciones

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS nota_credito;
DROP TABLE IF EXISTS recepcion_logistica;
DROP TABLE IF EXISTS solicitud_devolucion;

-- 3. Creación de tablas, relaciones, restricciones e índices

CREATE TABLE solicitud_devolucion (
    id_devolucion SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL, -- Referencia externa al microservicio 'pedidos'
    motivo VARCHAR(25) NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_motivo_dev CHECK (motivo IN ('ARREPENTIMIENTO', 'ERROR_COMPRA', 'RECHAZO_COURIER')),
    CONSTRAINT chk_estado_dev CHECK (estado IN ('PENDIENTE', 'EN_TRANSITO', 'RECIBIDA', 'FINALIZADA', 'RECHAZADA'))
);

-- Índices para agilizar las vistas del servicio al cliente y el cliente final
CREATE INDEX idx_devolucion_pedido ON solicitud_devolucion(id_pedido);
CREATE INDEX idx_devolucion_estado ON solicitud_devolucion(estado);

CREATE TABLE recepcion_logistica (
    id_recepcion SERIAL PRIMARY KEY,
    id_devolucion INTEGER UNIQUE NOT NULL,
    estado_caja VARCHAR(20) NOT NULL,
    apto_reventa BOOLEAN NOT NULL,
    CONSTRAINT fk_recepcion_devolucion FOREIGN KEY (id_devolucion) REFERENCES solicitud_devolucion (id_devolucion) ON DELETE CASCADE,
    CONSTRAINT chk_estado_caja CHECK (estado_caja IN ('SELLADA', 'ABIERTA_INTACTA', 'DAÑADA', 'INCOMPLETA'))
);

-- Índice para filtrar rápidamente qué recepciones requieren revisión manual extra
CREATE INDEX idx_recepcion_caja ON recepcion_logistica(estado_caja);

CREATE TABLE nota_credito (
    id_nota SERIAL PRIMARY KEY,
    id_recepcion INTEGER UNIQUE NOT NULL,
    monto_clp INTEGER NOT NULL,
    estado_sii VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE_SII',
    CONSTRAINT fk_nota_recepcion FOREIGN KEY (id_recepcion) REFERENCES recepcion_logistica (id_recepcion) ON DELETE RESTRICT,
    CONSTRAINT chk_monto_nc CHECK (monto_clp >= 0),
    CONSTRAINT chk_estado_sii CHECK (estado_sii IN ('EMITIDA', 'PENDIENTE_SII', 'ANULADA'))
);

-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)

-- SOLICITUD_DEVOLUCION: Derecho a retracto legal (10 días) o rechazos logísticos
INSERT INTO solicitud_devolucion (id_pedido, motivo, estado) VALUES
(1, 'ARREPENTIMIENTO', 'FINALIZADA'),    -- Caso base: Retracto legal normal, proceso terminado
(2, 'ERROR_COMPRA', 'RECIBIDA'),         -- Caso base: Compró placa Intel en vez de AMD, llegó a bodega
(3, 'RECHAZO_COURIER', 'EN_TRANSITO'),   -- Caso borde: Cliente nunca recibió, el courier lo devuelve
(4, 'ERROR_COMPRA', 'RECHAZADA'),        -- Caso borde: Cliente mandó ladrillos en vez del producto
(5, 'ARREPENTIMIENTO', 'PENDIENTE');     -- Caso base: Solicitud recién creada por el cliente

-- RECEPCION_LOGISTICA: Inspección física al llegar el paquete a la bodega
INSERT INTO recepcion_logistica (id_devolucion, estado_caja, apto_reventa) VALUES
(1, 'SELLADA', TRUE),            -- Caso base: Cliente no la abrió, vuelve directo a stock como NUEVO
(2, 'ABIERTA_INTACTA', TRUE),    -- Caso base: La abrió pero componentes están perfectos, vuelve como USADO/OPEN BOX
(3, 'SELLADA', TRUE),            -- Caso borde: Devuelto por courier, caja intacta
(4, 'INCOMPLETA', FALSE);        -- Caso borde: Falta el procesador en la caja. Se rechaza la devolución.

-- NOTA_CREDITO: Documento tributario (NC) que anula la boleta/factura original para reembolsar
INSERT INTO nota_credito (id_recepcion, monto_clp, estado_sii) VALUES
(1, 320000, 'EMITIDA'),          -- Caso base: NC validada por el SII y lista para gatillar reembolso en MS Pagos
(2, 75000, 'PENDIENTE_SII'),     -- Caso base: NC generada pero esperando timbre electrónico del SII
(3, 15000, 'EMITIDA'),           -- Caso base: NC por el valor del pedido rechazado por el courier
(4, 0, 'ANULADA');               -- Caso borde: Emisión anulada porque la recepción logística detectó fraude (caja incompleta)
