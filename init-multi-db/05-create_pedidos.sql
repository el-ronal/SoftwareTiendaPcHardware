-- 1. Conexión a la base de datos del microservicio
\c pedidos

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS historial_estado;
DROP TABLE IF EXISTS detalle_pedido;
DROP TABLE IF EXISTS pedido;

-- 3. Creación de tablas, relaciones, restricciones e índices

CREATE TABLE pedido (
    id_pedido SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL, -- Referencia externa al microservicio 'usuarios'
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) NOT NULL,
    total_clp INTEGER NOT NULL,
    CONSTRAINT chk_estado_pedido CHECK (estado IN ('PENDIENTE', 'PAGADO', 'ENVIADO', 'ENTREGADO', 'CANCELADO')),
    CONSTRAINT chk_total_valido CHECK (total_clp >= 0)
);

-- Índices para optimizar la búsqueda de pedidos por cliente y por estado de flujo
CREATE INDEX idx_pedido_usuario ON pedido(id_usuario);
CREATE INDEX idx_pedido_estado ON pedido(estado);

CREATE TABLE detalle_pedido (
    id_detalle SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL,
    sku_producto VARCHAR(30) NOT NULL, -- Referencia externa al microservicio 'catalogo/stock'
    cantidad INTEGER NOT NULL,
    precio_unitario INTEGER NOT NULL,
    CONSTRAINT fk_detalle_pedido FOREIGN KEY (id_pedido) REFERENCES pedido (id_pedido) ON DELETE CASCADE,
    CONSTRAINT uq_pedido_sku UNIQUE (id_pedido, sku_producto), -- Evita duplicar líneas del mismo producto
    CONSTRAINT chk_cantidad_positiva CHECK (cantidad > 0),
    CONSTRAINT chk_precio_positivo CHECK (precio_unitario >= 0)
);

-- Índice para consultar qué pedidos contienen un producto específico (útil para retiros o garantías)
CREATE INDEX idx_detalle_sku ON detalle_pedido(sku_producto);

CREATE TABLE historial_estado (
    id_historial SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL,
    estado_anterior VARCHAR(20),
    estado_nuevo VARCHAR(20) NOT NULL,
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_historial_pedido FOREIGN KEY (id_pedido) REFERENCES pedido (id_pedido) ON DELETE CASCADE
);

-- Índice para reconstruir la línea de tiempo de un pedido específico
CREATE INDEX idx_historial_pedido ON historial_estado(id_pedido);

-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)

-- PEDIDOS: Cubre diferentes estados de ciclo de vida y usuarios compradores
INSERT INTO pedido (id_usuario, estado, total_clp) VALUES
(2, 'ENTREGADO', 320000),   -- Caso base: Pedido histórico completado exitosamente
(2, 'PAGADO', 1950000),     -- Caso borde: Pedido de muy alto valor, listo para despacho
(4, 'PENDIENTE', 75000),    -- Caso base: Carrito convertido a pedido pero esperando transferencia
(5, 'CANCELADO', 0);        -- Caso borde: Pedido anulado (ej. promoción 100% descuento fallida o timeout de pago)

-- DETALLE_PEDIDO: Cruza con los SKU definidos en el microservicio de catálogo
INSERT INTO detalle_pedido (id_pedido, sku_producto, cantidad, precio_unitario) VALUES
(1, 'CPU-AMD-R75800X3D-NUEVO', 1, 320000), -- Compra de procesador estándar
(2, 'GPU-NVD-RTX4090-NUEVO', 1, 1950000),  -- Compra de gráfica gama alta
(3, 'MB-ASUS-B550M-USADO', 1, 75000),      -- Compra de repuesto reacondicionado
(4, 'CBL-ASUS-SATA-PROMO', 2, 0);          -- Caso borde: Cantidad múltiple pero con precio 0 (regalo promocional)

-- HISTORIAL_ESTADO: Trazabilidad de los saltos de estado para auditoría y visualización del cliente
INSERT INTO historial_estado (id_pedido, estado_anterior, estado_nuevo) VALUES
(1, NULL, 'PENDIENTE'),              -- Creación inicial
(1, 'PENDIENTE', 'PAGADO'),          -- Pago confirmado
(1, 'PAGADO', 'ENVIADO'),            -- Entregado al courier
(1, 'ENVIADO', 'ENTREGADO'),         -- Recepción final por el cliente
(2, NULL, 'PENDIENTE'),              -- Creación inicial del pedido caro
(2, 'PENDIENTE', 'PAGADO'),          -- Pago de alto monto validado
(3, NULL, 'PENDIENTE'),              -- Pedido esperando pago
(4, NULL, 'PENDIENTE'),              -- Creación de pedido promo
(4, 'PENDIENTE', 'CANCELADO');       -- Anulación por sistema (ej. fin de stock promocional)
