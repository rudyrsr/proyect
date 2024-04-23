--crear_tabla_professor
CREATE TABLE professor (
                           id integer not null,
                           name VARCHAR(70) not null,
                           last_name VARCHAR(70) not null,
                           second_last_name VARCHAR(70),
                           primary key (id)
);

create sequence professor_sequence as integer increment 1;

--tabla Curriculum
CREATE TABLE curriculum (
                            id SMALLINT PRIMARY KEY NOT NULL,
                            name VARCHAR(100) NOT NULL,
                            min_approved_subjects SMALLINT NOT NULL,
                            start_date DATE NOT NULL,
                            end_date DATE,
                            active BOOLEAN NOT NULL,
                            career_id INTEGER REFERENCES career(id)
);

create sequence curriculum_sequence as integer increment 1;

-- Agregar la restricción de clave externa a la tabla subject
alter table curriculum add constraint fk_curriculum_ref_career foreign key (career_id)
    references career (id) on delete restrict on update restrict;

--crear_tabla_subject_curriculum
CREATE TABLE subject_curriculum (
                                    curriculum_id INTEGER,
                                    subject_id INTEGER,
                                    level SMALLINT NOT NULL,
                                    optional BOOLEAN NOT NULL,
                                    path VARCHAR(50),
                                    workload SMALLINT,
                                    active BOOLEAN NOT NULL,
                                    PRIMARY KEY (curriculum_id, subject_id)
);
-- constrain de subject_curriculum
alter table subject_curriculum  add constraint fk_subject_curriculum_ref_subject foreign key (subject_id)
    references subject (id) on delete restrict on update restrict;


alter table subject_curriculum  add constraint fk_subject_curriculum_ref_curriculum foreign key (curriculum_id)
    references curriculum (id) on delete restrict on update restrict;

--crear_tabla_grupo
CREATE TABLE group_itinerary (
                                 id integer not null,
                                 identifier VARCHAR(10) not null ,
                                 remark VARCHAR(100),
                                 curriculum_id INTEGER,
                                 subject_id INTEGER,
                                 itinerary_id INTEGER,
                                 primary key (id)
);

create sequence group_itinerary_sequence as integer increment 1;

alter table group_itinerary add constraint fk_group_itinerary_ref_itinerary foreign key (itinerary_id)
    references itinerary (id) on delete restrict on update restrict;

ALTER TABLE group_itinerary ADD CONSTRAINT fk_group_itinerary_ref_subject_curriculum
    FOREIGN KEY (curriculum_id, subject_id)
        REFERENCES subject_curriculum (curriculum_id, subject_id);

--crear_tabla_schedule
CREATE TABLE schedule_itinerary (
                                    id integer not null,
                                    start_time TIME,
                                    end_time TIME,
                                    weekday SMALLINT,
                                    assistant VARCHAR(100),
                                    classroom_id INTEGER,
                                    professor_id INTEGER,
                                    group_itinerary_id INTEGER,
                                    primary key (id)
);

create sequence schedule_itinerary_sequence as integer increment 1;

alter table schedule_itinerary add constraint fk_schedule_itinerary_ref_classroom foreign key (classroom_id)
    references classroom (id) on delete restrict on update restrict;

alter table schedule_itinerary add constraint fk_schedule_ref_professor foreign key (professor_id)
    references professor (id) on delete restrict on update restrict;

alter table schedule_itinerary add constraint fk_schedule_ref_group foreign key (group_itinerary_id)
    references group_itinerary (id) on delete restrict on update restrict;