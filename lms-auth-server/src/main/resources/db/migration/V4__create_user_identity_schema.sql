CREATE TABLE user_identities (

    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    provider VARCHAR(50) NOT NULL,
    subject VARCHAR(255) NOT NULL,

    CONSTRAINT fk_user_identities_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_user_identity_provider_subject UNIQUE (provider, subject),
    CONSTRAINT chk_user_identity_provider CHECK (provider IN ('LOCAL', 'MICROSOFT'))
);