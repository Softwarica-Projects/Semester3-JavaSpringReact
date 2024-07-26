--Seeding Roles
INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');

--Seeding Admin User
INSERT IGNORE INTO users(created_at,updated_at,email,name,password,username)
       VALUES('2024-07-25 02:54:35','2024-07-25 02:54:35','admin','admin','$2a$10$PyORPuE1myB6b70N2kNa.e5/KRSr0vuQD6iJW5QrX8nIqWMUYGJam','admin');
INSERT IGNORE INTO user_roles(user_id,role_id) VALUES(1,2);