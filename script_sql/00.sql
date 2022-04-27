
CREATE SEQUENCE IF NOT EXISTS release_id_seq
    START 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE IF NOT EXISTS site_from_id_seq
    START 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE IF NOT EXISTS site_pre_id_seq
    START 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE IF NOT EXISTS site_exactinfo_id_seq
    START 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE IF NOT EXISTS site_fileinfo_id_seq
    START 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE IF NOT EXISTS site_file_id_seq
    START 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS quarantine_id_seq
    START 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS release_nuke_id_seq
    START 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



CREATE TABLE release (
                    rlz_id BIGINT NOT NULL DEFAULT nextval('release_id_seq') primary key,
                    rlz_name VARCHAR(500) NOT NULL,
                    rlz_name_lowercase VARCHAR(500) NOT NULL,
                    rlz_team VARCHAR(40) NOT NULL,
                    rlz_team_lowercase VARCHAR(40) NOT NULL
);
ALTER TABLE release ADD CONSTRAINT uk_release_name UNIQUE (rlz_name);

CREATE TABLE site_from (
                    from_id BIGINT NOT NULL DEFAULT nextval('site_from_id_seq') primary key,
                    from_nickname VARCHAR(25) NOT NULL,
                    from_chan VARCHAR(25) NOT NULL,
                    from_network VARCHAR(25) NOT NULL,
                    from_display_nickname VARCHAR(25) NOT NULL,
                    from_display_chan VARCHAR(25) NOT NULL,
                    from_display_network VARCHAR(25) NOT NULL
);
ALTER TABLE site_from ADD CONSTRAINT uk_site_from UNIQUE (from_nickname, from_chan, from_network);


CREATE TABLE site_pre (
                    pre_id BIGINT NOT NULL DEFAULT nextval('site_pre_id_seq') primary key,
                    pre_rlz_id BIGINT NOT NULL,
                    pre_from_id BIGINT NOT NULL,
                    pre_cat VARCHAR(15) NOT NULL DEFAULT 'UNKNOWN',
                    pre_pre_at timestamp NOT NULL DEFAULT current_timestamp,
                    pre_added_at timestamp NOT NULL DEFAULT current_timestamp
);
ALTER TABLE site_pre ADD CONSTRAINT fk_site_pre_id_release_id FOREIGN KEY(pre_rlz_id) REFERENCES release;
ALTER TABLE site_pre ADD CONSTRAINT fk_site_pre_id_site_from_id FOREIGN KEY(pre_from_id) REFERENCES site_from;
ALTER TABLE site_pre ADD CONSTRAINT uk_site_pre_release_id UNIQUE (pre_rlz_id);


CREATE TABLE site_exactinfo (
                    exactinfo_id BIGINT NOT NULL DEFAULT nextval('site_exactinfo_id_seq') primary key,
                    exactinfo_rlz_id BIGINT NOT NULL,
                    exactinfo_from_id BIGINT NOT NULL,
                    exactinfo_files integer DEFAULT NULL,
                    exactinfo_size float DEFAULT NULL,
                    exactinfo_added_at timestamp NOT NULL DEFAULT current_timestamp
);
ALTER TABLE site_exactinfo ADD CONSTRAINT fk_site_exactinfo_id_release_id FOREIGN KEY(exactinfo_rlz_id) REFERENCES release;
ALTER TABLE site_exactinfo ADD CONSTRAINT fk_site_exactinfo_id_site_from_id FOREIGN KEY(exactinfo_from_id) REFERENCES site_from;
ALTER TABLE site_exactinfo ADD CONSTRAINT uk_site_exactinfo_release_id UNIQUE (exactinfo_rlz_id);


CREATE TABLE site_fileinfo (
                    fileinfo_id BIGINT NOT NULL DEFAULT nextval('site_fileinfo_id_seq') primary key,
                    fileinfo_rlz_id BIGINT NOT NULL,
                    fileinfo_from_id BIGINT NOT NULL,
                    fileinfo_filename VARCHAR(500) NOT NULL,
                    fileinfo_size float DEFAULT NULL,
                    fileinfo_added_at timestamp NOT NULL DEFAULT current_timestamp
);
ALTER TABLE site_fileinfo ADD CONSTRAINT fk_site_fileinfo_id_release_id FOREIGN KEY(fileinfo_rlz_id) REFERENCES release;
ALTER TABLE site_fileinfo ADD CONSTRAINT fk_site_fileinfo_id_site_from_id FOREIGN KEY(fileinfo_from_id) REFERENCES site_from;
ALTER TABLE site_fileinfo ADD CONSTRAINT uk_site_fileinfo_release_id UNIQUE (fileinfo_rlz_id);

