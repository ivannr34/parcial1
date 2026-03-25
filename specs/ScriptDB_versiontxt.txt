CREATE DATABASE IF NOT EXISTS sumo_parcial1
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE sumo_parcial1;

CREATE TABLE IF NOT EXISTS luchador (
    id_luchador INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    peso DECIMAL(6,2) NOT NULL,
    tunica VARCHAR(20) NOT NULL,
    tecnicas TEXT NOT NULL,
    victorias INT NOT NULL DEFAULT 0,
    disponible TINYINT(1) NOT NULL DEFAULT 1,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

INSERT INTO luchador (nombre, peso, tunica, tecnicas, victorias, disponible) VALUES
('Ivann', 120.50, 'Roja', 'Empuje frontal,Derribo lateral', 0, 1),
('Juan', 135.00, 'Azul', 'Agarre mawashi,Proyeccion', 0, 1),
('Carlos', 98.75, 'Negra', 'Empuje doble,Barrida de pierna', 0, 1),
('Pedro', 145.20, 'Roja', 'Empuje frontal,Agarre mawashi', 0, 1),
('Diego', 110.30, 'Azul', 'Derribo lateral,Proyeccion', 0, 1),
('Mateo', 128.90, 'Negra', 'Empuje doble,Derribo lateral', 0, 1);