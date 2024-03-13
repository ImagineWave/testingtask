INSERT INTO person (
    login,
    pass_hash,
    first_name,
    last_name,
    patronymic,
    birth_date
) SELECT 'admin', '$2a$08$OQrCVKD2tsV/Xg2jsBqtN.BrjhP./clTgujql5DToSAt5VaRR8vee', 'Иван', 'Иванов', 'Иванович', '1984-09-11'
WHERE NOT EXISTS (
        SELECT login FROM person WHERE login = 'admin'
    );

INSERT INTO email (
    person_id,
    email_address,
    valid
) SELECT '1', 'admin@mail.ru', 'true'
WHERE NOT EXISTS (
        SELECT email_address FROM email WHERE email_address = 'admin@mail.ru'
    );
INSERT INTO phone (
    person_id,
    phone_number,
    valid
) SELECT '1', '+78005553535', 'true'
WHERE NOT EXISTS (
        SELECT phone_number FROM phone WHERE phone_number = 'admin@mail.ru'
    );