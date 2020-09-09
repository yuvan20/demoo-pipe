--liquibase formatted sql
--changeset kush.dev:5
alter table devops add column cloud varchar(2)
