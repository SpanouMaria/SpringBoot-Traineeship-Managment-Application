CREATE DATABASE IF NOT EXISTS `traineeship_db`;
USE `traineeship_db`;

DROP TABLE IF EXISTS `professors`;

CREATE TABLE `professors` (
  `username` VARCHAR(50) NOT NULL,
  `professor_name` VARCHAR(100) NOT NULL,
  `interests` TEXT,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
