-- All the privileges
INSERT INTO privileges (id, name) VALUES
   (1, 'CREATE_DATA'),
   (2, 'READ_DATA'),
   (3, 'UPDATE_DATA'),
   (4, 'DELETE_DATA');

-- Default role
INSERT INTO roles (id, name) VALUES
   (1, 'ADMIN');

-- Set privileges into default role
INSERT INTO role_privileges (role_id, privilege_id) VALUES
   (1, 1),
   (1, 2),
   (1, 3),
   (1, 4);

-- Set role into user
INSERT INTO user_roles (user_id, role_id) VALUES
   (1, 1);