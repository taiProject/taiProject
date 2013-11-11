SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;
SET search_path = PUBLIC, pg_catalog;

SET default_tablespace = '';
SET default_with_oids = FALSE;

CREATE SEQUENCE user_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE users(
  id			BIGINT DEFAULT nextval('user_seq' :: REGCLASS) NOT NULL,
  login			CHARACTER VARYING(50)						   NOT NULL,
  password		CHARACTER VARYING(50)						   NOT NULL,
  role_name 	CHARACTER VARYING(30)						   NOT NULL
);

ALTER TABLE ONLY users
ADD CONSTRAINT "PK.users" PRIMARY KEY (id, login);

CREATE INDEX "pki_PK.users.id" ON users USING BTREE (id);