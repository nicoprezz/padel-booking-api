-- V1: tabla de pistas (entidad Court).
-- Flyway ejecuta este script una sola vez y lo registra en flyway_schema_history.
-- Una vez aplicado NO se modifica: cualquier cambio de esquema va en un V2, V3...

CREATE TABLE courts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    type ENUM('INDOOR','OUTDOOR') NOT NULL,
    active BIT(1) NOT NULL,

    CONSTRAINT pk_courts PRIMARY KEY (id),
    CONSTRAINT uk_courts_name UNIQUE (name)
);
