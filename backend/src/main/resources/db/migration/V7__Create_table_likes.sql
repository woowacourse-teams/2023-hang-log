CREATE TABLE IF NOT EXISTS likes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    trip_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (trip_id) REFERENCES trip(trip_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id),
    CONSTRAINT unique_trip_member UNIQUE (trip_id, member_id)
) engine=InnoDB;
