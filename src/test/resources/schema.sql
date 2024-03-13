CREATE TABLE IF NOT EXISTS person (
    person_id SERIAL PRIMARY KEY,        -- идентификатор
    login VARCHAR(128) NOT NULL UNIQUE,  -- логин
    pass_hash VARCHAR(256) NOT NULL,     -- хеш пароля
    first_name VARCHAR(128) NOT NULL,    -- Имя
    last_name VARCHAR(128) NOT NULL,     -- Фамилия
    patronymic VARCHAR(128),             -- Отчество
    birth_date DATE NOT NULL             -- дата рождения
);
CREATE TABLE IF NOT EXISTS email (
    email_id SERIAL PRIMARY KEY,                               -- идентификатор email'а
    person_id BIGINT NOT NULL REFERENCES person (person_id),   -- id человека
    email_address VARCHAR(256) NOT NULL UNIQUE,                -- Сам email
    valid boolean NOT NULL                                     -- подтвержден ли email
);