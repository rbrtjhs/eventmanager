CREATE SCHEMA IF NOT EXISTS `user_db`;

USE `user_db`;

CREATE TABLE `user`
(
    user_id BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
    username CHAR(50) NOT NULL UNIQUE,
    password CHAR(60) NOT NULL
);