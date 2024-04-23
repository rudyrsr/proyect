alter table professor add email varchar(100) not null default '';

CREATE TABLE area_professor (
                            area_id INTEGER,
                            professor_id INTEGER,
                            creation_date date,
                            active bool not null,
                            PRIMARY KEY (area_id, professor_id)
);

alter table area_professor add constraint fk_curriculum_ref_area foreign key (area_id)
    references area (id) on delete restrict on update restrict;

alter table area_professor add constraint fk_curriculum_ref_professor foreign key (professor_id)
    references professor (id) on delete restrict on update restrict;