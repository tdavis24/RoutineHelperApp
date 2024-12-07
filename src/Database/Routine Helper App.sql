RoutineHelperApp;

sql;

<<<<<<< HEAD
=======
-- create account's table
>>>>>>> 7c25b8043722b10d6e73132e9754381b8252212d
create table UserAccounts (
    username VARCHAR2(30) PRIMARY KEY,
    password VARCHAR2(30) NOT NULL,
    email VARCHAR2(100) UNIQUE,
    name VARCHAR2(50) NOT NULL
);

<<<<<<< HEAD
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
=======
-- create category table
create table Category (
    categoryName VARCHAR2(100) PRIMARY KEY,
    type VARCHAR2(100) NOT NULL,
    username VARCHAR2(30) NOT NULL,
    FOREIGN KEY (username) REFERENCES UserAccounts(username)
);

-- create routine table
create table Routine (
    routineID NUMBER PRIMARY KEY,
    routineName VARCHAR2(50) NOT NULL,
    recurrance VARCHAR2(100) NOT NULL,
    username VARCHAR2(30) NOT NULL,
    categoryName VARCHAR2(100) NOT NULL,
    FOREIGN KEY (categoryName) REFERENCES Category(categoryName),
>>>>>>> 7c25b8043722b10d6e73132e9754381b8252212d
    FOREIGN KEY (username) REFERENCES UserAccounts(username)
);