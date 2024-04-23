-- Crear la tabla subject

create table subject (
                        id integer not null,
                        name varchar(100) not null,
                        initials varchar(100) not null,
                        active boolean not null,
                        area_id integer,
                        primary key (id)
);
-- Agregar la restricción de clave externa a la tabla subject
alter table subject add constraint fk_subject_ref_area foreign key (area_id)
    references area (id) on delete restrict on update restrict;

-- Crear la secuencia para subject
create sequence subject_sequence as integer increment 1;



