CREATE TABLE Influencer
(
    id            SERIAL PRIMARY KEY,
    ig_username   VARCHAR(255)        NOT NULL,
    first_name    VARCHAR(255)        NOT NULL,
    last_name     VARCHAR(255)        NOT NULL,
    contact       VARCHAR(255),
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    credit_limit  NUMERIC,
    dob           DATE,
    timestamp     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Business
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255)        NOT NULL,
    location      VARCHAR(255),
    contact       VARCHAR(255),
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    QR            VARCHAR(255),
    timestamp     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vTransactions
(
    id            SERIAL PRIMARY KEY,
    business_id   INT REFERENCES Business (id),
    influencer_id INT REFERENCES Influencer (id),
    amount        FLOAT NOT NULL,
    timestamp     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE MailList
(
    id        SERIAL PRIMARY KEY,
    email     VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);