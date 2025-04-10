package com.example.pomodoro_study;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import java.util.ArrayList;

public class ProjectDataBaseHelper extends SQLiteOpenHelper {

    // For flashcard Activity ... define flashcard
    // Define Flashcard as a static class to be used across the app
    public static class Flashcard {
        public String category;
        public String question;
        public String answer;

        public Flashcard(String category, String question, String answer) {
            this.category = category;
            this.question = question;
            this.answer = answer;
        }
    }
    private static final String DATABASE_NAME = "Flashcards.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    // SQL statements for creating and deleting tables
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FlashcardTable.TABLE_NAME + " (" +
                    FlashcardTable._ID + " INTEGER PRIMARY KEY," +
                    FlashcardTable.COLUMN_CATEGORY + " TEXT," +
                    FlashcardTable.COLUMN_QUESTION + " TEXT," +
                    FlashcardTable.COLUMN_ANSWER + " TEXT)";



    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FlashcardTable.TABLE_NAME;

    // Constructor
    public ProjectDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        // Insert initial data
        insertFlashcard(db, "Computer Science", "What is the boiling point of water?", "100°C");
        insertFlashcard(db, "Biology", "What is the formula for the area of a circle?", "πr²");
        insertFlashcard(db, "Business", "What is the boiling point of water?", "100°C");
        insertFlashcard(db, "Anatomy", "What is the formula for the area of a circle?", "πr²");
    }

    private void insertFlashcard(SQLiteDatabase db, String category, String question, String answer) {
        ContentValues values = new ContentValues();
        values.put(FlashcardTable.COLUMN_CATEGORY, category);
        values.put(FlashcardTable.COLUMN_QUESTION, question);
        values.put(FlashcardTable.COLUMN_ANSWER, answer);
        db.insert(FlashcardTable.TABLE_NAME, null, values);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public ProjectDataBaseHelper open() throws SQLException {
        db = this.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }
    // Inner class that defines the table contents
    public static class FlashcardTable implements BaseColumns {
        public static final String TABLE_NAME = "flashcards";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
    }

    public ArrayList<Flashcard> getFlashcardByCategory(String category) {
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                FlashcardTable.COLUMN_CATEGORY,
                FlashcardTable.COLUMN_QUESTION,
                FlashcardTable.COLUMN_ANSWER
        };

        String selection = FlashcardTable.COLUMN_CATEGORY + " = ?";
        String[] selectionArgs = { category };

        try (
                Cursor cursor = db.query(
                        FlashcardTable.TABLE_NAME,   // The table to query
                        projection,                  // The columns to return
                        selection,                   // The columns for the WHERE clause
                        selectionArgs,               // The values for the WHERE clause
                        null,                        // Don't group the rows
                        null,                        // Don't filter by row groups
                        null                         // The sort order
        )) {
            while (cursor.moveToNext()) {
                Flashcard flashcard = new Flashcard(
                        cursor.getString(cursor.getColumnIndexOrThrow(FlashcardTable.COLUMN_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(FlashcardTable.COLUMN_QUESTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(FlashcardTable.COLUMN_ANSWER))
                );
                flashcards.add(flashcard);
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error while retrieving flashcards: " + e.getMessage(), e);
        } finally {
            db.close();
        }

        return flashcards;
    }

}


