CREATE TABLE transaction (
                             id SERIAL PRIMARY KEY,
                             account_from BIGINT,
                             account_to BIGINT,
                             currency_shortname VARCHAR(3),
                             sum DECIMAL(19, 2),
                             expense_category VARCHAR(50),
                             datetime TIMESTAMP,
                             limit_exceeded BOOLEAN
);

CREATE TABLE expense_limit (
                               id SERIAL PRIMARY KEY,
                               limit_sum DECIMAL(19, 2),
                               limit_datetime TIMESTAMP,
                               limit_currency_shortname VARCHAR(3),
                               expense_category VARCHAR(50),
                               user_id BIGINT
);

CREATE TABLE currency_rate (
                               id SERIAL PRIMARY KEY,
                               currency_pair VARCHAR(7),
                               date DATE,
                               rate DECIMAL(19, 2)
);