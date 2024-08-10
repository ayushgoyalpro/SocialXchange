-- Create Influencer table
CREATE TABLE Influencer
(
    id            SERIAL PRIMARY KEY,
    ig_username   VARCHAR(255)        NOT NULL,
    name          VARCHAR(255)        NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    mobile_number VARCHAR(20) UNIQUE  NOT NULL,
    password      VARCHAR(255)        NOT NULL,
    followers     INT,
    address       VARCHAR(255),
    lat           DOUBLE PRECISION,
    lon           DOUBLE PRECISION,
    dob           DATE,
    date_created  DATE DEFAULT CURRENT_DATE
);
-- Create Business table
CREATE TABLE Business
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255)        NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password      VARCHAR(255)        NOT NULL,
    business_type VARCHAR(50),
    ig_username   VARCHAR(255),
    address       VARCHAR(255),
    lat           DOUBLE PRECISION,
    lon           DOUBLE PRECISION,
    mobile_number VARCHAR(20),
    qr            VARCHAR(255),
    date_created  DATE DEFAULT CURRENT_DATE,
    status        VARCHAR(50)
);


-- Create Payment Card table
CREATE TABLE Payment_Card
(
    id             SERIAL PRIMARY KEY,
    influencer_id  INT REFERENCES Influencer (id),
    stripe_card_id VARCHAR(255) UNIQUE NOT NULL,
    last4          VARCHAR(4)          NOT NULL,
    brand          VARCHAR(50),
    exp_month      INT CHECK (exp_month BETWEEN 1 AND 12),
    exp_year       INT CHECK (exp_year >= EXTRACT(YEAR FROM CURRENT_DATE)),
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create SoCo Card table
CREATE TABLE SoCo_Card
(
    id            SERIAL PRIMARY KEY,
    influencer_id INT REFERENCES Influencer (id),
    phy_card_id   INT UNIQUE NOT NULL,
    status        VARCHAR(50),
    timestamp     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create SoCo Transaction table
CREATE TABLE SoCo_Transaction
(
    id                SERIAL PRIMARY KEY,
    influencer_id     INT REFERENCES Influencer (id),
    business_id       INT REFERENCES Business (id),
    soco_amount       FLOAT,
    balance_remaining FLOAT,
    soco_card_id      INT REFERENCES SoCo_Card (id),
    payment_type      VARCHAR(10) CHECK (payment_type IN ('qr', 'card')),
    timestamp         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Transaction table
CREATE TYPE transaction_status AS ENUM ('authorised', 'captured', 'cancelled', 'released');

CREATE TABLE Transaction
(
    id                       SERIAL PRIMARY KEY,
    soco_t_id                INT REFERENCES SoCo_Transaction (id),
    stripe_payment_intent_id VARCHAR(255) UNIQUE NOT NULL,
    amount                   FLOAT,
    fee                      FLOAT,
    status                   transaction_status,
    captured_amount          FLOAT,
    created_at               TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Balances table
CREATE TABLE Balances
(
    id              SERIAL PRIMARY KEY,
    influencer_id   INT REFERENCES Influencer (id),
    current_balance DOUBLE PRECISION,
    timestamp       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Activity table
CREATE TABLE Activity
(
    id                SERIAL PRIMARY KEY,
    influencer_id     INT REFERENCES Influencer (id),
    business_id       INT REFERENCES Business (id),
    content_published VARCHAR(255),
    amount            FLOAT,
    date_created      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Create Bank Details table
CREATE TABLE Bank_Details
(
    id                   SERIAL PRIMARY KEY,
    business_id          INT REFERENCES Business (id),
    bank_account_details VARCHAR(255) NOT NULL, -- Encrypted or tokenized for security
    date_created         DATE DEFAULT CURRENT_DATE
);

-- Create Category table
CREATE TABLE Category
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    follower_count INT
);

-- Create Spend Cap table
CREATE TABLE Spend_Cap
(
    id          SERIAL PRIMARY KEY,
    business_id INT REFERENCES Business (id),
    category_id INT REFERENCES Category (id),
    amount      FLOAT
);

-- Create Stats table
CREATE TABLE Stats
(
    id               SERIAL PRIMARY KEY,
    business_id      INT REFERENCES Business (id),
    influencer_count INT,
    promotions_count INT,
    audience_reached INT
);

-- Create Business_Transfer table
CREATE TABLE Business_Transfer
(
    id                 SERIAL PRIMARY KEY,
    transaction_id     INT REFERENCES Transaction (id),
    stripe_transfer_id VARCHAR(255) UNIQUE NOT NULL,
    business_id        INT REFERENCES Business (id),
    transfer_amount    FLOAT,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
