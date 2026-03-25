CREATE TABLE experiments (
    id VARCHAR(36) PRIMARY KEY,
    organization_id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_by VARCHAR(36) NOT NULL,
    created_at DATETIME NOT NULL
);
