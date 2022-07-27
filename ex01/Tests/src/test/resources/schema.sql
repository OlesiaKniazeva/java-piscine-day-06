CREATE TABLE IF NOT EXISTS shop (
    identifier   BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name         VARCHAR(100),
    price        INT,

    PRIMARY KEY (identifier)
);