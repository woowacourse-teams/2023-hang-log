CREATE TABLE IF NOT EXISTS category (
    created_at DATETIME(6) NOT NULL,
    id BIGINT NOT NULL,
    modified_at DATETIME(6) NOT NULL,
    eng_name VARCHAR(50) NOT NULL,
    kor_name VARCHAR(50) NOT NULL,
    status ENUM ('DELETED','USABLE') NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS city (
    latitude DECIMAL(16,13) NOT NULL,
    longitude DECIMAL(16,13) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    modified_at DATETIME(6) NOT NULL,
    country VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    status ENUM ('DELETED','USABLE') NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS currency (
    chf FLOAT(53) NOT NULL,
    cny FLOAT(53) NOT NULL,
    date DATE NOT NULL UNIQUE,
    eur FLOAT(53) NOT NULL,
    gbp FLOAT(53) NOT NULL,
    hkd FLOAT(53) NOT NULL,
    jpy FLOAT(53) NOT NULL,
    krw FLOAT(53) NOT NULL,
    sgd FLOAT(53) NOT NULL,
    thb FLOAT(53) NOT NULL,
    usd FLOAT(53) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS member (
    created_at DATETIME(6),
    id BIGINT NOT NULL AUTO_INCREMENT,
    last_login_date DATETIME(6) NOT NULL,
    modified_at DATETIME(6),
    nickname VARCHAR(20) NOT NULL,
    social_login_id VARCHAR(30) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    status ENUM ('ACTIVE','DELETED','DORMANT'),
    PRIMARY KEY (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS trip (
    end_date DATE NOT NULL,
    start_date DATE NOT NULL,
    created_at DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT,
    modified_at DATETIME(6) NOT NULL,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    status ENUM ('DELETED','USABLE') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS day_log (
    ordinal INTEGER NOT NULL,
    created_at DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    modified_at DATETIME(6) NOT NULL,
    trip_id BIGINT NOT NULL,
    title VARCHAR(50) NOT NULL,
    status ENUM ('DELETED','USABLE') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (trip_id) REFERENCES trip (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS expense (
    amount DECIMAL(38,3),
    category_id BIGINT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    modified_at DATETIME(6) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    status ENUM ('DELETED','USABLE') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS place (
    latitude DECIMAL(16,13) NOT NULL,
    longitude DECIMAL(16,13) NOT NULL,
    category_id BIGINT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    modified_at DATETIME(6) NOT NULL,
    name VARCHAR(255) NOT NULL,
    status ENUM ('DELETED','USABLE') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS item (
    ordinal INTEGER NOT NULL,
    rating FLOAT(53),
    created_at DATETIME(6) NOT NULL,
    day_log_id BIGINT NOT NULL,
    expense_id BIGINT UNIQUE,
    id BIGINT NOT NULL AUTO_INCREMENT,
    modified_at DATETIME(6) NOT NULL,
    place_id BIGINT UNIQUE,
    title VARCHAR(50) NOT NULL,
    item_type ENUM ('NON_SPOT','SPOT') NOT NULL,
    memo VARCHAR(255),
    status ENUM ('DELETED','USABLE') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (day_log_id) REFERENCES day_log (id),
    FOREIGN KEY (expense_id) REFERENCES expense (id),
    FOREIGN KEY (place_id) REFERENCES place (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS image (
    created_at DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    item_id BIGINT,
    modified_at DATETIME(6) NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    status ENUM ('DELETED','USABLE') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (item_id) REFERENCES item (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS refresh_token (
    member_id BIGINT NOT NULL UNIQUE,
    token VARCHAR(255) NOT NULL,
    PRIMARY KEY (token)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS shared_trip (
    id BIGINT NOT NULL AUTO_INCREMENT,
    trip_id BIGINT NOT NULL UNIQUE,
    shared_code VARCHAR(255) NOT NULL,
    shared_status ENUM ('SHARED','UNSHARED') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (trip_id) REFERENCES trip (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS trip_city (
    city_id BIGINT,
    created_at DATETIME(6) NOT NULL,
    id BIGINT NOT NULL AUTO_INCREMENT,
    modified_at DATETIME(6) NOT NULL,
    trip_id BIGINT,
    status ENUM ('DELETED','USABLE') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (city_id) REFERENCES city (id),
    FOREIGN KEY (trip_id) REFERENCES trip (id)
) engine=InnoDB;