CREATE TABLE site_file (
                    file_id BIGINT NOT NULL DEFAULT nextval('site_file_id_seq') PRIMARY KEY,
                    file_rlz_id BIGINT NOT NULL,
                    file_from_id BIGINT NOT NULL,
                    file_binary BYTEA NOT NULL,
                    file_filename VARCHAR(500) NOT NULL,
                    file_filetype VARCHAR(15) NOT NULL,
                    file_crc32 VARCHAR(20) NOT NULL,
                    file_added_at timestamp NOT NULL DEFAULT current_timestamp
);
ALTER TABLE site_file ADD CONSTRAINT fk_site_file_id_release_id FOREIGN KEY(file_rlz_id) REFERENCES release;
ALTER TABLE site_file ADD CONSTRAINT fk_site_file_id_site_from_id FOREIGN KEY(file_from_id) REFERENCES site_from;
ALTER TABLE site_file ADD CONSTRAINT uk_site_file_release_id_file_filetype UNIQUE (file_rlz_id, file_filetype);



CREATE TABLE quarantine (
                           qua_id BIGINT NOT NULL DEFAULT nextval('quarantine_id_seq') PRIMARY KEY,
                           qua_commande VARCHAR(500) NOT NULL,
                           qua_error VARCHAR(75) NOT NULL,
                           qua_irc_commande VARCHAR(20) NOT NULL,
                           qua_from_nickname VARCHAR(25) NOT NULL,
                           qua_from_channel VARCHAR(25) NOT NULL,
                           qua_from_network VARCHAR(25) NOT NULL,
                           qua_added_at timestamp NOT NULL DEFAULT current_timestamp
);



-- Reset sequences
do $$
    begin
        PERFORM setval('release_id_seq', 1);
        PERFORM setval('site_from_id_seq', 1);
        PERFORM setval('site_pre_id_seq', 1);
        PERFORM setval('site_exactinfo_id_seq', 1);
        PERFORM setval('site_fileinfo_id_seq', 1);
        PERFORM setval('site_file_id_seq', 1);
        PERFORM setval('quarantine_id_seq', 1);
    end;
$$ language plpgsql;

CREATE SEQUENCE IF NOT EXISTS release_filter_id_seq
    START 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE release_filter (
                            rf_id BIGINT NOT NULL DEFAULT nextval('release_filter_id_seq') PRIMARY KEY,
                            rf_regex VARCHAR(255) NOT NULL,
                            rf_isactive BOOLEAN DEFAULT true
);

insert into release_filter (rf_regex) values ('^pre[.\-]');
insert into release_filter (rf_regex) values ('[.\-]pre$');
insert into release_filter (rf_regex) values ('[.\-]pre[.\-]');
insert into release_filter (rf_regex) values ('^test[.\-]');
insert into release_filter (rf_regex) values ('[.\-]test$');
insert into release_filter (rf_regex) values ('[.\-]test[.\-]');

CREATE TABLE release_nuke (
                                rn_id BIGINT NOT NULL DEFAULT nextval('release_nuke_id_seq') PRIMARY KEY,
                                rn_rlz_id BIGINT NOT NULL,
                                rn_from_id BIGINT NOT NULL,
                                rn_etat INTEGER NOT NULL,
                                rn_commentary VARCHAR(150) NOT NULL,
                                rn_network VARCHAR(50) NOT NULL,
                                rn_added_at TIMESTAMP NOT NULL  DEFAULT current_timestamp
);
ALTER TABLE release_nuke ADD CONSTRAINT fk_release_nuke_id_release_id FOREIGN KEY(rn_rlz_id) REFERENCES release;
ALTER TABLE release_nuke ADD CONSTRAINT fk_release_nuke_id_site_from_id FOREIGN KEY(rn_from_id) REFERENCES site_from;
