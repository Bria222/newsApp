--SET MODE PostgreSQL;
--
--CREATE TABLE IF NOT EXISTS departments (
-- id int PRIMARY KEY auto_increment,
-- name VARCHAR,
-- company VARCHAR,
-- division VARCHAR,
-- phone VARCHAR,
-- website VARCHAR,
-- email VARCHAR
--);
--
--CREATE TABLE IF NOT EXISTS departmentnews (
-- id int PRIMARY KEY auto_increment,
-- name VARCHAR
--);
--
--CREATE TABLE IF NOT EXISTS newsdetails (
-- id int PRIMARY KEY auto_increment,
-- writtenby VARCHAR,
-- rating VARCHAR,
-- content VARCHAR,
-- departmentid INTEGER,
-- createdat BIGINT
--);
--CREATE TABLE IF NOT EXISTS department_departmentnews (
-- id int PRIMARY KEY auto_increment,
-- departmentnewsid INTEGER,
-- departmentid INTEGER
--);

$ psql
psql (11.3 (Ubuntu 11.3-0ubuntu0.19.04.1), server 10.7 (Ubuntu 10.7-0ubuntu0.18.10.1))
Type "help" for help.

# CREATE DATABASE w-tracker;
CREATE DATABASE

# \c w-tracker;
You are now connected to database "w-tracker" as user "user-name".

# CREATE TABLE departments(id SERIAL PRIMARY KEY, name VARCHAR, company VARCHAR, division VARCHAR, phone VARCHAR, website VARCHAR, email VARCHAR);
CREATE TABLE

# CREATE TABLE departmentnews(id SERIAL PRIMARY KEY, name VARCHAR);
CREATE TABLE

# CREATE TABLE newsdetails(id SERIAL PRIMARY KEY, writtenby VARCHAR, rating VARCHAR, content VARCHAR, departmentid INTEGER, createdat BIGINT);
CREATE TABLE

# CREATE TABLE department_departmentnews(id SERIAL PRIMARY KEY, departmentnewsid INTEGER, departmentid INTEGER);
CREATE TABLE

# CREATE DATABASE w-tracker_test WITH TEMPLATE w-tracker;
CREATE DATABASE

# \q