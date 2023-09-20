CREATE TABLE IF NOT EXISTS `like` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    trip_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (trip_id) REFERENCES trip (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS published_trip (
    id BIGINT NOT NULL AUTO_INCREMENT,
    trip_id BIGINT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    modified_at DATETIME(6) NOT NULL,
    status ENUM ('DELETED','USABLE') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (trip_id) REFERENCES trip (id)
) engine=InnoDB;

ALTER TABLE trip ADD COLUMN published_status ENUM ('PUBLISHED','UNPUBLISHED');

UPDATE trip SET published_status = 'UNPUBLISHED';

ALTER TABLE trip MODIFY COLUMN published_status ENUM ('PUBLISHED','UNPUBLISHED') NOT NULL;
