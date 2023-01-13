--liquibase formatted sql
--changeset santhosh:1

CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  email varchar(45) NOT NULL,
  password varchar(64) NOT NULL,
  username varchar(45) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);