--crear la tabla academic_period
create table classroom(
                        id integer not null,
                        initials varchar(10) not null,
                        name varchar(100) not null,
                        type varchar(15) not null,
                        address varchar(100) not null,
                        active boolean not null,
                        area_id integer,
                        primary key(id)
);
--crear la secuencia para classroom
create sequence classroom_sequence as integer increment 1;


--agregar la restricion de clave externa a la tabla classroom
alter table classroom add constraint fk_classroom_ref_area foreign key (area_id)
    references area (id) on delete restrict on update restrict;