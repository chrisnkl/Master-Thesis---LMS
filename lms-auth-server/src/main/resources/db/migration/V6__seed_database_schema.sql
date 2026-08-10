INSERT INTO system_configuration (
    id,
    setup_completed
)
VALUES (
           '00000000-0000-0000-0000-000000000001',
           FALSE
       );


INSERT INTO users (
    id,
    username,
    email,
    password_hash
) VALUES (
             '11111111-1111-1111-1111-111111111111',
             'admin',
             'admin@lms.com',
             null
         );

INSERT INTO roles (
    id,
    name
) VALUES (
             '22222222-2222-2222-2222-222222222221',
             'ADMIN'
         );

INSERT INTO roles (
    id,
    name
) VALUES (
             '22222222-2222-2222-2222-222222222222',
             'USER'
         );

INSERT INTO permissions (
    id,
    name
) VALUES
      ('33333333-3333-3333-3333-333333333331', 'USER_READ'),
      ('33333333-3333-3333-3333-333333333332', 'USER_WRITE'),
      ('33333333-3333-3333-3333-333333333333', 'USER_DELETE'),
      ('33333333-3333-3333-3333-333333333334', 'ROLE_READ'),
      ('33333333-3333-3333-3333-333333333335', 'ROLE_WRITE'),
      ('33333333-3333-3333-3333-333333333336', 'COURSE_READ'),
      ('33333333-3333-3333-3333-333333333337', 'COURSE_WRITE'),
      ('33333333-3333-3333-3333-333333333338', 'COURSE_DELETE');

-- Assign ADMIN role to admin user
INSERT INTO user_roles (
    user_id,
    role_id
) VALUES (
             '11111111-1111-1111-1111-111111111111',
             '22222222-2222-2222-2222-222222222222'
         );

-- Assign all permissions to ADMIN
INSERT INTO role_permissions (
    role_id,
    permission_id
)
SELECT
    '22222222-2222-2222-2222-222222222222',
    id
FROM permissions;
