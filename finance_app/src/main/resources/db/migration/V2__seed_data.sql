-- Seed initial default accounts
INSERT INTO accounts (name, type) VALUES ('Checking Account', 'checking');
INSERT INTO accounts (name, type) VALUES ('Savings Account', 'savings');

-- Seed initial default categories
INSERT INTO categories (name, type) VALUES ('Groceries', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Salary', 'INCOME');
INSERT INTO categories (name, type) VALUES ('Rent', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Utilities', 'EXPENSE');
