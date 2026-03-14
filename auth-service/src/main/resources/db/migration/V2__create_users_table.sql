CREATE TABLE users (
    id VARCHAR(36) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ORG_ADMIN','ENGINEER','ANALYST') NOT NULL,
    organization_id VARCHAR(36) NOT NULL,
    created_at DATETIME NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT fk_users_organization FOREIGN KEY (organization_id)
        REFERENCES organizations(id)
);
