-- 1. Conexión a la base de datos del microservicio
\c catalogo

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS categoria;
DROP TABLE IF EXISTS marca;

-- 3. Creación de tablas, relaciones, restricciones e índices

CREATE TABLE marca (
    id_marca SERIAL PRIMARY KEY,
    codigo VARCHAR(15) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE categoria (
    id_categoria SERIAL PRIMARY KEY,
    slug VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE producto (
    id_producto SERIAL PRIMARY KEY,
    id_marca INTEGER NOT NULL,
    id_categoria INTEGER NOT NULL,
    sku VARCHAR(30) UNIQUE NOT NULL,
    precio_clp INTEGER NOT NULL,
    CONSTRAINT fk_producto_marca FOREIGN KEY (id_marca) REFERENCES marca (id_marca) ON DELETE RESTRICT,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria) REFERENCES categoria (id_categoria) ON DELETE RESTRICT,
    CONSTRAINT chk_precio_valido CHECK (precio_clp >= 0)
);

-- Índices para optimizar búsquedas por llaves foráneas (filtros del catálogo)
CREATE INDEX idx_producto_marca ON producto(id_marca);
CREATE INDEX idx_producto_categoria ON producto(id_categoria);

-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)

-- MARCAS: Fabricantes reales de hardware
INSERT INTO marca (codigo, nombre) VALUES
('AMD', 'Advanced Micro Devices'),
('NVIDIA', 'NVIDIA Corporation'),
('ASUS', 'ASUSTeK Computer Inc.'),
('INTEL', 'Intel Corporation');

-- CATEGORIAS: Agrupaciones lógicas con "slugs" aptos para URLs del frontend
INSERT INTO categoria (slug, nombre) VALUES
('procesadores-cpu', 'Procesadores'),
('tarjetas-de-video', 'Tarjetas de Video (GPU)'),
('placas-madre', 'Placas Madre (Motherboards)'),
('accesorios', 'Cables y Accesorios');

-- PRODUCTOS: Cubre casos normales, de alto valor, artículos usados/reacondicionados y promociones
INSERT INTO producto (id_marca, id_categoria, sku, precio_clp) VALUES
(1, 1, 'CPU-AMD-R75800X3D-NUEVO', 320000),   -- Caso base: Componente estándar muy cotizado
(2, 2, 'GPU-NVD-RTX4090-NUEVO', 1950000),  -- Caso borde: Producto de muy alto valor transaccional
(3, 3, 'MB-ASUS-B550M-USADO', 75000),      -- Caso base: Producto de segunda mano (identificado en SKU)
(4, 1, 'CPU-INT-I914900K-NUEVO', 650000),  -- Caso base: Procesador gama alta
(3, 4, 'CBL-ASUS-SATA-PROMO', 0);          -- Caso borde: Accesorio de regalo por promoción (precio 0 permitido)
