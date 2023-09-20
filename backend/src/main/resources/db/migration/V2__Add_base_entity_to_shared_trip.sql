ALTER TABLE shared_trip ADD COLUMN created_at DATETIME(6);
ALTER TABLE shared_trip ADD COLUMN modified_at DATETIME(6);
ALTER TABLE shared_trip ADD COLUMN status ENUM ('DELETED','USABLE');

UPDATE shared_trip SET created_at = now(), modified_at = now(), status = 'USABLE';

ALTER TABLE shared_trip MODIFY COLUMN created_at DATETIME(6) NOT NULL;
ALTER TABLE shared_trip MODIFY COLUMN modified_at DATETIME(6) NOT NULL;
ALTER TABLE shared_trip MODIFY COLUMN status ENUM ('DELETED','USABLE') NOT NULL;
