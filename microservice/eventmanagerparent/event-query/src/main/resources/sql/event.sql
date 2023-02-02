CREATE SCHEMA IF NOT EXISTS `event_query`;

USE `event_query`;

CREATE TABLE event
(
    aggregate_id CHAR(36) NOT NULL PRIMARY KEY,
    user_id CHAR(50) NOT NULL,
    title CHAR(50) NOT NULL,
    place CHAR(50) NOT NULL,
    `time` TIMESTAMP NOT NULL
);

CREATE TABLE event_processed
(
    aggregate_id CHAR(36) NOT NULL,
    event_id CHAR(36) NOT NULL,
    PRIMARY KEY (aggregate_id, event_id),
    FOREIGN KEY (aggregate_id) REFERENCES event(aggregate_id)
)