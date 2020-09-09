--liquibase formatted sql
--changeset dbadmin:4
create table devops (
    id int primary key,
    name varchar(50) not null,
    year varchar(50),
    domain varchar(30)
);
