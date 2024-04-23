insert into area (id, name, initials, active) values (1,'Faculatad de Ciencias y Tecnología', 'FCyT', true),
                                                     (2,'Faculatad de Ciencias Económicas','FCE', true);
SELECT setval('area_sequence', 2, true);  -- next value will be 3