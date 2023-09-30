CREATE TABLE instrument_category (
    instrument_category_id VARCHAR(40) NOT NULL,
    name                   VARCHAR(40) NOT NULL,
    PRIMARY KEY (instrument_category_id),
    UNIQUE (name)
);

CREATE TABLE instrument (
    instrument_id           VARCHAR(40) NOT NULL,
    name                    VARCHAR(20) NOT NULL,
    category_id             VARCHAR(40) NOT NULL,
    primary_school_degree   BOOLEAN NOT NULL,
    secondary_school_degree BOOLEAN NOT NULL,
    PRIMARY KEY (instrument_id),
    UNIQUE (name),
    CONSTRAINT fk_instrument_instrument_category
        FOREIGN KEY (category_id)
            REFERENCES instrument_category (instrument_category_id)
);