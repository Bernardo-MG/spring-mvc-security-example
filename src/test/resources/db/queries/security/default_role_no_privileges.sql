-- Default role
INSERT INTO roles (id, name) VALUES
   (1, 'ADMIN');

-- Set role into user
INSERT INTO user_roles (user_id, role_id) VALUES
   (1, 1);