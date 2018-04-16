DROP TABLE IF EXISTS album;
DROP TABLE IF EXISTS artist;

DROP SEQUENCE IF EXISTS artist_artist_id_seq;
DROP SEQUENCE IF EXISTS album_album_id_seq;

CREATE SEQUENCE artist_artist_id_seq start WITH 1 INCREMENT BY 1;
CREATE SEQUENCE album_album_id_seq start WITH 1 INCREMENT BY 1;


CREATE TABLE artist(artist_id INTEGER GENERATED BY DEFAULT AS SEQUENCE artist_artist_id_seq,
                    name VARCHAR(100) NOT NULL,
            PRIMARY KEY(artist_id));

CREATE TABLE album(album_id INTEGER GENERATED BY DEFAULT AS SEQUENCE album_album_id_seq,
                   artist_id INTEGER NOT NULL,
                   title VARCHAR(100) NOT NULL,
                   year VARCHAR(4) NOT NULL,
            PRIMARY KEY(album_id));


ALTER TABLE album ADD CONSTRAINT record_artist FOREIGN KEY (artist_id) REFERENCES artist;