-- 1. Conexión a la base de datos del microservicio
\c tasacion

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS oferta_compra;
DROP TABLE IF EXISTS evaluacion_tecnica;
DROP TABLE IF EXISTS solicitud_tasacion;

-- 3. Creación de tablas, relaciones, restricciones e índices

CREATE TABLE solicitud_tasacion (
    id_solicitud SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL, -- Referencia externa al microservicio 'usuarios' (cliente)
    hardware_descripcion VARCHAR(255) NOT NULL,
    estado_solicitud VARCHAR(20) NOT NULL,
    fecha_ingreso TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_estado_solicitud CHECK (estado_solicitud IN ('PENDIENTE', 'EN_REVISION', 'TASADO', 'RECHAZADO'))
);

-- Índices para listar rápidamente las solicitudes de un cliente o filtrar por cola de trabajo
CREATE INDEX idx_solicitud_usuario ON solicitud_tasacion(id_usuario);
CREATE INDEX idx_solicitud_estado ON solicitud_tasacion(estado_solicitud);

CREATE TABLE evaluacion_tecnica (
    id_evaluacion SERIAL PRIMARY KEY,
    id_solicitud INTEGER UNIQUE NOT NULL,
    id_tasador INTEGER NOT NULL, -- Referencia externa al MS 'usuarios' (empleado con rol TASADOR)
    puntaje_condicion INTEGER NOT NULL,
    observaciones VARCHAR(255),
    CONSTRAINT fk_evaluacion_solicitud FOREIGN KEY (id_solicitud) REFERENCES solicitud_tasacion (id_solicitud) ON DELETE CASCADE,
    CONSTRAINT chk_puntaje_valido CHECK (puntaje_condicion BETWEEN 1 AND 10)
);

-- Índice para auditar qué empleado evaluó qué hardware
CREATE INDEX idx_evaluacion_tasador ON evaluacion_tecnica(id_tasador);

CREATE TABLE oferta_compra (
    id_oferta SERIAL PRIMARY KEY,
    id_evaluacion INTEGER UNIQUE NOT NULL,
    monto_ofrecido_clp INTEGER NOT NULL,
    estado_oferta VARCHAR(20) NOT NULL,
    fecha_emision TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_oferta_evaluacion FOREIGN KEY (id_evaluacion) REFERENCES evaluacion_tecnica (id_evaluacion) ON DELETE CASCADE,
    CONSTRAINT chk_monto_oferta CHECK (monto_ofrecido_clp >= 0),
    CONSTRAINT chk_estado_oferta CHECK (estado_oferta IN ('ENVIADA', 'ACEPTADA', 'RECHAZADA_CLIENTE', 'CADUCADA'))
);

-- Índice para monitorear el estado de las propuestas económicas
CREATE INDEX idx_oferta_estado ON oferta_compra(estado_oferta);

-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)

-- SOLICITUD_TASACION: Diferentes etapas del hardware enviado por clientes
INSERT INTO solicitud_tasacion (id_usuario, hardware_descripcion, estado_solicitud) VALUES
(2, 'GPU NVIDIA RTX 3070 Founders Edition, sin caja', 'TASADO'),       -- Caso base: Proceso completo
(5, 'Placa Madre ASUS B450, pines doblados', 'RECHAZADO'),             -- Caso borde: Rechazo directo sin oferta
(3, 'Procesador AMD Ryzen 5 5600X, uso normal', 'EN_REVISION'),        -- Caso base: Actualmente en mesón técnico
(2, 'Lote de 5 fuentes de poder genéricas quemadas', 'TASADO'),        -- Caso borde: Basura electrónica (se tasa a costo 0)
(4, 'RAM Kingston Fury 16GB DDR4', 'PENDIENTE');                       -- Caso base: Recién ingresado por el usuario

-- EVALUACION_TECNICA: Resultado de los benchmarks y pruebas físicas (solo para los que pasaron PENDIENTE)
INSERT INTO evaluacion_tecnica (id_solicitud, id_tasador, puntaje_condicion, observaciones) VALUES
(1, 4, 8, 'Pasta térmica seca, leves marcas de polvo. Funciona perfecto en FurMark.'), -- Caso base: Buen estado general
(2, 4, 1, 'Pines del socket irreparables, corto en VRM. No apto para compra.'),        -- Caso borde: Hardware destruido (puntaje mínimo)
(3, 4, 9, 'Temperaturas estables, sin daño físico aparente. En pruebas de estrés.'),   -- Caso base: Revisión en curso
(4, 4, 2, 'Totalmente inservibles. Se aceptan solo para reciclaje electrónico.');      -- Caso borde: Puntaje muy bajo por daño total

-- OFERTA_COMPRA: Propuestas económicas enviadas al cliente según la evaluación
INSERT INTO oferta_compra (id_evaluacion, monto_ofrecido_clp, estado_oferta) VALUES
(1, 150000, 'ACEPTADA'),          -- Caso base: El cliente aceptó el dinero por su GPU usada
(2, 0, 'RECHAZADA_CLIENTE'),      -- Caso borde: Se ofreció $0 por estar destruido, el cliente se ofendió y rechazó
(4, 0, 'ACEPTADA');               -- Caso borde: Hardware quemado, cliente aceptó cederlo por $0 para reciclaje
