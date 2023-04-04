--liquibase formatted sql
--changeset santhosh:25

alter table member_details
ADD diabetic_package varchar(100);

alter table member_details_audit
ADD diabetic_package varchar(100);