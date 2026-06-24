-- 1. Conexión a la base de datos del microservicio
\c usuarios

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS perfil;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS rol;

-- 3. Creación de tablas, relaciones, restricciones e índices

CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    nombre VARCHAR(30) UNIQUE NOT NULL,
    descripcion VARCHAR(100) NOT NULL
);

CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    id_rol INTEGER NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES rol (id_rol) ON DELETE RESTRICT,
    CONSTRAINT chk_email_formato CHECK (email ~* '^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+\.[A-Za-z]+$'),
    CONSTRAINT chk_estado_valido CHECK (estado IN ('ACTIVO', 'INACTIVO', 'BANEADO'))
);

-- Índice para búsquedas frecuentes por estado de cuenta
CREATE INDEX idx_usuario_estado ON usuario(estado);

CREATE TABLE perfil (
    id_perfil SERIAL PRIMARY KEY,
    id_usuario INTEGER UNIQUE NOT NULL,
    rut VARCHAR(12) UNIQUE NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    CONSTRAINT fk_perfil_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE
);

-- Índice para búsquedas rápidas por documento de identidad
CREATE INDEX idx_perfil_rut ON perfil(rut);

-- 4. Poblado de tablas con datos de prueba (Casos base y bordes)

-- ROLES: Cubre los perfiles lógicos de un ecommerce de hardware (incluyendo al tasador)
INSERT INTO rol (nombre, descripcion) VALUES
('ADMIN', 'Administrador global del sistema y catálogos'),
('CLIENTE', 'Usuario regular que compra y vende componentes'),
('TASADOR', 'Especialista interno que aprueba el hardware usado');

-- USUARIOS: Cubre casos de uso normales, inactivos, baneados y correos con caracteres especiales
-- [JJWT-INI] 
-- La contraseña por defecto fue configurada como 'SoftwareTienda@2026' para todos los usuarios, y está almacenada como 
-- hash (huella digital) utilizando el algoritmo BCrypt (generado con BCryptPasswordEncoder de Spring Security).
INSERT INTO usuario (id_rol, email, password_hash, estado) VALUES
(1, 'admin.root@pchardware.cl', '$2a$12$EfQfl6uP9KEODE2XUhn1Ru38rD8.pfV9liP1m2tnOQlxyL6CFGWDO', 'ACTIVO'),          -- Caso: Admin normal
(2, 'gamer.pro99@gmail.com', '$2a$12$EfQfl6uP9KEODE2XUhn1Ru38rD8.pfV9liP1m2tnOQlxyL6CFGWDO', 'ACTIVO'),             -- Caso: Cliente normal
(2, 'scammer_baneado@hotmail.com', '$2a$12$EfQfl6uP9KEODE2XUhn1Ru38rD8.pfV9liP1m2tnOQlxyL6CFGWDO', 'BANEADO'),      -- Caso borde: Cuenta bloqueada por fraude
(3, 'tasaciones@pchardware.cl', '$2a$12$EfQfl6uP9KEODE2XUhn1Ru38rD8.pfV9liP1m2tnOQlxyL6CFGWDO', 'ACTIVO'),          -- Caso: Empleado tasador
(2, 'correo.raro+alias@empresa.com.ar', '$2a$12$EfQfl6uP9KEODE2XUhn1Ru38rD8.pfV9liP1m2tnOQlxyL6CFGWDO', 'INACTIVO');-- Caso borde: Correo con '+' y subdominio, cuenta inactiva

-- PERFILES: Asociados 1:1, rut chilenos (contexto .cl), y valores nulos permitidos
INSERT INTO perfil (id_usuario, rut, nombre_completo, telefono) VALUES
(1, '11.111.111-1', 'Sys Admin Hardware', '+56911111111'),   -- Datos completos
(2, '18.123.456-7', 'Matias Soto', '+56922222222'),          -- Datos completos
(3, '19.987.654-3', 'Usuario Sospechoso', NULL),             -- Caso borde: Teléfono no proporcionado (NULL)
(4, '15.555.555-5', 'Pedro Tasador', '+56944444444'),        -- Datos completos
(5, '20.111.222-K', 'Maria Inactiva', '+56955555555');       -- Caso borde: RUT terminado en 'K'
