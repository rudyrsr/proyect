ALTER TABLE career
    ADD COLUMN code varchar(10);

CREATE SEQUENCE career_code_seq;

ALTER TABLE career
    ALTER COLUMN code SET DEFAULT 'CAR-' || nextval('career_code_seq');

UPDATE career
SET code = DEFAULT
WHERE code IS NULL;

ALTER TABLE career
    ALTER COLUMN code SET NOT NULL;

CREATE UNIQUE INDEX idx_career_code ON career(code);

