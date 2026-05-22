-- 1. Conexión a la base de datos del microservicio
\c pagos

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS reembolso;
DROP TABLE IF EXISTS transaccion;
DROP TABLE IF EXISTS metodo_pago;

-- 3. Creación de tablas, relaciones, restricciones e índices

CREATE TABLE metodo_pago (
    id_metodo SERIAL PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE transaccion (
    id_transaccion SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL, -- Referencia externa al microservicio 'pedidos'
    id_metodo INTEGER NOT NULL,
    monto_clp INTEGER NOT NULL,
    estado VARCHAR(20) NOT NULL,
    CONSTRAINT fk_transaccion_metodo FOREIGN KEY (id_metodo) REFERENCES metodo_pago (id_metodo) ON DELETE RESTRICT,
    CONSTRAINT chk_monto_valido CHECK (monto_clp >= 0),
    CONSTRAINT chk_estado_trx CHECK (estado IN ('PENDIENTE', 'APROBADA', 'RECHAZADA', 'REEMBOLSADA'))
);

-- Índices para optimizar la conciliación de pagos y búsquedas por pedido
CREATE INDEX idx_transaccion_pedido ON transaccion(id_pedido);
CREATE INDEX idx_transaccion_estado ON transaccion(estado);

CREATE TABLE reembolso (
    id_reembolso SERIAL PRIMARY KEY,
    id_transaccion INTEGER UNIQUE NOT NULL,
    monto_devolucion INTEGER NOT NULL,
    motivo VARCHAR(100) NOT NULL,
    fecha_proceso TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reembolso_transaccion FOREIGN KEY (id_transaccion) REFERENCES transaccion (id_transaccion) ON DELETE CASCADE,
    CONSTRAINT chk_monto_reembolso CHECK (monto_devolucion > 0)
);

-- Índice para auditoría de devoluciones financieras
CREATE INDEX idx_reembolso_fecha ON reembolso(fecha_proceso);

-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)

-- METODOS DE PAGO: Pasarelas disponibles en el ecommerce chileno
INSERT INTO metodo_pago (codigo, nombre, activo) VALUES
('WEBPAY_PLUS', 'Tarjetas de Crédito y Débito (Transbank)', TRUE), -- Caso base: Pasarela principal
('MERCADOPAGO', 'Billetera MercadoPago', TRUE),                    -- Caso base: Pasarela secundaria
('TRANSFERENCIA', 'Transferencia Bancaria Manual', TRUE),          -- Caso base: Pago offline
('KIPU', 'Pasarela Kipu', FALSE);                                  -- Caso borde: Método deshabilitado/obsoleto

-- TRANSACCIONES: Cubre pagos exitosos, fallidos, pendientes y montos cero
INSERT INTO transaccion (id_pedido, id_metodo, monto_clp, estado) VALUES
(1, 1, 320000, 'APROBADA'),     -- Caso base: Pago exitoso con Webpay
(2, 3, 1950000, 'APROBADA'),    -- Caso borde: Pago de alto valor mediante transferencia
(3, 3, 75000, 'PENDIENTE'),     -- Caso base: Esperando validación del comprobante de transferencia
(4, 2, 0, 'APROBADA'),          -- Caso borde: Transacción válida de $0 (pedido cubierto 100% por promoción)
(5, 1, 150000, 'RECHAZADA'),    -- Caso borde: Tarjeta sin fondos (Rechazo de pasarela)
(6, 2, 45000, 'REEMBOLSADA');   -- Caso base: Pago original que posteriormente fue devuelto

-- REEMBOLSOS: Ejecuciones de devoluciones de dinero por garantías o cancelaciones
INSERT INTO reembolso (id_transaccion, monto_devolucion, motivo) VALUES
(6, 45000, 'Devolución por garantía - Componente defectuoso'); -- Caso base: Devolución total vinculada a la trx 6
