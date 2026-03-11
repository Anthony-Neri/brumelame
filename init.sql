-- Adminer 5.4.1 MariaDB 10.11.14-MariaDB-0ubuntu0.24.04.1 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS `brumelame` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `brumelame`;

DROP TABLE IF EXISTS `boards`;
CREATE TABLE `boards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `cells`;
CREATE TABLE `cells` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL,
  `id_board` int(11) DEFAULT NULL,
  `id_equipement` int(11) DEFAULT NULL,
  `id_ennemy` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_board` (`id_board`),
  KEY `id_equipement` (`id_equipement`),
  KEY `id_ennemy` (`id_ennemy`),
  CONSTRAINT `cells_ibfk_1` FOREIGN KEY (`id_board`) REFERENCES `boards` (`id`),
  CONSTRAINT `cells_ibfk_2` FOREIGN KEY (`id_equipement`) REFERENCES `equipments` (`id`),
  CONSTRAINT `cells_ibfk_3` FOREIGN KEY (`id_ennemy`) REFERENCES `enemies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `enemies`;
CREATE TABLE `enemies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `health` int(11) DEFAULT NULL,
  `attack` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `enemies` (`id`, `name`, `health`, `attack`) VALUES
(1,	'sorcier',	9,	2),
(2,	'gobelin',	6,	1),
(3,	'dragon',	15,	4);

DROP TABLE IF EXISTS `equipments`;
CREATE TABLE `equipments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `type` enum('ATTACK','DEFENSE','CONSUMABLE') NOT NULL,
  `bonus` int(11) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  `category` varchar(50) NOT NULL,
  `niv` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `equipments` (`id`, `name`, `type`, `bonus`, `description`, `category`, `niv`) VALUES
(1,	'Epee de fer',	'ATTACK',	3,	'Epee de fer banale',	'WEAPON',	1),
(2,	'Epee de bois',	'ATTACK',	0,	'arme intiale',	'WEAPON',	0),
(3,	'baton de marche',	'ATTACK',	0,	'baton initiale',	'SPELL',	0),
(4,	'baton de boule de feu',	'ATTACK',	3,	'une peu plus de panache',	'SPELL',	1),
(5,	'Epee de fer',	'ATTACK',	5,	'epee tranchante',	'WEAPON',	2),
(6,	'Baton boule de feu supreme',	'ATTACK',	5,	NULL,	'SPELL',	2),
(7,	'Bouclier de Mana',	'DEFENSE',	3,	NULL,	'SPELL',	1),
(8,	'Bouclier de fer',	'DEFENSE',	3,	NULL,	'WEAPON',	1),
(9,	'Petite potion de vie',	'CONSUMABLE',	3,	NULL,	'CONSOMABLE',	1),
(10,	'Grande potion de vie',	'CONSUMABLE',	5,	NULL,	'CONSOMABLE',	2);

DROP TABLE IF EXISTS `heroclasses`;
CREATE TABLE `heroclasses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `attack` int(11) NOT NULL,
  `health` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `heroclasses` (`id`, `name`, `attack`, `health`) VALUES
(1,	'warrior',	5,	10),
(2,	'wizard',	8,	6);

DROP TABLE IF EXISTS `heroclasses_categories`;
CREATE TABLE `heroclasses_categories` (
  `category` varchar(50) NOT NULL,
  `heroclasse` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `heroclasses_categories` (`category`, `heroclasse`) VALUES
('WEAPON',	'WARRIOR'),
('SPELL',	'WIZARD'),
('CONSOMABLE',	'WARRIOR'),
('CONSOMABLE',	'WIZARD');

DROP TABLE IF EXISTS `heroes`;
CREATE TABLE `heroes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` enum('WARRIOR','WIZARD') DEFAULT NULL,
  `health` int(11) DEFAULT NULL,
  `attack` int(11) DEFAULT NULL,
  `id_off_equip` int(11) DEFAULT NULL,
  `id_def_equip` int(11) DEFAULT NULL,
  `id_cell` int(11) DEFAULT NULL,
  `id_board` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_off_equip` (`id_off_equip`),
  KEY `id_def_equip` (`id_def_equip`),
  KEY `id_cell` (`id_cell`),
  KEY `id_board` (`id_board`),
  CONSTRAINT `heroes_ibfk_1` FOREIGN KEY (`id_off_equip`) REFERENCES `equipments` (`id`),
  CONSTRAINT `heroes_ibfk_2` FOREIGN KEY (`id_def_equip`) REFERENCES `equipments` (`id`),
  CONSTRAINT `heroes_ibfk_3` FOREIGN KEY (`id_cell`) REFERENCES `cells` (`id`),
  CONSTRAINT `heroes_ibfk_4` FOREIGN KEY (`id_board`) REFERENCES `boards` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- 2026-03-11 15:29:46 UTC
