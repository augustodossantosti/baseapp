--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

DROP TABLE USERS CASCADE CONSTRAINTS;

CREATE TABLE USERS (
  USERNAME VARCHAR2(128) PRIMARY KEY,
  PASSWORD VARCHAR2(128) NOT NULL,
  ENABLED CHAR(1) CHECK (ENABLED IN ('Y','N') ) NOT NULL
);

--------------------------------------------------------
--  DDL for Table AUTHORITIES
--------------------------------------------------------

DROP TABLE AUTHORITIES CASCADE CONSTRAINTS;

CREATE TABLE AUTHORITIES (
  USERNAME VARCHAR2(128) NOT NULL,
  AUTHORITY VARCHAR2(128) NOT NULL
);

--------------------------------------------------------
--  Constraints for Table AUTHORITIES
--------------------------------------------------------

ALTER TABLE AUTHORITIES ADD CONSTRAINT AUTHORITIES_UNIQUE UNIQUE (USERNAME, AUTHORITY);
ALTER TABLE AUTHORITIES ADD CONSTRAINT AUTHORITIES_FK1 FOREIGN KEY (USERNAME) REFERENCES USERS (USERNAME) ENABLE;

--------------------------------------------------------
--  DDL for Table DOMAIN
--------------------------------------------------------

DROP TABLE DOMAIN CASCADE CONSTRAINTS;

CREATE TABLE DOMAIN (
  "ID" NUMBER(19,0),
	"CREATE_DATE" TIMESTAMP (6),
	"INFO" VARCHAR2(255 CHAR)
);

--------------------------------------------------------
--  Constraints for Table DOMAIN
--------------------------------------------------------

ALTER TABLE DOMAIN ADD PRIMARY KEY ("ID");