CREATE TABLE system_configuration (
              id UUID PRIMARY KEY,
              setup_completed BOOLEAN NOT NULL DEFAULT FALSE,
              created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
              updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);