
CREATE TABLE IF NOT EXISTS admin_member (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(20) NOT NULL,
    password VARCHAR(64) NOT NULL,
    last_login_date DATETIME(6) NOT NULL,
    admin_type ENUM ('ADMIN','MASTER'),
    status ENUM ('ACTIVE','DELETED','DORMANT'),
    created_at DATETIME(6),
    modified_at DATETIME(6),
    PRIMARY KEY (id)
    ) engine=InnoDB;
