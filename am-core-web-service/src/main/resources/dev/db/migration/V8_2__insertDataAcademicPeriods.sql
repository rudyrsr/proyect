INSERT INTO academic_period (id, year, name, start_date, end_date, active, area_id) VALUES
                                                                                        (1, 2024, '1 - Primer Semestre', '2024-02-12', '2024-06-12', true, 1),
                                                                                        (2, 2024, '2 - Segundo Semestre', '2024-08-12', '2024-12-12', true, 1),
                                                                                        (3, 2024, 'Invierno', '2024-06-30', '2024-08-12', true, 1),
                                                                                        (4, 2025, 'Verano', '2025-01-02', '2025-02-12', true, 1);
SELECT setval('academic_period_sequence', 4, true);