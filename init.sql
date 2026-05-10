CREATE DATABASE IF NOT EXISTS veterinary_system;
USE veterinary_system;

-- ======================
-- TABLA USUARIOS
-- ======================
CREATE TABLE IF NOT EXISTS `user` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150),
    password VARCHAR(255) NOT NULL
);

INSERT INTO user (username, email, password) VALUES
('admin', 'admin@veterinary.cl', '1234'),
('user', 'user@veterinary.cl', '4321'),
('doctor', 'doctor@veterinary.cl', '1234');

-- ======================
-- TABLA PACIENTES
-- ======================
CREATE TABLE IF NOT EXISTS patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    species VARCHAR(100),
    breed VARCHAR(100),
    age INT,
    owner VARCHAR(100)
);

INSERT INTO patient (name, species, breed, age, owner) VALUES
('Max', 'Perro', 'Labrador', 5, 'Juan Pérez'),
('Luna', 'Gato', 'Siames', 3, 'María López'),
('Rocky', 'Perro', 'Bulldog', 4, 'Carlos Díaz'),
('Milo', 'Gato', 'Persa', 2, 'Ana Torres');

-- ======================
-- TABLA CITAS
-- ======================
CREATE TABLE IF NOT EXISTS appointment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    time TIME,
    reason VARCHAR(255),
    veterinarian VARCHAR(100)
);

INSERT INTO appointment (date, time, reason, veterinarian) VALUES
('2026-04-10', '10:00:00', 'Control general', 'Dr. Soto'),
('2026-04-11', '11:30:00', 'Vacunación', 'Dra. Pérez'),
('2026-04-12', '09:00:00', 'Dolor estomacal', 'Dr. Soto'),
('2026-04-13', '16:00:00', 'Chequeo anual', 'Dra. Pérez');

-- ======================
-- TABLA SERVICIOS
-- ======================
CREATE TABLE IF NOT EXISTS care (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150),
    cost DECIMAL(10,2)
);

INSERT INTO care (name, cost) VALUES
('Consulta general', 15000),
('Vacunación', 12000),
('Radiografía', 30000),
('Cirugía menor', 50000);

-- ======================
-- TABLA MEDICAMENTOS
-- ======================
CREATE TABLE IF NOT EXISTS medication (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150),
    cost DECIMAL(10,2)
);

INSERT INTO medication (name, cost) VALUES
('Antibiótico', 8000),
('Analgésico', 5000),
('Vacuna Rabia', 10000),
('Antiinflamatorio', 7000);

-- ======================
-- TABLA FACTURAS
-- ======================
CREATE TABLE IF NOT EXISTS invoice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100),
    date DATE,
    time TIME,
    total_cost DECIMAL(10,2)
);

INSERT INTO invoice (id, patient_name, date, time, total_cost) VALUES
(1, 'Max', '2026-04-10', '10:00:00', 40000),
(2, 'Luna', '2026-04-11', '11:30:00', 22000),
(3, 'Rocky', '2026-04-12', '09:00:00', 43000),
(4, 'Milo', '2026-04-13', '16:00:00', 57000);

-- ======================
-- RELACION FACTURA - SERVICIOS
-- ======================
CREATE TABLE IF NOT EXISTS invoice_cares (
    invoice_id BIGINT,
    care_id BIGINT,
    PRIMARY KEY (invoice_id, care_id),
    FOREIGN KEY (invoice_id) REFERENCES invoice(id) ON DELETE CASCADE,
    FOREIGN KEY (care_id) REFERENCES care(id) ON DELETE CASCADE
);

INSERT INTO invoice_cares (invoice_id, care_id) VALUES
(1, 1),
(1, 2),

(2, 2),

(3, 1),
(3, 3),

(4, 4);

-- ======================
-- RELACION FACTURA - MEDICAMENTOS
-- ======================
CREATE TABLE IF NOT EXISTS invoice_medications (
    invoice_id BIGINT,
    medication_id BIGINT,
    PRIMARY KEY (invoice_id, medication_id),
    FOREIGN KEY (invoice_id) REFERENCES invoice(id) ON DELETE CASCADE,
    FOREIGN KEY (medication_id) REFERENCES medication(id) ON DELETE CASCADE
);

INSERT INTO invoice_medications (invoice_id, medication_id) VALUES
(1, 1),
(1, 2),

(2, 3),

(3, 1),
(3, 2),

(4, 1),
(4, 4);