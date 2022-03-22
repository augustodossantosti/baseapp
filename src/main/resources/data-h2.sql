--------------------------------------------------------
--  DDM for Table USERS
--------------------------------------------------------

INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('user','$2a$05$Bkk.jx7rJXlH2UfTn0srNeZhM0NJYIUz.VcRoLpj/iv4YcMr.luua','Y');
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('admin','$2a$05$TmR52.JmG0ePZ4QNWdVU.eWsLdqHc2ZJxxrz384sDZtIqw99evsu6','Y');

--------------------------------------------------------
--  DDM for Table AUTHORITIES
--------------------------------------------------------

INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('user', 'USER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'USER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'ADMIN');

--------------------------------------------------------
--  DDM for Table Domain
--------------------------------------------------------

INSERT INTO DOMAIN (ID, INFO) VALUES (1, 'INFO1');
INSERT INTO DOMAIN (ID, INFO) VALUES (2, 'INFO2');
INSERT INTO DOMAIN (ID, INFO) VALUES (3, 'INFO3');