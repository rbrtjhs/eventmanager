CREATE SCHEMA IF NOT EXISTS `event_query`;

USE `event_query`;

CREATE TABLE event
(
    aggregate_id CHAR(36) NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    version BIGINT NOT NULL,
    title CHAR(50) NOT NULL,
    place CHAR(50) NOT NULL,
    `time` TIMESTAMP NOT NULL,
    capacity BIGINT NOT NULL,
    actual_capacity BIGINT DEFAULT (0)
);

CREATE TABLE event_processed
(
    event_id BIGINT NOT NULL PRIMARY KEY,
    aggregate_id CHAR(36) NOT NULL,
    FOREIGN KEY (aggregate_id) REFERENCES event(aggregate_id)
)