CREATE SCHEMA IF NOT EXISTS eventstore;

USE eventstore;

CREATE TABLE aggregate (
                           id CHAR(36) PRIMARY KEY,
                           version BIGINT NOT NULL,
                           type CHAR(100) NOT NULL
);

CREATE TABLE event (
                       event_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       aggregate_id CHAR(36) NOT NULL,
                       data JSON,
                       FOREIGN KEY (aggregate_id) REFERENCES aggregate(id)
);