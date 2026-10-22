CREATE DATABASE IF NOT EXISTS torneofutbol
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE torneofutbol;

CREATE TABLE campeonato (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    anio INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    CONSTRAINT uk_campeonato_anio UNIQUE (anio)
) ENGINE=InnoDB;

CREATE TABLE equipo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    escudo VARCHAR(255) NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE inscripcion_campeonato (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    campeonato_id BIGINT NOT NULL,
    equipo_id BIGINT NOT NULL,
    abandonado TINYINT(1) NOT NULL DEFAULT 0,
    CONSTRAINT fk_inscripcion_campeonato
        FOREIGN KEY (campeonato_id) REFERENCES campeonato(id),
    CONSTRAINT fk_inscripcion_equipo
        FOREIGN KEY (equipo_id) REFERENCES equipo(id),
    CONSTRAINT uk_campeonato_equipo UNIQUE (campeonato_id, equipo_id)
) ENGINE=InnoDB;

CREATE TABLE jornada (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    campeonato_id BIGINT NOT NULL,
    numero INT NOT NULL,
    fecha DATE NOT NULL,
    CONSTRAINT fk_jornada_campeonato
        FOREIGN KEY (campeonato_id) REFERENCES campeonato(id),
    CONSTRAINT uk_campeonato_jornada_numero UNIQUE (campeonato_id, numero),
    CONSTRAINT uk_campeonato_jornada_fecha UNIQUE (campeonato_id, fecha)
) ENGINE=InnoDB;

CREATE TABLE partido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    jornada_id BIGINT NOT NULL,
    equipo1_id BIGINT NOT NULL,
    equipo2_id BIGINT NOT NULL,
    goles_equipo1 INT NULL,
    goles_equipo2 INT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PROGRAMADO',
    walkover TINYINT(1) NOT NULL DEFAULT 0,
    CONSTRAINT fk_partido_jornada
        FOREIGN KEY (jornada_id) REFERENCES jornada(id),
    CONSTRAINT fk_partido_equipo1
        FOREIGN KEY (equipo1_id) REFERENCES equipo(id),
    CONSTRAINT fk_partido_equipo2
        FOREIGN KEY (equipo2_id) REFERENCES equipo(id),
    CONSTRAINT chk_equipos_diferentes CHECK (equipo1_id <> equipo2_id),
    CONSTRAINT chk_goles_equipo1 CHECK (goles_equipo1 IS NULL OR goles_equipo1 >= 0),
    CONSTRAINT chk_goles_equipo2 CHECK (goles_equipo2 IS NULL OR goles_equipo2 >= 0),
    CONSTRAINT chk_estado_partido CHECK (estado IN ('PROGRAMADO', 'JUGADO')),
    CONSTRAINT chk_resultado_jugado CHECK (
        estado = 'PROGRAMADO'
        OR (goles_equipo1 IS NOT NULL AND goles_equipo2 IS NOT NULL)
    )
) ENGINE=InnoDB;

CREATE TABLE sorteo_campeonato (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    campeonato_id BIGINT NOT NULL,
    equipo_ganador_id BIGINT NOT NULL,
    fecha_sorteo DATE NOT NULL,
    CONSTRAINT fk_sorteo_campeonato
        FOREIGN KEY (campeonato_id) REFERENCES campeonato(id),
    CONSTRAINT fk_sorteo_equipo_ganador
        FOREIGN KEY (equipo_ganador_id) REFERENCES equipo(id),
    CONSTRAINT uk_sorteo_campeonato UNIQUE (campeonato_id)
) ENGINE=InnoDB;

CREATE INDEX idx_inscripcion_campeonato ON inscripcion_campeonato(campeonato_id);
CREATE INDEX idx_inscripcion_equipo ON inscripcion_campeonato(equipo_id);
CREATE INDEX idx_jornada_campeonato ON jornada(campeonato_id);
CREATE INDEX idx_partido_jornada ON partido(jornada_id);
CREATE INDEX idx_partido_equipo1 ON partido(equipo1_id);
CREATE INDEX idx_partido_equipo2 ON partido(equipo2_id);
CREATE INDEX idx_sorteo_equipo_ganador ON sorteo_campeonato(equipo_ganador_id);
