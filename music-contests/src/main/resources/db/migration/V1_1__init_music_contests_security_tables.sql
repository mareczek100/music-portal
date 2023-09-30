CREATE TABLE music_contests_role (
    role_id     UUID          NOT NULL,
    role        VARCHAR(20)   NOT NULL,
    PRIMARY KEY (role_id),
    UNIQUE (role)
);
CREATE TABLE music_contests_user
(
    user_id       UUID          NOT NULL,
    user_name     VARCHAR(60)   NOT NULL,
    password      TEXT          NOT NULL,
    active        BOOLEAN       NOT NULL,
    role_id       UUID          NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE (user_name),
    CONSTRAINT fk_music_contests_user_role
            FOREIGN KEY (role_id)
                REFERENCES music_contests_role (role_id)
);