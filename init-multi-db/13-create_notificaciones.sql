-- 1. Conexión a la base de datos del microservicio
\c armado


-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS resultado_testing;
DROP TABLE IF EXISTS orden_ensamble;
DROP TABLE IF EXISTS tecnico_armado;


-- 3. Creación de tablas, relaciones, restricciones e índices


CREATE TABLE tecnico_armado (
    id_tecnico SERIAL PRIMARY KEY,
    id_usuario INTEGER UNIQUE NOT NULL, -- Referencia externa al MS 'usuarios'
    especialidad VARCHAR(30) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT chk_especialidad CHECK (especialidad IN ('ESTANDAR', 'REFRIGERACION_LIQUIDA', 'MINI_ITX', 'MODDING'))
);


CREATE TABLE orden_ensamble (
    id_orden SERIAL PRIMARY KEY,
    id_pedido INTEGER UNIQUE NOT NULL, -- Referencia externa al MS 'pedidos'
    id_tecnico INTEGER NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'EN_COLA',
    fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orden_tecnico FOREIGN KEY (id_tecnico) REFERENCES tecnico_armado (id_tecnico) ON DELETE RESTRICT,
    CONSTRAINT chk_estado_ensamble CHECK (estado IN ('EN_COLA', 'ENSAMBLANDO', 'TESTEANDO', 'FINALIZADO', 'CANCELADO'))
);


-- Índices para gestionar la carga de trabajo del taller
CREATE INDEX idx_orden_estado ON orden_ensamble(estado);
CREATE INDEX idx_orden_tecnico ON orden_ensamble(id_tecnico);


CREATE TABLE resultado_testing (
    id_resultado SERIAL PRIMARY KEY,
    id_orden INTEGER UNIQUE NOT NULL,
    temp_max_cpu INTEGER NOT NULL,
    puntaje_benchmark INTEGER,
    aprobado BOOLEAN NOT NULL,
    CONSTRAINT fk_resultado_orden FOREIGN KEY (id_orden) REFERENCES orden_ensamble (id_orden) ON DELETE CASCADE,
    CONSTRAINT chk_temperatura_cpu CHECK (temp_max_cpu BETWEEN 20 AND 110)
);


-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)


-- TECNICO_ARMADO: Personal capacitado para ensamblar los equipos
INSERT INTO tecnico_armado (id_usuario, especialidad, activo) VALUES
(4, 'ESTANDAR', TRUE), -- Caso base: Técnico para armados tradicionales
(6, 'REFRIGERACION_LIQUIDA', TRUE), -- Caso base: Especialista en custom loops
(7, 'MINI_ITX', FALSE); -- Caso borde: Técnico inactivo (vacaciones/licencia)


-- ORDEN_ENSAMBLE: Solicitudes de armado vinculadas a pedidos pagados
INSERT INTO orden_ensamble (id_pedido, id_tecnico, estado) VALUES
(1, 1, 'FINALIZADO'), -- Caso base: PC armado y entregado
(2, 2, 'TESTEANDO'), -- Caso base: PC de gama alta con refrigeración líquida en pruebas de estrés
(3, 1, 'EN_COLA'), -- Caso base: Esperando turno en el mesón de trabajo
(4, 2, 'CANCELADO'); -- Caso borde: Cliente se arrepintió del servicio de armado antes de empezar


-- RESULTADO_TESTING: Métricas de las pruebas de estrés (FurMark, Cinebench, etc.)
INSERT INTO resultado_testing (id_orden, temp_max_cpu, puntaje_benchmark, aprobado) VALUES
(1, 75, 14500, TRUE), -- Caso base: Temperaturas normales, aprueba control de calidad
(2, 98, 32000, FALSE); -- Caso borde: PC de gama alta con thermal throttling, reprueba testing y vuelve a ensamblaje
