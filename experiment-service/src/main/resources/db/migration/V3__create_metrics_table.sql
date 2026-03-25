CREATE TABLE metrics (
    id VARCHAR(36) PRIMARY KEY,
    run_id VARCHAR(36) NOT NULL,
    metric_name VARCHAR(255) NOT NULL,
    metric_value DOUBLE NOT NULL,
    recorded_at DATETIME NOT NULL,
    CONSTRAINT fk_run FOREIGN KEY (run_id) REFERENCES experiment_runs(id)
);
