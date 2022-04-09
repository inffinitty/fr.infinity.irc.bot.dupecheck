CREATE TABLE pre (
                     id integer NOT NULL primary key,
                     name varchar(512) NOT NULL,
                     team varchar(32) DEFAULT NULL,
                     cat varchar(32) DEFAULT NULL,
                     genre varchar(256) DEFAULT NULL,
                     url varchar(512) DEFAULT NULL,
                     size float DEFAULT NULL,
                     files integer DEFAULT NULL,
                     source varchar(64) DEFAULT NULL,
                     added_at timestamp NOT NULL DEFAULT current_timestamp,
                     pre_at timestamp NULL DEFAULT NULL
);

-- CREATE SEQUENCE FOR IDS
CREATE SEQUENCE IF NOT EXISTS pre_id_seq
    START 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
