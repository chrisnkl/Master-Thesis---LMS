CREATE TABLE users(
    id UUID PRIMARY KEY,
    username VARCHAR(24) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles(
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE permissions(
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE role_permissions(
    role_id UUID NOT NULL,
    permission_id UUID NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_permissions_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_permissions_permission FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);


-- ============================================================
-- ADMIN USER / ROLE / PERMISSIONS
-- Development seed data
-- ============================================================

INSERT INTO users (
    id,
    username,
    email,
    password_hash
) VALUES (
             '11111111-1111-1111-1111-111111111111',
             'admin',
             'admin@lms.local',
             '$2a$14$PEQ1YgLXq1E8i5T3MOf7G.BBG11nICs8nPES7uS.ULU/OOqi.Hh7e'
         );

INSERT INTO roles (
    id,
    name
) VALUES (
             '22222222-2222-2222-2222-222222222222',
             'ADMIN'
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
