--liquibase formatted sql
--changeset santhosh:4

INSERT INTO roles(name) VALUES("ROLE_USER");
INSERT INTO roles(name) VALUES("ROLE_MODERATOR");
INSERT INTO roles(name) VALUES("ROLE_ADMIN");