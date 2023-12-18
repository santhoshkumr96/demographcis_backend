--liquibase formatted sql
--changeset santhosh:25

alter table member_details
ADD height int DEFAULT NULL;

alter table member_details
ADD weight int DEFAULT NULL;

alter table member_details_audit
ADD height int DEFAULT NULL;

alter table member_details_audit
ADD weight int DEFAULT NULL;