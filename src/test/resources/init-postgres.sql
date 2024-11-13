CREATE SEQUENCE IF NOT EXISTS users_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS users
(
    user_id    BIGINT NOT NULL PRIMARY KEY,
    login      VARCHAR(255) UNIQUE,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);


INSERT INTO users (user_id, login, first_name, last_name)
VALUES (1, 'bor', 'John', 'Wick'),
       (2, 'bob', 'Bill', 'Stark'),
       (3, 'astra', 'Anna', 'Kors'),
       (4, 'river', 'Lara', 'Croft'),
       (5, 'neo', 'Thomas', 'Anderson'),
       (6, 'trinity', 'Tracy', 'Moss'),
       (7, 'morpheus', 'Michael', 'Smith'),
       (8, 'matrix', 'Alice', 'Brown'),
       (9, 'sparrow', 'Jack', 'Sparrow'),
       (10, 'joker', 'Arthur', 'Fleck'),
       (11, 'wayne', 'Bruce', 'Wayne'),
       (12, 'bond', 'James', 'Bond'),
       (13, 'lara', 'Mia', 'White'),
       (14, 'xeno', 'Oscar', 'Blue');
