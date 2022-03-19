--
--  The MIT License (MIT)
--
--  Copyright (c) 2017 Bernardo Martínez Garrido
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

INSERT INTO USERS (name, password, enabled, locked, expired, CREDENTIALS_EXPIRED) VALUES
   ('admin',      '$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW',   true,    false,   false,   false),
   ('noroles',    '$2a$04$JXVnGr9TtIqum.vvpe/qsOyjsy2hkEVBZJEAv4NV7eQJisE4xH68a',   true,    false,   false,   false),
   ('locked',     '$2a$04$JXVnGr9TtIqum.vvpe/qsOyjsy2hkEVBZJEAv4NV7eQJisE4xH68a',   true,    true,    false,   false),
   ('expired',    '$2a$04$JXVnGr9TtIqum.vvpe/qsOyjsy2hkEVBZJEAv4NV7eQJisE4xH68a',   true,    false,   true,    false),
   ('disabled',   '$2a$04$JXVnGr9TtIqum.vvpe/qsOyjsy2hkEVBZJEAv4NV7eQJisE4xH68a',   false,   false,   false,   false),
   ('expCreds',   '$2a$04$JXVnGr9TtIqum.vvpe/qsOyjsy2hkEVBZJEAv4NV7eQJisE4xH68a',   true,    false,   false,   true);

INSERT INTO ROLES (name) VALUES
   ('ADMIN'),
   ('USER');

INSERT INTO PRIVILEGES (name) VALUES
   ('CREATE_USER'),
   ('READ_USER'),
   ('UPDATE_USER'),
   ('DELETE_USER');

INSERT INTO ROLE_PRIVILEGES (role_id, privilege_id) VALUES
   (1, 1),
   (1, 2),
   (1, 3),
   (1, 4),
   (2, 2);

INSERT INTO USER_ROLES (user_id, role_id) VALUES
   (1, 1),
   (3, 1),
   (4, 1),
   (5, 1);
