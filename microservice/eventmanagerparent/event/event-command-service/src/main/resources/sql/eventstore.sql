CREATE SCHEMA IF NOT EXISTS eventstore;

USE eventstore;

CREATE TABLE event (
                       event_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       aggregate_id CHAR(36) NOT NULL,
                       version BIGINT NOT NULL,
                       data JSON,
                       UNIQUE(event_id, aggregate_id)
)