
DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id BINARY(16) NOT NULL,
    username VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email_address VARCHAR(255) NOT NULL
    PRIMARY KEY (id),
    UNIQUE (username),
    UNIQUE (email_address)
);

DROP TABLE IF EXISTS user_password;
CREATE TABLE user_password(
    id BINARY(16) NOT NULL,
    password VARBINARY(255) NOT NULL,
    salt VARBINARY(64) NOT NULL,
    effective_datetime DATETIME NOT NULL,
    ineffective_datetime DATETIME,
    local_user_id BINARY(16),
    PRIMARY KEY (id),
    FOREIGN KEY (local_user_id) REFERENCES local_users(id) ON DELETE CASCADE
);

