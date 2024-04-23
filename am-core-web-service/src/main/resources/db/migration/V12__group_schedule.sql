-- Crear tabla class_group
CREATE TABLE class_group (
                             id INTEGER NOT NULL ,
                             identifier VARCHAR(10) NOT NULL,
                             remark VARCHAR(100),
                             active BOOLEAN NOT NULL,
                             curriculum_id INTEGER,
                             subject_id INTEGER,
                             itinerary_id INTEGER,
                             academic_period_id INTEGER,
                             PRIMARY KEY (id)

);
create sequence class_group_sequence as integer increment 1;

alter table class_group add constraint fk_class_group_ref_itinerary foreign key (itinerary_id)
    references itinerary (id) on delete restrict on update restrict;

alter table class_group add constraint fk_academic_period_ref_itinerary foreign key (academic_period_id)
    references academic_period (id) on delete restrict on update restrict;

ALTER TABLE class_group ADD CONSTRAINT fk_class_group_ref_subject_curriculum
    FOREIGN KEY (curriculum_id, subject_id)
        REFERENCES subject_curriculum (curriculum_id, subject_id);

-- Crear tabla schedule
CREATE TABLE schedule (
                          id INTEGER NOT NULL ,
                          start_time TIME,
                          end_time TIME,
                          weekday SMALLINT,
                          assistant VARCHAR(100),
                          classroom_id INTEGER,
                          professor_id INTEGER,
                          group_id INTEGER,
                          primary key (id)
);

create sequence schedule_sequence as integer increment 1;

alter table schedule add constraint fk_schedule_ref_classroom foreign key (classroom_id)
    references classroom (id) on delete restrict on update restrict;

alter table schedule add constraint fk_schedule_ref_professsor foreign key (professor_id)
    references professor (id) on delete restrict on update restrict;

alter table schedule add constraint fk_schedule_ref_class_group foreign key (group_id)
    references class_group (id) on delete restrict on update restrict;