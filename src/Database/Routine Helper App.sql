RoutineHelperApp;

sql;

create table UserAccounts (
    username VARCHAR2(30) PRIMARY KEY,
    password VARCHAR2(30) NOT NULL,
    email VARCHAR2(100) UNIQUE,
    name VARCHAR2(50) NOT NULL
);

create table Routine (
    routineID NUMBER PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    recurrance VARCHAR2(100) NOT NULL,
    FOREIGN KEY (category) REFERENCES Category(name),
    FOREIGN KEY (username) REFERENCES UserAccounts(username)
);

create table Category (
    name VARCHAR2(100) PRIMARY KEY,
    type VARCHAR2(100) NOT NULL,
    FOREIGN KEY (username) REFERENCES UserAccounts(username)
);