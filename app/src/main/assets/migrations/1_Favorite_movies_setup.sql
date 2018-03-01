CREATE TABLE
    IF NOT EXISTS 'favorite'
        (_id INTEGER PRIMARY KEY,
         title TEXT,
         release_date TEXT,
         poster BLOB,
         original_title TEXT,
         vote TEXT,
         plot TEXT);