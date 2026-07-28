CREATE TABLE accounts (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20)  NOT NULL,

    CONSTRAINT account_type_check CHECK (type IN ('checking', 'savings', 'credit'))
);

CREATE TABLE categories (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(20) NOT NULL,

    CONSTRAINT category_type_check CHECK (type IN ('INCOME', 'EXPENSE'))
);

CREATE TABLE transactions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES accounts(id),
    category_id BIGINT NOT NULL REFERENCES categories(id),
    amount NUMERIC(12, 2) NOT NULL,
    description VARCHAR(255),
    occurred_at TIMESTAMP NOT NULL
);

CREATE TABLE budgets (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    category_id  BIGINT NOT NULL REFERENCES categories(id),
    month        DATE   NOT NULL,
    limit_amount NUMERIC(12, 2) NOT NULL,

    CONSTRAINT budget_month_is_first_of_month CHECK (EXTRACT(DAY FROM month) = 1)
);