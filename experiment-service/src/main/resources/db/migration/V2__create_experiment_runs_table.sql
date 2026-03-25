CREATE TABLE experiment_runs (
    id VARCHAR(36) PRIMARY KEY,
    experiment_id VARCHAR(36) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'RUNNING',
    started_at DATETIME NOT NULL,
    completed_at DATETIME,
    CONSTRAINT fk_experiment FOREIGN KEY (experiment_id) REFERENCES experiments(id)
);
