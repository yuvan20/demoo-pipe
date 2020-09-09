--liquibase formatted sql
--changeset dbadmin:3
CREATE TABLE IF NOT EXISTS frizzy(
columnName1 VARCHAR (355)
);
--rollback DROP TABLE
--rollback prodTable
