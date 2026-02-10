DROP DATABASE IF EXISTS bdempleados;

CREATE DATABASE bdempleados

CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
USE bdempleados;

CREATE TABLE departamento (
    id_departamento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    ubicacion VARCHAR(100) DEFAULT NULL
);

INSERT INTO departamento (nombre, ubicacion) VALUES 
    ('Recursos Humanos', 'Edificio A'),
    ('Desarrollo', 'Edificio B'),
    ('Ventas', 'Edificio C');

CREATE TABLE empleado (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY, 
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL, 
    email VARCHAR(100) DEFAULT NULL,
    telefono VARCHAR(20) DEFAULT NULL,  
    id_departamento INT DEFAULT NULL,
    FOREIGN KEY (id_departamento) REFERENCES departamento(id_departamento)
        ON DELETE SET NULL
        ON UPDATE CASCADE       
);

INSERT INTO empleado (nombre, apellidos, email, telefono, id_departamento) VALUES 
    ('Ana', 'García López', 'ana.garcia@empresa.com', '123456789', 1),
    ('Carlos', 'Martínez Ruiz', 'carlos.martinez@empresa.com', '987654321', 2),
    ('María', 'Fernández Sánchez', 'maria.fernandez@empresa.com', '456789123', 3);
