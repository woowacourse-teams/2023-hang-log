DROP TABLE IF EXISTS likes;

CREATE TABLE likes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    trip_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_trip_member UNIQUE (trip_id, member_id)
) engine=InnoDB;
