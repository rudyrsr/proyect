create table itinerary
(
    id        integer      not null,
    career_id integer,
    name      varchar(100) not null,
    active    boolean      not null,
    primary key (id)
);


alter table itinerary add constraint fk_itinerary_ref_career foreign key (career_id)
    references career (id) on delete restrict on update restrict;

create sequence itinerary_sequence as integer increment 1;