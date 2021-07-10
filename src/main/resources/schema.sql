CREATE TABLE CHALLENGE (
                           id              BIGINT NOT NULL auto_increment,
                           mission_count   INT NOT NULL DEFAULT 0,
                           name            VARCHAR(40) NOT NULL,
                           create_date     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
                           PRIMARY KEY (id)
)default character set utf8mb4 collate utf8mb4_general_ci;


CREATE TABLE MISSION (
                         id             BIGINT NOT NULL auto_increment,
                         challenge_id   LONG NOT NULL,
                         mission_check  TINYINT(1) NOT NULL DEFAULT 0,
                         name           VARCHAR(100) NOT NULL,
                         memo           VARCHAR(600),
                         image          VARCHAR(500),
                         PRIMARY KEY (id),
                         FOREIGN KEY (challenge_id) REFERENCES CHALLENGE (id)
)default character set utf8mb4 collate utf8mb4_general_ci;


CREATE TABLE TAG (
                     id   BIGINT NOT NULL auto_increment,
                     text VARCHAR(100) NOT NULL,
                     PRIMARY KEY(id)
)default character set utf8mb4 collate utf8mb4_general_ci;


CREATE TABLE CHALLENGE_TAG (
                               challenge_id LONG NOT NULL,
                               tag_id       LONG NOT NULL,
                               FOREIGN KEY (challenge_id) REFERENCES CHALLENGE (id),
                               FOREIGN KEY (tag_id) REFERENCES TAG (id)
)default character set utf8mb4 collate utf8mb4_general_ci;

