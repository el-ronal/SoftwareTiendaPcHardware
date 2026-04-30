-- 1. Conexión a la base de datos del microservicio
\c garantias

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS resolucion;
DROP TABLE IF EXISTS inspeccion_tecnica;
DROP TABLE IF EXISTS ticket_garantia;

-- 3. Creación de tablas, relaciones, restricciones e índices

CREATE TABLE ticket_garantia (
    id_ticket SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL, -- Referencia externa al microservicio 'pedidos'
    sku_producto VARCHAR(30) NOT NULL, -- Referencia externa al microservicio 'catalogo'
    motivo_cliente VARCHAR(255) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    CONSTRAINT chk_estado_ticket CHECK (estado IN ('PENDIENTE', 'EN_TALLER', 'RESUELTO', 'CERRADO_SIN_EFECTO'))
);

-- Índices para que el cliente o soporte busquen tickets rápidamente por su orden de compra
CREATE INDEX idx_ticket_pedido ON ticket_garantia(id_pedido);
CREATE INDEX idx_ticket_estado ON ticket_garantia(estado);

CREATE TABLE inspeccion_tecnica (
    id_inspeccion SERIAL PRIMARY KEY,
    id_ticket INTEGER UNIQUE NOT NULL,
    id_tecnico INTEGER NOT NULL, -- Referencia externa al MS 'usuarios'
    aplica_garantia BOOLEAN NOT NULL,
    detalle_tecnico VARCHAR(255) NOT NULL,
    CONSTRAINT fk_inspeccion_ticket FOREIGN KEY (id_ticket) REFERENCES ticket_garantia (id_ticket) ON DELETE CASCADE
);

-- Índice para auditar la carga de trabajo y resoluciones de un técnico específico
CREATE INDEX idx_inspeccion_tecnico ON inspeccion_tecnica(id_tecnico);

CREATE TABLE resolucion (
    id_resolucion SERIAL PRIMARY KEY,
    id_inspeccion INTEGER UNIQUE NOT NULL,
    accion_tomada VARCHAR(30) NOT NULL,
    fecha_cierre TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_resolucion_inspeccion FOREIGN KEY (id_inspeccion) REFERENCES inspeccion_tecnica (id_inspeccion) ON DELETE CASCADE,
    CONSTRAINT chk_accion_resolucion CHECK (accion_tomada IN ('REEMBOLSO_DINERO', 'CAMBIO_NUEVO', 'REPARACION', 'DEVOLUCION_RECHAZADA'))
);

-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)

-- TICKET_GARANTIA: Reclamos iniciales ingresados por los clientes
INSERT INTO ticket_garantia (id_pedido, sku_producto, motivo_cliente, estado) VALUES
(1, 'CPU-AMD-R75800X3D-NUEVO', 'El PC da pantallazos azules por temperatura, parece falla de fábrica.', 'RESUELTO'),     -- Caso base: Falla real de fábrica
(2, 'GPU-NVD-RTX4090-NUEVO', 'Huele a quemado y no da video.', 'RESUELTO'),                                            -- Caso borde: Falla inducida por el usuario
(3, 'MB-ASUS-B550M-USADO', 'No reconoce el puerto M.2 principal.', 'EN_TALLER'),                                       -- Caso base: Producto usado en revisión
(1, 'CBL-ASUS-SATA-PROMO', 'Vino cortado por la mitad.', 'CERRADO_SIN_EFECTO'),                                        -- Caso borde: Producto de regalo ($0) no tiene garantía
(5, 'RAM-CORSAIR-16GB', 'Compré DDR4 pero mi placa es DDR5, me equivoqué.', 'PENDIENTE');                              -- Caso borde: Error de usuario (no es garantía, es retracto)

-- INSPECCION_TECNICA: Resultado de la revisión en el taller para los tickets procesados
INSERT INTO inspeccion_tecnica (id_ticket, id_tecnico, aplica_garantia, detalle_tecnico) VALUES
(1, 4, TRUE, 'Defecto en el IHS del procesador comprobado. Calienta a 95C en reposo. Aplica reemplazo.'),              -- Caso base: Garantía aprobada
(2, 4, FALSE, 'Conector 12VHPWR derretido por mala conexión del usuario. Daño físico anula garantía.'),                -- Caso borde: Rechazo por daño físico inducido
(3, 4, TRUE, 'Puerto M.2 con soldadura fría de fábrica. Se procede a reparar o cambiar.');                             -- Caso base: Falla en producto usado

-- RESOLUCION: Decisión final vinculante que gatilla acciones en otros microservicios (Stock, Pagos, etc.)
INSERT INTO resolucion (id_inspeccion, accion_tomada) VALUES
(1, 'CAMBIO_NUEVO'),             -- Caso base: Se autoriza sacar uno nuevo de bodega y enviar
(2, 'DEVOLUCION_RECHAZADA'),     -- Caso borde: Se devuelve el producto dañado al cliente sin reembolso
(3, 'REEMBOLSO_DINERO');         -- Caso base: No hay más stock del producto usado, se devuelve la plata
