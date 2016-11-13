CREATE KEYSPACE test WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor': 1};

drop TABLE test.songs;
CREATE TABLE test.songs (
  song_id uuid PRIMARY KEY,
  title text,
  album text,
  artist text
) WITH comment='song'
  AND COMPACT STORAGE
  AND read_repair_chance = 1.0;


drop TABLE test.songs_by_artist;
CREATE TABLE test.songs_by_artist (
  artist text PRIMARY KEY,
  song_id uuid ,
  title text,
  album text
) WITH comment='songs_by_artist'
  AND COMPACT STORAGE
  AND read_repair_chance = 1.0;