--liquibase formatted sql
--changeset santhosh:24

alter table member_details
ADD permanent_sterlization varchar(100);
alter table member_details
ADD permanent_sterlization_date timestamp(100);
alter table member_details
ADD tmp_sterlization varchar(100);
alter table member_details
ADD tmp_sterlization_type varchar(100);
alter table member_details
ADD diabetic_enrolment_date timestamp;
alter table member_details
ADD diabetic_enrollment_status varchar(100);
alter table member_details
ADD diabetic_enrollment_end_date timestamp;

alter table member_details_audit
ADD permanent_sterlization varchar(100);
alter table member_details_audit
ADD permanent_sterlization_date timestamp(100);
alter table member_details_audit
ADD tmp_sterlization varchar(100);
alter table member_details_audit
ADD tmp_sterlization_type varchar(100);
alter table member_details_audit
ADD diabetic_enrolment_date timestamp;
alter table member_details_audit
ADD diabetic_enrollment_status varchar(100);
alter table member_details_audit
ADD diabetic_enrollment_end_date timestamp;