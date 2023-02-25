CREATE SCHEMA IF NOT EXISTS `user_db`;

USE `user_db`;

CREATE TABLE `user`
(
    id BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
    username CHAR(50) NOT NULL UNIQUE,
    password CHAR(60) NOT NULL
);

CREATE TABLE `subscription`
(
    subscriber BIGINT(11) NOT NULL,
    subscribed_to BIGINT(11) NOT NULL,
    PRIMARY KEY (subscriber, subscribed_to),
    FOREIGN KEY (subscriber) REFERENCES user(id),
    FOREIGN KEY (subscribed_to) REFERENCES user(id)
)