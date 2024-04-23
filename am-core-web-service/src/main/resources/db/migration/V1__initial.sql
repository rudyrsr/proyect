-- create area table
create table area (
                         id integer not null,
                         name varchar(100) not null,
                         initials varchar(100) not null,
                         active boolean not null,
                         primary key (id)
);

-- create a sequence for area
create sequence area_sequence as integer increment 1;

create table career (
                      id integer not null,
                      name varchar(100) not null,
                      initials varchar(100) not null,
                      description varchar(200) not null,
                      creation_date date,
                      active boolean not null,
                      area_id integer,
                      primary key (id)
);

alter table career add constraint fk_career_ref_area foreign key (area_id)
    references area (id) on delete restrict on update restrict;

create sequence career_sequence as integer increment 1;

