/* --------------------------------------------------------
    using Concrete Table Inheritance pattern here
    to display User class inheritance structure
  -------------------------------------------------------- */
CREATE TABLE Users (
    Id int NOT NULL PRIMARY KEY,
    Login varchar(20) NOT NULL UNIQUE,
    Password int NOT NULL,
    Name varchar(20) NOT NULL,
    SecondName varchar(20),
    Email varchar(20) NOT NULL
);

CREATE TABLE Publishers (
    Id int NOT NULL PRIMARY KEY,
    Login varchar(20) NOT NULL UNIQUE,
    Password int NOT NULL,
    Name varchar(20) NOT NULL,
    SecondName varchar(20),
    Email varchar(20) NOT NULL
);

CREATE TABLE Administrators (
    Id int NOT NULL PRIMARY KEY,
    Login varchar(20) NOT NULL UNIQUE,
    Password int NOT NULL,
    Name varchar(20) NOT NULL,
    SecondName varchar(20),
    Email varchar(20) NOT NULL
);

CREATE TABLE Customers (
    Id int NOT NULL PRIMARY KEY,
    Login varchar(20) NOT NULL UNIQUE,
    Password int NOT NULL,
    Name varchar(20) NOT NULL,
    SecondName varchar(20),
    Email varchar(20) NOT NULL,
    Personal_Discount int NOT NULL CHECK(Personal_Discount >= 0 AND Personal_Discount <= 100)
);
-----------------------------------------------------------------

CREATE TABLE Books (
    Id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    Name varchar(20) NOT NULL,
    Jenre varchar(20) NOT NULL,
    Isbn char(13) NOT NULL UNIQUE,
    PublicationDate date NOT NULL,
    Price double precision NOT NULL CHECK(Price >= 0),
    Discount int NOT NULL CHECK(Discount >= 0 AND Discount <= 100),
    Publisher_Id int REFERENCES Publishers(Id)
);

CREATE TABLE Orders (
    Id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    CreationDate date NOT NULL,
    Price double precision NOT NULL CHECK(Price >= 0),
    Status int NOT NULL CHECK(Status > 0)
);

CREATE TABLE OrderEntries (
    Id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    BookId int REFERENCES Books(Id),
    Amount int NOT NULL CHECK(Amount > 0)
);

CREATE TABLE Cart (
    OrderId int NOT NULL REFERENCES Orders(Id),
    OrderEntryId int NOT NULL REFERENCES OrderEntries(Id),
    PRIMARY KEY (OrderId, OrderEntryId)
);
