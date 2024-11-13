CREATE TABLE IF NOT EXISTS user_table
(
    id         BIGINT NOT NULL PRIMARY KEY,
    ldap_login VARCHAR(255) UNIQUE,
    name       VARCHAR(255),
    surname    VARCHAR(255)
);


INSERT INTO user_table (id, ldap_login, name, surname)
VALUES (1, 'damiani', 'Vlad', 'G'),
       (2, 'abruptum', 'Michael', 'L'),
       (3, 'plutonium', 'Anna', 'B'),
       (4, 'celestial', 'Sophie', 'M'),
       (5, 'neutron', 'Liam', 'N'),
       (6, 'quasar', 'Emma', 'H');
