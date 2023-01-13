--liquibase formatted sql
--changeset santhosh:2

CREATE TABLE `user_roles` (
  `user_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL
);