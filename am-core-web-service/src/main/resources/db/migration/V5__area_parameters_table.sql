create table area_parameters (
                        area_id integer not null primary key,
                        monday_schedule boolean not null,
                        tuesday_schedule boolean not null,
                        wednesday_schedule boolean not null,
                        thursday_schedule boolean not null,
                        friday_schedule boolean not null,
                        saturday_schedule boolean not null,
                        sunday_schedule boolean not null,
                        start_time_schedule time not null,
                        end_time_schedule time not null,
                        time_interval_schedule smallint not null
);

alter table area_parameters add constraint fk_area_parameters_ref_area foreign key (area_id)
    references area (id) on delete restrict on update restrict;
