ALTER TABLE album ADD COLUMN user_id INTEGER;
INSERT INTO album ( album_name, user_id ) VALUES ('Nature', 1);
CREATE TABLE album_photo (id SERIAL PRIMARY KEY, album_id INTEGER, photo_id INTEGER);
INSERT INTO album_photo ( album_id, photo_id ) VALUES (1, 1);
INSERT INTO album_photo ( album_id, photo_id ) VALUES (2, 1);