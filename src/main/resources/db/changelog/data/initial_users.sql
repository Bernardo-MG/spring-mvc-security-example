--
--  The MIT License (MIT)
--
--  Copyright (c) 2022 Bernardo Martínez Garrido
--  
--  Permission is hereby granted, free of charge, to any person obtaining a copy
--  of this software and associated documentation files (the "Software"), to deal
--  in the Software without restriction, including without limitation the rights
--  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
--  copies of the Software, and to permit persons to whom the Software is
--  furnished to do so, subject to the following conditions:
--  
--  The above copyright notice and this permission notice shall be included in all
--  copies or substantial portions of the Software.
--  
--  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
--  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
--  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
--  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
--  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
--  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
--  SOFTWARE.
--


-- ****************************************
-- This SQL script populates the initial data.
-- ****************************************

INSERT INTO users (id, name, email, username, password, enabled, locked, expired, credentials_expired) VALUES
   (1, 'admin',    'admin@somewhere.com',    'admin',    '$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW', true,  false, false, false),
   (2, 'noroles',  'noroles@somewhere.com',  'noroles',  '$2a$04$JXVnGr9TtIqum.vvpe/qsOyjsy2hkEVBZJEAv4NV7eQJisE4xH68a', true,  false, false, false),
   (3, 'locked',   'locked@somewhere.com',   'locked',   '$2a$04$JXVnGr9TtIqum.vvpe/qsOyjsy2hkEVBZJEAv4NV7eQJisE4xH68a', true,  true,  false, false),
   (4, 'expired',  'expired@somewhere.com',  'expired',  '$2a$04$JXVnGr9TtIqum.vvpe/qsOyjsy2hkEVBZJEAv4NV7eQJisE4xH68a', true,  false, true,  false),
   (5, 'disabled', 'disabled@somewhere.com', 'disabled', '$2a$04$JXVnGr9TtIqum.vvpe/qsOyjsy2hkEVBZJEAv4NV7eQJisE4xH68a', false, false, false, false),
   (6, 'expcreds', 'expcreds@somewhere.com', 'expcreds', '$2a$04$JXVnGr9TtIqum.vvpe/qsOyjsy2hkEVBZJEAv4NV7eQJisE4xH68a', true,  false, false, true),
   (7, 'noread',   'noread@somewhere.com',   'noread',   '$2a$04$JXVnGr9TtIqum.vvpe/qsOyjsy2hkEVBZJEAv4NV7eQJisE4xH68a', true,  false, false, false);

INSERT INTO roles (id, name) VALUES
   (1, 'ADMIN'),
   (2, 'USER'),
   (3, 'NO_READ');

INSERT INTO privileges (id, name) VALUES
   (1, 'CREATE_DATA'),
   (2, 'READ_DATA'),
   (3, 'UPDATE_DATA'),
   (4, 'DELETE_DATA');

INSERT INTO role_privileges (role_id, privilege_id) VALUES
   (1, 1),
   (1, 2),
   (1, 3),
   (1, 4),
   (2, 2),
   (3, 1),
   (3, 3),
   (3, 4);

INSERT INTO user_roles (user_id, role_id) VALUES
   (1, 1),
   (3, 1),
   (4, 1),
   (5, 1),
   (6, 1),
   (7, 3);
