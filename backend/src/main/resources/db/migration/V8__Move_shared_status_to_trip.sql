ALTER TABLE trip ADD COLUMN shared_status ENUM ('SHARED','UNSHARED');
UPDATE trip SET shared_status = 'UNSHARED';
ALTER TABLE trip MODIFY COLUMN shared_status ENUM ('SHARED','UNSHARED') NOT NULL;

CREATE TABLE copy_shared_trip AS SELECT * FROM shared_trip;

UPDATE trip INNER JOIN shared_trip ON trip.id = shared_trip.trip_id
SET trip.shared_status = shared_trip.shared_status;

ALTER TABLE shared_trip DROP COLUMN shared_status;
