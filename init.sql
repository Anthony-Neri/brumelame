SET NAMES utf8mb4;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

CREATE DATABASE IF NOT EXISTS brumelame
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE brumelame;

DROP TABLE IF EXISTS heroes;
DROP TABLE IF EXISTS hero_class_categories;
DROP TABLE IF EXISTS cells;
DROP TABLE IF EXISTS equipments;
DROP TABLE IF EXISTS equipment_categories;
DROP TABLE IF EXISTS enemies;
DROP TABLE IF EXISTS hero_classes;
DROP TABLE IF EXISTS boards;

CREATE TABLE boards (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE hero_classes (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  base_attack INT NOT NULL,
  base_health INT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_hero_classes_name (name),
  CONSTRAINT chk_hero_classes_base_attack CHECK (base_attack >= 0),
  CONSTRAINT chk_hero_classes_base_health CHECK (base_health >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO hero_classes (id, name, base_attack, base_health) VALUES
(1, 'WARRIOR', 5, 10),
(2, 'WIZARD', 8, 6);

CREATE TABLE equipment_categories (
  id INT NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_equipment_categories_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO equipment_categories (id, code) VALUES
(1, 'WEAPON'),
(2, 'SPELL'),
(3, 'POTION');

CREATE TABLE enemies (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  health INT NOT NULL,
  attack INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT chk_enemies_health CHECK (health >= 0),
  CONSTRAINT chk_enemies_attack CHECK (attack >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO enemies (id, name, health, attack) VALUES
(1, 'sorcier', 9, 2),
(2, 'gobelin', 6, 1),
(3, 'dragon', 15, 4);

CREATE TABLE equipments (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  equipment_type ENUM('ATTACK', 'DEFENSE', 'CONSUMABLE') NOT NULL,
  bonus INT NOT NULL,
  description VARCHAR(255) DEFAULT NULL,
  equipment_category_id INT NOT NULL,
  level INT NOT NULL,
  PRIMARY KEY (id),
  KEY idx_equipments_equipment_category_id (equipment_category_id),
  CONSTRAINT fk_equipments_equipment_category_id
    FOREIGN KEY (equipment_category_id) REFERENCES equipment_categories (id),
  CONSTRAINT chk_equipments_bonus CHECK (bonus >= 0),
  CONSTRAINT chk_equipments_level CHECK (level >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO equipments (id, name, equipment_type, bonus, description, equipment_category_id, level) VALUES
(1, 'Épée de bois', 'ATTACK', 0, 'Arme initiale', 1, 0),
(2, 'Bâton de marche', 'ATTACK', 0, 'Bâton initial', 2, 0),
(3, 'Épée de fer', 'ATTACK', 2, 'Épée de fer banale', 1, 1),
(4, 'Épée en acier', 'ATTACK', 4, 'Épée en acier', 1, 2),
(5, 'Bâton d''éclair de mana', 'ATTACK', 2, 'Éclair de mana', 2, 1),
(6, 'Bâton de boule de feu', 'ATTACK', 4, 'Les boules de feu ça brule', 2, 2),
(7, 'Bouclier de fer', 'DEFENSE', 2, 'Bouclier en bois', 1, 1),
(8, 'Bouclier de mana', 'DEFENSE', 1, 'Bouclier de mana', 2, 2),
(9, 'Petite potion de vie', 'CONSUMABLE', 3, 'Rend un peu de vie', 3, 1),
(10, 'Grande potion de vie', 'CONSUMABLE', 5, 'Rend davantage de vie', 3, 2);

CREATE TABLE hero_class_categories (
  hero_class_id INT NOT NULL,
  equipment_category_id INT NOT NULL,
  PRIMARY KEY (hero_class_id, equipment_category_id),
  KEY idx_hero_class_categories_equipment_category_id (equipment_category_id),
  CONSTRAINT fk_hero_class_categories_hero_class_id
    FOREIGN KEY (hero_class_id) REFERENCES hero_classes (id),
  CONSTRAINT fk_hero_class_categories_equipment_category_id
    FOREIGN KEY (equipment_category_id) REFERENCES equipment_categories (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO hero_class_categories (hero_class_id, equipment_category_id) VALUES
(1, 1), -- WARRIOR -> WEAPON
(1, 3), -- WARRIOR -> POTION
(2, 2), -- WIZARD -> SPELL
(2, 3); -- WIZARD -> POTION

CREATE TABLE cells (
  id INT NOT NULL AUTO_INCREMENT,
  cell_number INT NOT NULL,
  board_id INT NOT NULL,
  equipment_id INT DEFAULT NULL,
  enemy_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_cells_board_id_cell_number (board_id, cell_number),
  KEY idx_cells_equipment_id (equipment_id),
  KEY idx_cells_enemy_id (enemy_id),
  CONSTRAINT fk_cells_board_id
    FOREIGN KEY (board_id) REFERENCES boards (id),
  CONSTRAINT fk_cells_equipment_id
    FOREIGN KEY (equipment_id) REFERENCES equipments (id),
  CONSTRAINT fk_cells_enemy_id
    FOREIGN KEY (enemy_id) REFERENCES enemies (id),
  CONSTRAINT chk_cells_cell_number CHECK (cell_number >= 0),
  CONSTRAINT chk_cells_single_content CHECK (
    equipment_id IS NULL OR enemy_id IS NULL
  )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE heroes (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  hero_class_id INT NOT NULL,
  health INT NOT NULL,
  attack INT NOT NULL,
  offensive_equipment_id INT DEFAULT NULL,
  defensive_equipment_id INT DEFAULT NULL,
  cell_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_heroes_cell_id (cell_id),
  KEY idx_heroes_hero_class_id (hero_class_id),
  KEY idx_heroes_offensive_equipment_id (offensive_equipment_id),
  KEY idx_heroes_defensive_equipment_id (defensive_equipment_id),
  CONSTRAINT fk_heroes_hero_class_id
    FOREIGN KEY (hero_class_id) REFERENCES hero_classes (id),
  CONSTRAINT fk_heroes_offensive_equipment_id
    FOREIGN KEY (offensive_equipment_id) REFERENCES equipments (id),
  CONSTRAINT fk_heroes_defensive_equipment_id
    FOREIGN KEY (defensive_equipment_id) REFERENCES equipments (id),
  CONSTRAINT fk_heroes_cell_id
    FOREIGN KEY (cell_id) REFERENCES cells (id),
  CONSTRAINT chk_heroes_health CHECK (health >= 0),
  CONSTRAINT chk_heroes_attack CHECK (attack >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET foreign_key_checks = 1;
