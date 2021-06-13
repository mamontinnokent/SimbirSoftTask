set global time_zone = '+8:00';
create schema test_task;
USE test_task;

CREATE TABLE sites
(
    id        bigint NOT NULL AUTO_INCREMENT,
    url       varchar(500),
    PRIMARY KEY (id)
);

CREATE TABLE words
(
    site_id       bigint,
    word          varchar(40),
    count_of_word int
);
