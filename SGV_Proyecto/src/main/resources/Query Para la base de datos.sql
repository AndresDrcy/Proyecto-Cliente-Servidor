CREATE DATABASE SGV_Proyecto;

-- Tabla para los inventarios
CREATE TABLE SGV_Proyecto.at_inventarios (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    codigo_unico VARCHAR(20) UNIQUE NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    color VARCHAR(30) NOT NULL,
    anno INT NOT NULL,
    precio DOUBLE NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    numero_puertas INT,
    tiene_aire BOOLEAN,
    cilindrada INT
);


-- Tabla para los clientes
CREATE TABLE SGV_Proyecto.at_cliente (
    documento_identidad VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    edad INT,
    telefono VARCHAR(20),
    correo VARCHAR(100)
);

-- Tabla de ventas
CREATE TABLE SGV_Proyecto.at_ventas (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    numero_voucher VARCHAR(20) NOT NULL UNIQUE,
    documento_cliente VARCHAR(20) NOT NULL,
    codigo_vehiculo VARCHAR(20) NOT NULL,
    fecha_venta DATETIME NOT NULL,
    monto_total DOUBLE NOT NULL,
    termino_pago VARCHAR(30) NOT NULL
);

-- tabla de respaldo de del inventario
CREATE TABLE SGV_Proyecto.at_inventarios_respaldo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo_unico VARCHAR(20),
    marca VARCHAR(50),
    modelo VARCHAR(50),
    color VARCHAR(30),
    anno INT,
    precio DOUBLE,
    tipo VARCHAR(20),
    numero_puertas INT,
    tiene_aire BOOLEAN,
    cilindrada INT,
    fecha_respaldo DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- tabla para los usuarios del sistema
DROP table SGV_Proyecto.at_usuarios;
CREATE TABLE SGV_Proyecto.at_usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    rol VARCHAR(20) DEFAULT 'usuario'
);

-- inserts de prueba para usuarios
INSERT INTO SGV_Proyecto.at_usuarios (usuario, contrasena, rol)
VALUES 
('ventas1', '1234', 'vendedor'),
('admin', 'admin123', 'administrador'),
('ventas2', '4567', 'vendedor');


-- inserts de prueba para inventarios
INSERT INTO SGV_Proyecto.at_inventarios 
(codigo_unico, marca, modelo, color, anno, precio, tipo, numero_puertas, tiene_aire, cilindrada) VALUES
('VH-X9A1B2C', 'Toyota', 'Corolla', 'Rojo', 2020, 15000.00, 'Automovil', 4, TRUE, NULL),
('VH-F7G8H2I', 'Honda', 'Civic', 'Azul', 2019, 14500.00, 'Automovil', 4, TRUE, NULL),
('VH-L9M0N1P', 'Suzuki', 'GSX-R150', 'Negro', 2022, 4500.00, 'Motocicleta', NULL, NULL, 150),
('VH-R3S4T5U', 'Chevrolet', 'Spark', 'Gris', 2021, 9800.00, 'Automovil', 4, FALSE, NULL),
('VH-W8X9Y1Z', 'Yamaha', 'FZ25', 'Azul', 2023, 5200.00, 'Motocicleta', NULL, NULL, 250),
('VH-B4C5D6E', 'Ford', 'Focus', 'Blanco', 2018, 13000.00, 'Automovil', 4, TRUE, NULL),
('VH-G1H2I3J', 'KTM', 'Duke 390', 'Naranja', 2023, 6900.00, 'Motocicleta', NULL, NULL, 390),
('VH-L6M7N8O', 'Nissan', 'Versa', 'Plata', 2020, 14200.00, 'Automovil', 4, TRUE, NULL),
('VH-Q1R2S3T', 'Bajaj', 'Pulsar NS200', 'Rojo', 2021, 3800.00, 'Motocicleta', NULL, NULL, 200),
('VH-V6W7X8Y', 'Hyundai', 'Elantra', 'Negro', 2022, 16000.00, 'Automovil', 4, TRUE, NULL);

-- insets de prueba para clientes 
INSERT INTO SGV_Proyecto.at_cliente 
(documento_identidad, nombre, edad, telefono, correo) VALUES
('101010101', 'Carlos Ramírez', 30, '8888-1111', 'carlos.ramirez@email.com'),
('202020202', 'Laura Jiménez', 27, '8888-2222', 'laura.jimenez@email.com'),
('303030303', 'Andrés Mora', 35, '8888-3333', 'andres.mora@email.com'),
('404040404', 'Sofía Vargas', 22, '8888-4444', 'sofia.vargas@email.com'),
('505050505', 'Ricardo Méndez', 40, '8888-5555', 'ricardo.mendez@email.com'),
('606060606', 'Ana Torres', 31, '8888-6666', 'ana.torres@email.com'),
('707070707', 'Luis Fernández', 29, '8888-7777', 'luis.fernandez@email.com'),
('808080808', 'Paula Rojas', 33, '8888-8888', 'paula.rojas@email.com'),
('909090909', 'Jorge Castillo', 45, '8888-9999', 'jorge.castillo@email.com'),
('111111111', 'Daniela Cruz', 26, '8888-0000', 'daniela.cruz@email.com');

select * from SGV_Proyecto.at_inventarios;
select * from SGV_Proyecto.at_cliente;
select * from SGV_Proyecto.at_ventas;
select * from SGV_Proyecto.at_inventarios_respaldo;

