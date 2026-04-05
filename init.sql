CREATE DATABASE IF NOT EXISTS veterinary_system;
USE veterinary_system;

-- TABLA USUARIOS
CREATE TABLE IF NOT EXISTS `user` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150),
    password VARCHAR(255) NOT NULL
);

-- TABLA PACIENTES
CREATE TABLE IF NOT EXISTS patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    species VARCHAR(100),
    breed VARCHAR(100),
    age INT,
    owner VARCHAR(100)
);

-- TABLA CITAS
CREATE TABLE IF NOT EXISTS appointment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    time TIME,
    reason VARCHAR(255),
    veterinarian VARCHAR(100)
);

-- TABLA SERVICIOS
CREATE TABLE IF NOT EXISTS care (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150),
    cost DECIMAL(10,2)
);

-- TABLA MEDICAMENTOS
CREATE TABLE IF NOT EXISTS medication (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150),
    cost DECIMAL(10,2)
);

-- TABLA FACTURAS
CREATE TABLE IF NOT EXISTS invoice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100),
    date DATE,
    time TIME,
    total_cost DECIMAL(10,2)
);

-- RELACION FACTURA - SERVICIOS
CREATE TABLE IF NOT EXISTS invoice_cares (
    invoice_id BIGINT,
    care_id BIGINT,
    PRIMARY KEY (invoice_id, care_id),
    FOREIGN KEY (invoice_id) REFERENCES invoice(id) ON DELETE CASCADE,
    FOREIGN KEY (care_id) REFERENCES care(id) ON DELETE CASCADE
);

-- RELACION FACTURA - MEDICAMENTOS
CREATE TABLE IF NOT EXISTS invoice_medications (
    invoice_id BIGINT,
    medication_id BIGINT,
    PRIMARY KEY (invoice_id, medication_id),
    FOREIGN KEY (invoice_id) REFERENCES invoice(id) ON DELETE CASCADE,
    FOREIGN KEY (medication_id) REFERENCES medication(id) ON DELETE CASCADE
);

-- USUARIO INICIAL PARA LOGIN
INSERT INTO user (username, email, password)
VALUES ('admin', 'admin@gmail.com', '1234');