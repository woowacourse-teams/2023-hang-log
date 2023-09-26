ALTER TABLE likes ADD CONSTRAINT unique_trip_member UNIQUE (trip_id, member_id);
