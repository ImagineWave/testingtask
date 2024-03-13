CREATE TABLE IF NOT EXISTS person (
    person_id SERIAL PRIMARY KEY,                              -- идентификатор
    login VARCHAR(128) NOT NULL UNIQUE,                        -- логин
    pass_hash VARCHAR(256) NOT NULL,                           -- хеш пароля
    first_name VARCHAR(128) NOT NULL,                          -- Имя
    last_name VARCHAR(128) NOT NULL,                           -- Фамилия
    patronymic VARCHAR(128),                                   -- Отчество
    birth_date DATE NOT NULL                                   -- дата рождения
);
CREATE TABLE IF NOT EXISTS email (
    email_id SERIAL PRIMARY KEY,                               -- идентификатор email'а
    person_id BIGINT NOT NULL REFERENCES person (person_id),   -- id человека
    email_address VARCHAR(256) NOT NULL UNIQUE,                -- Сам email
    valid boolean NOT NULL                                     -- подтвержден ли email
);
CREATE TABLE IF NOT EXISTS phone (
    phone_id SERIAL PRIMARY KEY,                               -- идентификатор телефона
    person_id BIGINT NOT NULL REFERENCES person (person_id),   -- id человека
    phone_number VARCHAR(256) NOT NULL UNIQUE,                 -- Сам номер телефона
    valid boolean NOT NULL                                     -- подтвержден ли email
);
CREATE TABLE IF NOT EXISTS bank_account (
    bank_account_id SERIAL PRIMARY KEY,                        -- идентификатор аккаунта
    account_number VARCHAR(256) NOT NULL UNIQUE,               -- номер счета
    person_id BIGINT NOT NULL REFERENCES person (person_id),   -- id человека
    init_balance BIGINT NOT NULL DEFAULT 0,                    -- Стартовый баланс от которого идет расчет максимума
    balance BIGINT NOT NULL DEFAULT 0 CHECK (balance >= 0)     -- текущий баланс
);
CREATE TABLE IF NOT EXISTS transaction (
    transaction_id VARCHAR(256) PRIMARY KEY,                   -- идентификатор транзакции (UUID)
    receiver_account_no VARCHAR(256) NOT NULL,                 -- номер счета получателя
    sender_account_no VARCHAR(256),                            -- номер счета отправителя (null, если отправитель банк)
    amount BIGINT NOT NULL                                     -- количество средств
);