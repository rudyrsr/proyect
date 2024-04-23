ALTER TABLE itinerary DROP COLUMN IF EXISTS career_id;
ALTER TABLE itinerary DROP CONSTRAINT IF EXISTS fk_itinerary_ref_career;

ALTER TABLE itinerary ADD COLUMN curriculum_id SMALLINT;
alter table itinerary add constraint fk_itinerary_ref_curriculum foreign key (curriculum_id)
    references curriculum (id) on delete restrict on update restrict;
