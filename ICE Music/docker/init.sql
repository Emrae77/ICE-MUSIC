CREATE TABLE IF NOT EXISTS counter (
   id SERIAL PRIMARY KEY,
   count BIGINT DEFAULT 1,
   previous_artist_id BIGINT DEFAULT 0
);
