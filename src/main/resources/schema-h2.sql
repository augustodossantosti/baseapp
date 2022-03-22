drop table if exists users cascade constraints;

create table users(
    username varchar_ignorecase(15) not null primary key,
    password varchar_ignorecase(100) not null,
    enabled boolean not null
);

drop table if exists authorities cascade constraints;

create table authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

drop table if exists domain cascade constraints;

create table domain (
  id NUMBER(19,0) not null primary key,
	create_date TIMESTAMP (6) DEFAULT CURRENT_TIMESTAMP,
	info varchar_ignorecase(255)
);
