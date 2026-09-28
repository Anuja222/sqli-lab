CREATE DATABASE IF NOT EXISTS sqli_lab;
USE sqli_lab;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

INSERT INTO users (username, password) VALUES
    ('alice', 'alice123'),
    ('bob', 'bob123'),
    ('charlie', 'charlie123');
