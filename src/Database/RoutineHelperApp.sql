RoutineHelperApp;

sql;

DROP TABLE UserAccounts CASCADE CONSTRAINTS;
DROP TABLE Category CASCADE CONSTRAINTS;
DROP TABLE Routine CASCADE CONSTRAINTS;
DROP SEQUENCE routine_seq;

CREATE TABLE UserAccounts (
    username VARCHAR2(30) PRIMARY KEY,
    password VARCHAR2(255) NOT NULL,
    email VARCHAR2(100) UNIQUE,
    first_name VARCHAR2(50) NOT NULL,
    last_name VARCHAR2(50) NOT NULL
);

CREATE TABLE Category (
    name VARCHAR2(100) PRIMARY KEY,
    type VARCHAR2(100) NOT NULL,
    username VARCHAR2(30) NOT NULL,
    FOREIGN KEY (username) REFERENCES UserAccounts(username)
);

CREATE SEQUENCE routine_seq START WITH 1 INCREMENT BY 1;

username, name, information, deadline, routine_time, routine_day, recurrence, duration, category

CREATE TABLE Routine (
    routineID NUMBER PRIMARY KEY,
    username VARCHAR2(30) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    deadline DATE NOT NULL,
    information VARCHAR2(500) NOT NULL,
    recurrence VARCHAR2(100) NOT NULL,
    duration NUMBER NOT NULL,
    routine_day DATE NOT NULL,
    routine_time DATE NOT NULL,
    category VARCHAR2(100) NOT NULL,
    FOREIGN KEY (category) REFERENCES Category(name),
    FOREIGN KEY (username) REFERENCES UserAccounts(username)
);

CREATE TABLE ToDoList (
    username VARCHAR2(30) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    information VARCHAR2(500),
    deadline VARCHAR2(30),
    toDo_time DATE,
    toDo_day DATE,
    duration NUMBER,
    category VARCHAR2(100),
    FOREIGN KEY (username) REFERENCES UserAccounts(username),
    FOREIGN KEY (category) REFERENCES Category(name)
);




