-- 1. Conexión a la base de datos del microservicio
\c stock

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS movimiento;
DROP TABLE IF EXISTS inventario;
DROP TABLE IF EXISTS bodega;

-- 3. Creación de tablas, relaciones, restricciones e índices

CREATE TABLE bodega (
    id_bodega SERIAL PRIMARY KEY,
    codigo VARCHAR(15) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    CONSTRAINT chk_tipo_bodega CHECK (tipo IN ('NUEVOS', 'USADOS', 'MERMA'))
);

CREATE TABLE inventario (
    id_inventario SERIAL PRIMARY KEY,
    id_bodega INTEGER NOT NULL,
    sku_producto VARCHAR(30) NOT NULL, -- Referencia externa al microservicio 'catalogo'
    cantidad INTEGER NOT NULL,
    CONSTRAINT fk_inventario_bodega FOREIGN KEY (id_bodega) REFERENCES bodega (id_bodega) ON DELETE RESTRICT,
    CONSTRAINT uq_bodega_sku UNIQUE (id_bodega, sku_producto),
    CONSTRAINT chk_cantidad_valida CHECK (cantidad >= 0)
);

-- Índice para búsquedas rápidas de disponibilidad por producto (muy consultado por el frontend)
CREATE INDEX idx_inventario_sku ON inventario(sku_producto);

CREATE TABLE movimiento (
    id_movimiento SERIAL PRIMARY KEY,
    id_inventario INTEGER NOT NULL,
    tipo_movimiento VARCHAR(15) NOT NULL,
    cantidad_variacion INTEGER NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mov_inventario FOREIGN KEY (id_inventario) REFERENCES inventario (id_inventario) ON DELETE RESTRICT,
    CONSTRAINT chk_tipo_mov CHECK (tipo_movimiento IN ('ENTRADA', 'SALIDA', 'AJUSTE')),
    CONSTRAINT chk_variacion_no_cero CHECK (cantidad_variacion != 0)
);

-- Índice para trazar auditorías de movimientos de un registro de inventario específico
CREATE INDEX idx_movimiento_inventario ON movimiento(id_inventario);

-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)

-- BODEGAS: Centros lógicos de almacenamiento para segregar hardware nuevo, reacondicionado y defectuoso
INSERT INTO bodega (codigo, nombre, tipo) VALUES
('BOD-CEN-01', 'Bodega Central Santiago', 'NUEVOS'),      -- Caso base: Productos nuevos
('BOD-TAS-01', 'Centro de Tasaciones', 'USADOS'),         -- Caso base: Productos de segunda mano
('BOD-DEV-01', 'Cuarentena y Garantías', 'MERMA');        -- Caso borde: Productos defectuosos retenidos

-- INVENTARIO: Cruce físico de existencias. Se asume independencia del MS catálogo usando el SKU.
INSERT INTO inventario (id_bodega, sku_producto, cantidad) VALUES
(1, 'CPU-AMD-R75800X3D-NUEVO', 50),  -- Caso base: Stock normal
(1, 'GPU-NVD-RTX4090-NUEVO', 2),     -- Caso borde: Stock crítico (poco inventario de alto valor)
(2, 'MB-ASUS-B550M-USADO', 1),       -- Caso base: Stock unitario (típico en hardware usado)
(1, 'CPU-INT-I914900K-NUEVO', 0),    -- Caso borde: Producto sin stock (Agotado, permitido por el CHECK >= 0)
(3, 'GPU-NVD-RTX4090-NUEVO', 1);     -- Caso borde: Mismo SKU pero en bodega de merma (por devolución/fallo)

-- MOVIMIENTOS: Historial de transacciones de stock que justifican los números del inventario
INSERT INTO movimiento (id_inventario, tipo_movimiento, cantidad_variacion) VALUES
(1, 'ENTRADA', 100),                 -- Ingreso inicial de lote de procesadores
(1, 'SALIDA', -50),                  -- Ventas acumuladas de procesadores (reduce stock)
(2, 'ENTRADA', 2),                   -- Ingreso de tarjetas gráficas de gama alta
(3, 'ENTRADA', 1),                   -- Recepción de una placa madre usada tras tasación
(5, 'AJUSTE', 1);                    -- Movimiento administrativo aislando una gráfica defectuosa a merma
