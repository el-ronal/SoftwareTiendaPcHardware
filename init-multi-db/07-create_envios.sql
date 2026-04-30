-- 1. Conexión a la base de datos del microservicio
\c envios

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS despacho;
DROP TABLE IF EXISTS direccion_envio;
DROP TABLE IF EXISTS courier;

-- 3. Creación de tablas, relaciones, restricciones e índices

CREATE TABLE courier (
    id_courier SERIAL PRIMARY KEY,
    codigo VARCHAR(15) UNIQUE NOT NULL,
    nombre_empresa VARCHAR(50) NOT NULL,
    url_rastreo VARCHAR(255)
);

CREATE TABLE direccion_envio (
    id_direccion SERIAL PRIMARY KEY,
    id_pedido INTEGER UNIQUE NOT NULL, -- Referencia externa al microservicio 'pedidos'
    calle_numero VARCHAR(100) NOT NULL,
    comuna VARCHAR(50) NOT NULL,
    region VARCHAR(50) NOT NULL
);

-- Índice para agrupar logísticamente zonas de despacho recurrentes
CREATE INDEX idx_direccion_comuna ON direccion_envio(comuna);

CREATE TABLE despacho (
    id_despacho SERIAL PRIMARY KEY,
    id_direccion INTEGER NOT NULL,
    id_courier INTEGER NOT NULL,
    codigo_seguimiento VARCHAR(50) UNIQUE,
    estado_logistico VARCHAR(20) NOT NULL,
    CONSTRAINT fk_despacho_direccion FOREIGN KEY (id_direccion) REFERENCES direccion_envio (id_direccion) ON DELETE RESTRICT,
    CONSTRAINT fk_despacho_courier FOREIGN KEY (id_courier) REFERENCES courier (id_courier) ON DELETE RESTRICT,
    CONSTRAINT chk_estado_logistico CHECK (estado_logistico IN ('PREPARACION', 'TRANSITO', 'REPARTO', 'ENTREGADO', 'EXTRAVIADO'))
);

-- Índices vitales para el seguimiento en tiempo real del usuario
CREATE INDEX idx_despacho_estado ON despacho(estado_logistico);
CREATE INDEX idx_despacho_seguimiento ON despacho(codigo_seguimiento);

-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)

-- COURIER: Empresas de transporte operativas en el contexto nacional
INSERT INTO courier (codigo, nombre_empresa, url_rastreo) VALUES
('CHX', 'Chilexpress', 'https://www.chilexpress.cl/rastreo?ot='),
('STK', 'Starken', 'https://www.starken.cl/seguimiento?codigo='),
('BLX', 'Bluexpress', 'https://www.blue.cl/seguimiento/?os='),
('RETIRO', 'Retiro en Tienda Local', NULL); -- Caso borde: Entregas presenciales no tienen URL de rastreo

-- DIRECCION_ENVIO: Destinos físicos atados a un pedido ya pagado
INSERT INTO direccion_envio (id_pedido, calle_numero, comuna, region) VALUES
(1, 'Av. Vicuña Mackenna 4860', 'Macul', 'Metropolitana'),      -- Caso base: Dirección residencial o universitaria estándar
(2, 'Arturo Prat 123, Depto 402', 'Santiago Centro', 'Metropolitana'), -- Caso base: Dirección con complemento (departamento)
(3, 'Los Carrera 850', 'Concepción', 'Biobío'),                 -- Caso base: Envío a región
(4, 'Direccion Nula 0', 'Retiro Presencial', 'Metropolitana');  -- Caso borde: Estructura comodín para retiros físicos

-- DESPACHO: Cruce de la orden física, el transportista y el estado actual del paquete
INSERT INTO despacho (id_direccion, id_courier, codigo_seguimiento, estado_logistico) VALUES
(1, 1, '998877665544', 'ENTREGADO'),    -- Caso base: Ciclo de despacho completado exitosamente
(2, 2, 'STK-99001122', 'TRANSITO'),     -- Caso base: Paquete viajando entre centros de distribución
(3, 3, 'BLX-554433', 'EXTRAVIADO'),     -- Caso borde: Paquete perdido o siniestrado (gatilla proceso de garantía)
(4, 4, NULL, 'PREPARACION');            -- Caso borde: Retiro en tienda, NULL es válido porque no hay código de seguimiento
