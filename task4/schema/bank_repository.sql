CREATE SEQUENCE customer_id;

CREATE TABLE customer
(
    ID INT DEFAULT NEXTVAL('customer_id') PRIMARY KEY,
    Name VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(50) NOT NULL,
    Balance MONEY NOT NULL
);