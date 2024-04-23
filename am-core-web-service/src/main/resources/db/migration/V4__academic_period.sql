-- Crear la tabla academic_period

create table academic_period (
                                 id integer not null,
                                 year integer not null,
                                 name varchar(100) not null,
                                 start_date date not null,
                                 end_date date not null,
                                 active boolean not null,
                                 area_id integer,
                                 primary key (id)
);

-- Agregar la restricción de clave externa a la tabla academic_period
alter table academic_period add constraint fk_academic_period_ref_area foreign key (area_id)
    references area (id) on delete restrict on update restrict;

-- Crear la secuencia para academic_period
create sequence academic_period_sequence as integer increment 1;