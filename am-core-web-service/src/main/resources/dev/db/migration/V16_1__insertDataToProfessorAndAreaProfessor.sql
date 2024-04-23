INSERT INTO professor (id, name, last_name, second_last_name, email)
VALUES (1, 'Juan', 'Perez', 'Gomez', 'jperez@gmail.com'),
       (2, 'Maria', 'Lopez', NULL, 'mlopez@gmail.com');

SELECT setval('professor_sequence', 2, true);  -- next value will be 3

INSERT INTO area_professor (area_id, professor_id, creation_date, active)
VALUES (1, 1, now(), true),(1, 2, now(), true);