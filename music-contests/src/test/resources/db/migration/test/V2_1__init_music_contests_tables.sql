CREATE TABLE address (
    address_id      VARCHAR(40) NOT NULL,
    country         VARCHAR(40) NOT NULL,
    city            VARCHAR(40) NOT NULL,
    postal_code     VARCHAR(20) NOT NULL,
    street          VARCHAR(40) NOT NULL,
    building_number VARCHAR(20),
    additional_info TEXT,
    PRIMARY KEY (address_id)
);
CREATE TABLE music_school (
    music_school_id  VARCHAR(40) NOT NULL,
    name             TEXT        NOT NULL,
    patron           VARCHAR(60),
    primary_degree   BOOLEAN     NOT NULL,
    secondary_degree BOOLEAN     NOT NULL,
    address_id       VARCHAR(40) NOT NULL,
    PRIMARY KEY (music_school_id),
    CONSTRAINT fk_music_school_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
);
CREATE TABLE headmaster (
    headmaster_id       VARCHAR(40) NOT NULL,
    name                VARCHAR(30) NOT NULL,
    surname             VARCHAR(40) NOT NULL,
    email               VARCHAR(60) NOT NULL,
    pesel               VARCHAR(60) NOT NULL,
    music_school_id     VARCHAR(40) NOT NULL,
    PRIMARY KEY (headmaster_id),
    UNIQUE (pesel),
    CONSTRAINT fk_headmaster_music_school
        FOREIGN KEY (music_school_id)
            REFERENCES music_school (music_school_id)
);
CREATE TABLE teacher (
    teacher_id          VARCHAR(40) NOT NULL,
    name                VARCHAR(30) NOT NULL,
    surname             VARCHAR(40) NOT NULL,
    email               VARCHAR(60) NOT NULL,
    pesel               VARCHAR(60) NOT NULL,
    instrument          VARCHAR(40) NOT NULL,
    music_school_id     VARCHAR(40) NOT NULL,
    PRIMARY KEY (teacher_id),
    UNIQUE (pesel),
    CONSTRAINT fk_teacher_music_school
        FOREIGN KEY (music_school_id)
            REFERENCES music_school (music_school_id)
);
CREATE TABLE student (
    student_id              VARCHAR(40) NOT NULL,
    name                    VARCHAR(30) NOT NULL,
    surname                 VARCHAR(40) NOT NULL,
    email                   VARCHAR(60) NOT NULL,
    pesel                   VARCHAR(60) NOT NULL,
    class                   VARCHAR(10) NOT NULL,
    education_duration      VARCHAR(10) NOT NULL,
    music_school_degree     VARCHAR(10) NOT NULL,
    music_school_id         VARCHAR(40) NOT NULL,
    main_instrument         VARCHAR(40) NOT NULL,
    second_instrument       VARCHAR(40),
    teacher_id              VARCHAR(40) NOT NULL,
    PRIMARY KEY (student_id),
    UNIQUE (pesel),
    CONSTRAINT fk_student_music_school
        FOREIGN KEY (music_school_id)
            REFERENCES music_school (music_school_id),
    CONSTRAINT fk_student_teacher
        FOREIGN KEY (teacher_id)
            REFERENCES teacher (teacher_id)
);
CREATE TABLE competition_location (
    competition_location_id VARCHAR(40) NOT NULL,
    name                    TEXT        NOT NULL,
    address_id              VARCHAR(40) NOT NULL,
    PRIMARY KEY (competition_location_id),
    CONSTRAINT fk_competition_location_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
);
CREATE TABLE competition (
    competition_id           VARCHAR(40)              NOT NULL,
    name                     TEXT                     NOT NULL,
    instrument               VARCHAR(40)              NOT NULL,
    online		             BOOLEAN                  NOT NULL,
    primary_degree           BOOLEAN                  NOT NULL,
    secondary_degree         BOOLEAN                  NOT NULL,
    beginning_date_time      TIMESTAMP WITH TIME ZONE NOT NULL,
    end_date_time      TIMESTAMP WITH TIME ZONE NOT NULL,
    result_announcement      TIMESTAMP WITH TIME ZONE NOT NULL,
    application_deadline     TIMESTAMP WITH TIME ZONE NOT NULL,
    requirements_description TEXT                     NOT NULL,
    organizer_id             VARCHAR(40)              NOT NULL,
    location_id              VARCHAR(40)              NOT NULL,
    finished		         BOOLEAN                  NOT NULL,
    PRIMARY KEY (competition_id),
    CONSTRAINT fk_competition_headmaster
        FOREIGN KEY (organizer_id)
            REFERENCES headmaster (headmaster_id),
    CONSTRAINT fk_competition_competition_location
        FOREIGN KEY (location_id)
            REFERENCES competition_location (competition_location_id)
);
CREATE TABLE competition_result (
    competition_result_id VARCHAR(40) NOT NULL,
    competition_id        VARCHAR(40) NOT NULL,
    student_id            VARCHAR(40) NOT NULL,
    competition_place     VARCHAR(20) NOT NULL,
    special_award         TEXT,
    PRIMARY KEY (competition_result_id),
    CONSTRAINT fk_competition_result_competition
        FOREIGN KEY (competition_id)
            REFERENCES competition (competition_id),
    CONSTRAINT fk_competition_result_student
        FOREIGN KEY (student_id)
            REFERENCES student (student_id)
);
CREATE TABLE application_form (
    application_form_id VARCHAR(40) NOT NULL,
    competition_id      VARCHAR(40) NOT NULL,
    teacher_id          VARCHAR(40) NOT NULL,
    student_id          VARCHAR(40) NOT NULL,
    class_level         VARCHAR(10) NOT NULL,
    performance_pieces  TEXT        NOT NULL,
    PRIMARY KEY (application_form_id),
    CONSTRAINT fk_application_form_competition
        FOREIGN KEY (competition_id)
            REFERENCES competition (competition_id),
    CONSTRAINT fk_application_form_teacher
        FOREIGN KEY (teacher_id)
            REFERENCES teacher (teacher_id),
    CONSTRAINT fk_application_form_student
        FOREIGN KEY (student_id)
            REFERENCES student (student_id)
);