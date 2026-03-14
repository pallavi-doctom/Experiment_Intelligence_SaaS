CREATE TABLE organizations (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    subscription_tier ENUM('FREE','PRO','ENTERPRISE') NOT NULL DEFAULT 'FREE',
    created_at DATETIME NOT NULL,
    CONSTRAINT pk_organizations PRIMARY KEY (id),
    CONSTRAINT uq_organizations_name UNIQUE (name)
);
