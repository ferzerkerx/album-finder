drop sequence if exists artist_artist_id_seq;
drop sequence if exists album_album_id_seq;
create sequence artist_artist_id_seq start with 1 increment by 1;
create sequence album_album_id_seq start with 1 increment by 1;

DROP TABLE IF EXISTS album;
DROP TABLE IF EXISTS artist;

CREATE TABLE artist(artist_id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1),
                    name VARCHAR(100) NOT NULL,
            PRIMARY KEY(artist_id));

CREATE TABLE album(album_id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1),
                   artist_id INTEGER NOT NULL,
                   title VARCHAR(100) NOT NULL,
                   year VARCHAR(4) NOT NULL,
            PRIMARY KEY(album_id));


ALTER TABLE album ADD CONSTRAINT record_artist FOREIGN KEY (artist_id) REFERENCES artist;