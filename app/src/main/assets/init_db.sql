-- SQL script to create the flashcards table
-- Using ProjectDatabaseHelper
CREATE TABLE ID NOT EXISTS flashcards(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    question TEXT NULL,
    answer TEXT NOT NULL,
    category TEXT
    );
